package vision.spotAnalysis.approximatedSpotAnalysis;

import vision.colorAnalysis.SDPColor;
import vision.colorAnalysis.SDPColorInstance;
import vision.colorAnalysis.SDPColors;
import vision.constants.Constants;
import vision.tools.VectorGeometry;
/**
 * Created by Simon Rovder
 */
public class Spot extends VectorGeometry implements Comparable{
	public int magnitude;
	public SDPColor color;
	public SDPColorInstance colorInstance;
	public float[] avgHSV;
	public Spot(double x, double y, int magnitude, SDPColor color, SDPColorInstance cInstance, float[] avgHSV){
		this.x = x;
		this.y = y;
		this.color = color;
		this.magnitude = magnitude;
		this.colorInstance = cInstance;
		this.avgHSV = avgHSV;
	}
	

	@Override
	public int compareTo(Object o) {
		return ((Spot)o).magnitude - this.magnitude;
	}
}
