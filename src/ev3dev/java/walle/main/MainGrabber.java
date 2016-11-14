package ev3dev.java.walle.main;

import ev3dev.hardware.port.MotorPort;
import ev3dev.java.walle.actuators.Grabber;

public class MainGrabber {
	public static void main(String[] args){
		Grabber g = new Grabber(MotorPort.D);
		g.close();
	}
}
 