package strategy.actions.offense;

import communication.ports.interfaces.PropellerEquipedRobotPort;
import strategy.Strategy;
import strategy.controllers.ControllerBase;
import strategy.robots.RobotBase;
import vision.Robot;
import vision.constants.Constants;
import vision.tools.VectorGeometry;

/**
 * Created by s1410149 on 26/02/17.
 */
public class Kicker extends ControllerBase {

    public Kicker(RobotBase robot) {
        super(robot);

    }

    public void kick(int dir){
        PropellerEquipedRobotPort port = (PropellerEquipedRobotPort) this.robot.port;
        port.propeller(dir);
    }

    public void perform(){}
}
