package vision.spotAnalysis.recursiveSpotAnalysis;

import vision.colorAnalysis.SDPColor;
import vision.colorAnalysis.SDPColorInstance;
import vision.colorAnalysis.SDPColors;
import vision.constants.Constants;
import vision.gui.Preview;
import vision.spotAnalysis.SpotAnalysisBase;
import vision.spotAnalysis.approximatedSpotAnalysis.RegionFinder;
import vision.spotAnalysis.approximatedSpotAnalysis.Spot;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;

import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import java.awt.image.Raster;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static vision.tools.ImageTools.rgbToHsv;

/**
 * Created by Simon Rovder
 */
public class RecursiveSpotAnalysis extends SpotAnalysisBase{

    private int[] rgb;
    private float[] hsv;
    private SDPColor[] found;


    public RecursiveSpotAnalysis(){
        super();
        // Have arrays of 4 times the size for the inputs\
        // (for red, green, blue, alpha OR hue, saturation, value, alpha)
        this.rgb   = new int[4* Constants.INPUT_WIDTH*Constants.INPUT_HEIGHT];
        this.hsv   = new float[4*Constants.INPUT_WIDTH*Constants.INPUT_HEIGHT];

        // array to keep track of visited spots
        this.found = new SDPColor[Constants.INPUT_WIDTH*Constants.INPUT_HEIGHT];
    }

    private int getIndex(int x, int y){
        return y*Constants.INPUT_WIDTH*3 + x*3;
    }

    private void processPixel(int x, int y, SDPColorInstance sdpColorInstance, XYCumulativeAverage average, int maxDepth){
        if(maxDepth <= 0 || x < 0 || x >= Constants.INPUT_WIDTH || y < 0 || y >= Constants.INPUT_HEIGHT) return;
        int i = getIndex(x, y);
        if(this.found[i/3] == sdpColorInstance.sdpColor) return;
        if(sdpColorInstance.isColor(this.hsv[i], this.hsv[i + 1], this.hsv[i + 2])){
            average.addPoint(x, y);
            this.found[i/3] = sdpColorInstance.sdpColor;
            this.processPixel(x-1,y, sdpColorInstance, average, maxDepth - 1);
            this.processPixel(x+1,y, sdpColorInstance, average, maxDepth - 1);
            this.processPixel(x,y+1, sdpColorInstance, average, maxDepth - 1);
            this.processPixel(x,y-1, sdpColorInstance, average, maxDepth - 1);
            Graphics g = Preview.getImageGraphics();
            if(g != null && sdpColorInstance.isVisible()){
                g.setColor(Color.WHITE);
                g.drawRect(x,y,1,1);
            }
        }
    }

    public static BufferedImage mat2Img(Mat mat)
    {
        byte[] data = new byte[mat.rows()*mat.cols()*(int)(mat.elemSize())];
        mat.get(0, 0, data);
        if (mat.channels() == 3) {
            for (int i = 0; i < data.length; i += 3) {
                byte temp = data[i];
                data[i] = data[i + 2];
                data[i + 2] = temp;
            }
        }
        BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), BufferedImage.TYPE_3BYTE_BGR);
        image.getRaster().setDataElements(0, 0, mat.cols(), mat.rows(), data);

        return image;
    }

    public static Mat img2Mat(BufferedImage image)
    {
        byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        Mat mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
        mat.put(0, 0, data);

        return mat;
    }

    public Mat blurFrame(BufferedImage image){

        //System.setProperty("java.library.path", "/afs/inf.ed.ac.uk/user/s14/s1408218/sdp/sdp/libs");
        //System.out.println(System.getProperty("java.library.path"));


        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat source = img2Mat(image);
        // Mat source = Highgui.imread("afs/inf.ed.ac.uk/user/s14/s1408218/Desktop/digital_image_processing.jpg",  Highgui.CV_LOAD_IMAGE_COLOR);
        Mat destination = new Mat(source.rows(), source.cols(), source.type());
        Imgproc.GaussianBlur(source, destination, new Size(11, 11), 0);
        //Highgui.imwrite("Gaussian45.jpg", destination);
        //BufferedImage result = mat2Img(destination);
        return destination;

    }




    @Override
    public void nextFrame(BufferedImage image, long time) {

        Mat blurredImage = blurFrame(image);
        BufferedImage img = mat2Img(blurredImage);
        Raster raster = img.getData();

        /*
         * SDP2017NOTE
         * This line right here, right below is the reason our vision system is real time. We fetch the
         * rgb values of the Raster into a preallocated array this.rgb, without allocating more memory.
         * We recycle the memory, so garbage collection is never called.
         */
        raster.getPixels(0, 0, Constants.INPUT_WIDTH, Constants.INPUT_HEIGHT, this.rgb);
        rgbToHsv(this.rgb, this.hsv);

        HashMap<SDPColor, ArrayList<Spot>> spots = new HashMap<SDPColor, ArrayList<Spot>>();
        for(SDPColor c : SDPColor.values()){
            spots.put(c, new ArrayList<Spot>());
        }

        XYCumulativeAverage average = new XYCumulativeAverage();
        SDPColorInstance colorInstance;
        for(int i = 0 ; i < Constants.INPUT_HEIGHT * Constants.INPUT_WIDTH; i++){
            this.found[i] = null;
        }
        for(SDPColor color : SDPColor.values()){
            colorInstance = SDPColors.colors.get(color);
            for(int y = 0; y < Constants.INPUT_HEIGHT; y++){
                for(int x = 0; x < Constants.INPUT_WIDTH; x++){
                    this.processPixel(x, y, colorInstance, average, 200);
                    if(average.getCount() > 5){
                        spots.get(color).add(new Spot(average.getXAverage(), average.getYAverage(), average.getCount(), color));
                    }
                    average.reset();
                }
            }
            Collections.sort(spots.get(color));
        }
        this.informListeners(spots, time);
        Preview.flushToLabel();

    }
}
