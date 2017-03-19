package strategy.points.basicPoints;

import strategy.Strategy;
import strategy.points.DynamicPointBase;
import vision.Ball;
import vision.Robot;
import vision.RobotType;

/**
 * Created by s1410149 on 25/02/17.
 */
public class BehindBallPoint extends DynamicPointBase {


    public BehindBallPoint()
    {
        this.recalculate();
    }

    @Override
    public void recalculate() {
        Ball ball = Strategy.world.getBall();
        if(ball != null){
            this.x = (int)ball.location.x-20;
//            if ((int)ball.location.y ==0 ) {
//                this.y = (int)ball.location.y;
//            } else if ((int)ball.location.y < 0 ) {
//                this.y = (int)ball.location.y-10;
//            } else {
//                this.y = (int)ball.location.y-10;
//            }
            this.y = (int) (ball.location.y*1.11);
//            if ((int)ball.location.y>=0){
//                this.y = (int)ball.location.y+5;
//            }
//            else {
//                this.y = (int)ball.location.y-5;
//            }
        } else {
            RobotType probableHolder = Strategy.world.getProbableBallHolder();
            if(probableHolder != null){
                Robot p = Strategy.world.getRobot(probableHolder);
                if(p != null){
                    this.x = (int)p.location.x;
                    this.y = (int)p.location.y;
                }
            }
        }
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }
}
