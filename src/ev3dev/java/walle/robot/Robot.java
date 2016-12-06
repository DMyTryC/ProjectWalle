package ev3dev.java.walle.robot;

import ev3dev.hardware.port.MotorPort;
import ev3dev.hardware.port.SensorPort;
import ev3dev.hardware.sensor.ev3.EV3UltrasonicSensor;
import ev3dev.java.walle.actuators.DifferentialDrive;
import ev3dev.java.walle.actuators.Grabber;
import ev3dev.java.walle.sensors.ColorSensor;
import ev3dev.java.walle.sensors.TouchSensor;
import ev3dev.java.walle.strategies.CherchePallet;
import ev3dev.java.walle.strategies.Strat1;
import ev3dev.java.walle.utils.State;

public class Robot {

	@SuppressWarnings("unused")
	private State s;
	private DifferentialDrive dd;
	private TouchSensor ts;
	private EV3UltrasonicSensor ds;
	private ColorSensor cs;
	private Grabber g;
	private CherchePallet cp;
	private Strat1 s1;

	public Robot() {
		s = new State();
		dd = new DifferentialDrive(MotorPort.B, MotorPort.C);
		ts = new TouchSensor(SensorPort.S1);
		cs = new ColorSensor(SensorPort.S4);
		g = new Grabber(MotorPort.D);
		ds = new EV3UltrasonicSensor(SensorPort.S2);
		cp = new CherchePallet(dd, ds, ts, g);
		s1 = new Strat1(dd, ts, g, ds);
	}

	public void variousTest() {
		boolean go = true;

		while (go) {
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
		cp.run();
	}

	public void strat1() {
		s1.run();
	}

	public void testColor() {
		cs.test();
	}
}
