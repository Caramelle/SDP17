package strategy.actions.other;

import strategy.Strategy;
import strategy.actions.ActionBase;
import strategy.actions.ActionException;
import strategy.points.DynamicPoint;
import strategy.robots.RobotBase;
import vision.Robot;
import vision.RobotType;
import vision.tools.VectorGeometry;

/**
 * Created by Dabal Pedamonti on 21/02/2017
 */
public class GotoTest extends ActionBase {
    public GotoTest(RobotBase robot, DynamicPoint point) {
        super(robot, point);
        this.rawDescription = " GOTOTEST";
    }

    @Override
    public void enterState(int newState) {
        if(newState == 1){
            this.robot.MOTION_CONTROLLER.setDestination(this.point);
            System.out.print("Inside newState = 1a");
            this.robot.MOTION_CONTROLLER.setHeading(this.point);
            System.out.print("Inside newState = 1b");
        } else {
            this.robot.MOTION_CONTROLLER.setDestination(null);
            System.out.print("Outside newState = 1a");
            this.robot.MOTION_CONTROLLER.setHeading(null);
            System.out.print("Outside newState = 1b");
        }
        this.state = newState;
        System.out.print(newState);
    }

    @Override
    public void tok() throws ActionException {
        Robot us = Strategy.world.getRobot(RobotType.FRIEND_2);
        if(us == null){
            this.enterState(0);
            System.out.print("Entered null");
            return;
        }
        if(VectorGeometry.distance(this.point.getX(), this.point.getY(), us.location.x, us.location.y) < 10){
            System.out.print("Entered second if");
            this.enterState(2);
        } else {
            if(this.state == 0){
                System.out.print("Entered state 0");
                this.enterState(1);

            }
        }
        if(this.state == 2){
            System.out.print("Entered state 2");
            throw new ActionException(true, false);
        }
    }
}
