package strategy.actions.other;

import strategy.actions.ActionException;
import strategy.actions.ActionBase;
import strategy.points.basicPoints.BehindBallPoint;
import strategy.points.basicPoints.DangerousPoint;
import strategy.points.basicPoints.MidDangerPoint;
import strategy.robots.Fred;
import strategy.actions.offense.OffensiveKick;
import strategy.robots.RobotBase;

/**
 * Created by Simon Rovder
 */
public class DefendGoal extends ActionBase {


    public DefendGoal(RobotBase robot) {
        super(robot);
        this.rawDescription = " Defend Goal";
    }
    @Override
    public void enterState(int newState) {
        if(newState == 0){
            if(this.robot instanceof Fred){
                ((Fred)this.robot).PROPELLER_CONTROLLER.setActive(true);
            }
        }
        this.state = newState;
    }

    @Override
    public void tok() throws ActionException {
        if(this.state == 0){
            robot.MOTION_CONTROLLER.setHeading(new DangerousPoint());

            MidDangerPoint midDangerPoint=new MidDangerPoint(this.robot.robotType);
            //Attack in case the ball is moving slowly ,far from the goal area, in our own side of the pitch.
            if(midDangerPoint.betterAttack())
            {
                this.enterAction(new OffensiveKick(this.robot,new BehindBallPoint()), 0, 0);
            }
            else
            {
                this.enterAction(new HoldPosition(this.robot, midDangerPoint), 0, 0);
                this.enterState(1);
            }
        }
    }
}


