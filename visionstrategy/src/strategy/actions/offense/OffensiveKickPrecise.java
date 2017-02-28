package strategy.actions.offense;

import strategy.actions.ActionException;
import strategy.actions.ActionBase;
import strategy.actions.other.Goto;
import strategy.points.basicPoints.BallPoint;
import strategy.points.basicPoints.KickPoint;
import strategy.robots.Fred;
import strategy.robots.RobotBase;

/**
 * Created by Simon Rovder,
 * adapted by Perry Gibson
 */
public class OffensiveKickPrecise extends ActionBase {

    public OffensiveKickPrecise(RobotBase robot) {
        super(robot);
        this.rawDescription = "OffensiveKickPrecise";
    }
    @Override
    public void enterState(int newState) {
        // Head to kick point
        if (this.state == 0) {
            this.enterAction(new GotoAvoidBall(this.robot, new KickPoint()), 0, 0);
        }

        this.robot.MOTION_CONTROLLER.setHeading(new BallPoint());
        this.robot.MOTION_CONTROLLER.setDestination(new BallPoint());


        if(newState == 1){
            if(this.robot instanceof Fred){
                ((Fred)this.robot).PROPELLER_CONTROLLER.setActive(true);
            }
        }
        this.state = 0;
    }

    @Override
    public void tok() throws ActionException {
        this.enterState(1);
    }
}