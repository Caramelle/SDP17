package strategy.points.basicPoints;

import strategy.points.DynamicPoint;
import strategy.points.DynamicPointBase;
import vision.tools.VectorGeometry;

/**
 * Created by Dabal Pedamonti on 21/02/2017
 */
public class TestPoint extends DynamicPointBase {

    @Override
    public void recalculate() {
//        this.point1.recalculate();
//        this.point2.recalculate();
//        VectorGeometry v = VectorGeometry.fromTo(this.point1.getX(), this.point1.getY(), this.point2.getX(), this.point2.getY()).multiply(0.5).add(this.point1.getX(), this.point1.getY());
//        this.x = (int) v.x;
//        this.y = (int) v.y;
    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

}
