package ev3dev.java.walle.robot;

import ev3dev.hardware.port.MotorPort;
import ev3dev.hardware.port.SensorPort;
import ev3dev.java.walle.actuators.DifferentialDrive;
import ev3dev.java.walle.actuators.Grabber;
import ev3dev.java.walle.sensors.ColorSensor;
import ev3dev.java.walle.sensors.DistanceSensor;
import ev3dev.java.walle.sensors.TouchSensor;
import ev3dev.java.walle.utils.State;

public class Robot {
	
	@SuppressWarnings("unused")
	private State s;
	private DifferentialDrive dd;
	private TouchSensor ts;
	@SuppressWarnings("unused")
	private DistanceSensor ds;
	@SuppressWarnings("unused")
	private ColorSensor cs;
	private Grabber g;
	
	public Robot(){
		s = new State();
		dd = new DifferentialDrive(MotorPort.B, MotorPort.C);
		ts = new TouchSensor(SensorPort.S1);
		cs = new ColorSensor(SensorPort.S4);
		g = new Grabber(MotorPort.D);
		ds = new DistanceSensor(SensorPort.S2);
	}
	
	public void variousTest() {
		boolean go = true;
		
		while(go) {
			dd.goTo(360);
			dd.turnTo(360, 4);
			if (g.isOpen())
				g.close();
			else
				g.open();
			go = !ts.isPressed();
		}
	}
	public void ddTest() {
		dd.test();
	}
	public void cherchePalet() {
		/** TODO : @Dimitri */
	}
}
