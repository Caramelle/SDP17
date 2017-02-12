package strategy.points.basicPoints;

import strategy.Strategy;
import strategy.points.DynamicPoint;
import strategy.points.DynamicPointBase;
import vision.Ball;
import vision.Robot;
import vision.RobotType;
import vision.constants.Constants;

/**
 * Created by Perry Gibson on 31/01/17.
 */
public class KickPoint extends DynamicPointBase{

    public void recalculate() {
        DynamicPoint ballPoint = new BallPoint();
        EnemyGoal goal = new EnemyGoal();

        double tempx = goal.getX() - ballPoint.getX();
        double tempy = ballPoint.getY();
        int angle = (int) Math.atan(tempy/tempx);
        double h_prime =  Math.sqrt(tempx * tempx + tempy * tempy) + 10;

        this.x = (int) (Constants.PITCH_WIDTH/2 - h_prime * Math.cos(angle));
        this.y = (int) (h_prime * Math.sin(angle));


    }

//    public static void main(String[] args) {
//        ConstantPoint kp = new KickPoint;
//        System.out.print(kp.getX() + kp.getY());
//    }
////
//    @Override
//    public void recalculate() {
//        Ball ball = Strategy.world.getBall();
//        if(ball != null){
//            this.x = (int)ball.location.x;
//            this.y = (int)ball.location.y;
//        } else {
//            RobotType probableHolder = Strategy.world.getProbableBallHolder();
//            if(probableHolder != null){
//                Robot p = Strategy.world.getRobot(probableHolder);
//                if(p != null){
//                    this.x = (int)p.location.x;
//                    this.y = (int)p.location.y;
//                }
//            }
//        }
//    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }
}
