package strategy.actions.offense;
import java.lang.*;
import strategy.Strategy;
import strategy.actions.ActionException;
import strategy.actions.ActionBase;
import strategy.points.basicPoints.BallPoint;
import strategy.points.basicPoints.BehindBallPoint;
import strategy.points.basicPoints.EnemyGoal;
import strategy.robots.Fred;
import strategy.robots.RobotBase;
import vision.Robot;
import vision.RobotType;
import vision.tools.VectorGeometry;

/**
 * Created by Simon Rovder
 */
public class OffensiveKick extends ActionBase {

   // public BehindBallPoint behindBall = new BehindBallPoint();
    //public Robot us = Strategy.world.getRobot(RobotType.FRIEND_2);


    public OffensiveKick(RobotBase robot) {
        super(robot);
        this.rawDescription = "OffensiveKick";
        this.point= new BehindBallPoint();
    }

    public boolean cankick (BehindBallPoint behindBall, Robot us){

        if(VectorGeometry.distance(behindBall.getX(), behindBall.getY(), us.location.x, us.location.y) < 25){
            return true;
        }
        else{
              return false;
        }


    }

    @Override
    public void enterState(int newState) {
        BehindBallPoint behindBall = new BehindBallPoint();
        Robot us = Strategy.world.getRobot(RobotType.FRIEND_2);
        Kicker kicker = new Kicker(this.robot);


        this.robot.MOTION_CONTROLLER.setHeading(EnemyGoal.getEnemyGoalPoint());
        this.robot.MOTION_CONTROLLER.setDestination(behindBall);


       /*
        if(newState == 0){
            System.out.println("entered state 0");
            if(this.robot instanceof Fred){
                //((Fred)this.robot).PROPELLER_CONTROLLER.setActive(true);
                Kicker kicker = new Kicker(this.robot);

                //kicker.kick(1);
                System.out.println("entered kick");
                System.out.println("kicked in offensive kick");
                int counter=0;
                Robot us = Strategy.world.getRobot(RobotType.FRIEND_2);
                //System.out.println("coordinate: x:" +(new BallPoint()).getX()+" y:"+ (new BallPoint().getY()));
                System.out.println("coordinate: x:" +behindBall.getX()+" y:"+behindBall.getY());
                System.out.println("coordinateLocation: x:" +us.location.x+" y:"+us.location.y);

                if(VectorGeometry.distance(behindBall.getX(), behindBall.getY(), us.location.x, us.location.y) < 10) {
                    ((Fred) this.robot).PROPELLER_CONTROLLER.setActive(true);
                    //((Fred)this.robot).PROPELLER_CONTROLLER.propell(-1);
                    System.out.println("counter: " + counter);
                    behindBall.recalculate();
                    kicker.kick(1);

                    System.out.println("Close enough to kick and kick 1");
                    counter++;
                }
                kicker.kick(0);
                System.out.println(" kick 0");

//                    try{
//                        Thread.sleep(50);
//                    }
//                    catch(Exception e)
//                    {
//                        System.out.println("Failed to sleep in offensive kick");
//                    }
//                }
//                else
//                {
//                    System.out.println("Not close enough to kick");
//                    //((Fred)this.robot).PROPELLER_CONTROLLER.propell(0);
//                    System.out.println("Stopped kicking");
//                }
//
//                ((Fred)this.robot).PROPELLER_CONTROLLER.setActive(false);
            }
            System.out.println("exit kick ");
        }

        */




        if(VectorGeometry.distance(behindBall.getX(), behindBall.getY(), us.location.x, us.location.y) < 10){

            kicker.kick(1);
            System.out.println("kicker.kick");
        }
        else {
            kicker.kick(0);
            System.out.println(" NOOOOOOOOOOOOOOOOOOOOOOOt kicker.kick");
        }




        System.out.println("exit state 0");
        this.state = 0;
    }

    @Override
    public void tok() throws ActionException {

       //sumits debugger

       /* if(VectorGeometry.distance(behindBall.getX(), behindBall.getY(), us.location.x, us.location.y) < 10) {
            System.out.println("in tok offensive kick");
            //((Fred) this.robot).PROPELLER_CONTROLLER.setActive(true);


        }*/
/*
        System.out.println("in tok offensive kick");
      if(cankick(behindBall, us)){
          ((Fred) this.robot).PROPELLER_CONTROLLER.setActive(true);
          System.out.println("in tok offensive can kick");
          System.out.println(us.location.x);
      }
      else {
          ((Fred) this.robot).PROPELLER_CONTROLLER.setActive(false);
          System.out.println("in tok offensive can NOOOOOOOOOOOOOOOOOOOT kick");
          System.out.println(us.location.x  + "   "+ us.location.y);
      }*/

        //((Fred) this.robot).PROPELLER_CONTROLLER.setActive(true);

     this.enterState(0);

    }
}
