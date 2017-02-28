package strategy.actions.offense;

import strategy.actions.ActionException;
import strategy.actions.ActionBase;
import strategy.navigation.Obstacle;
import strategy.points.DynamicPoint;
import strategy.Strategy;
import strategy.robots.RobotBase;
import vision.Ball;
import vision.Robot;
import vision.RobotType;
import vision.tools.VectorGeometry;

/**
 * Created by Amrit Seshadri
 */
public class GotoAvoidBall extends ActionBase {
    public GotoAvoidBall(RobotBase robot, DynamicPoint point) {
        super(robot, point);
        this.rawDescription = " GOTO";
    }

    @Override
    public void enterState(int newState) {

        Ball ball = Strategy.world.getBall();

        if(newState == 1){
            this.robot.MOTION_CONTROLLER.addObstacle(new Obstacle((int)ball.location.x, (int)ball.location.y, 30));
            this.robot.MOTION_CONTROLLER.setDestination(this.point);
            this.robot.MOTION_CONTROLLER.setHeading(this.point);
        } else {
            this.robot.MOTION_CONTROLLER.setDestination(null);
            this.robot.MOTION_CONTROLLER.setHeading(null);
        }
        this.state = newState;
    }

    @Override
    public void tok() throws ActionException {
        Robot us = Strategy.world.getRobot(RobotType.FRIEND_2);
        if(us == null){
            this.enterState(0);
            return;
        }
        if(VectorGeometry.distance(this.point.getX(), this.point.getY(), us.location.x, us.location.y) < 10){
            this.enterState(2);
        } else {
            if(this.state == 0){
                this.enterState(1);
            }
        }
        if(this.state == 2){

            this.robot.MOTION_CONTROLLER.clearObstacles();
            throw new ActionException(true, false);
        }
    }
}




