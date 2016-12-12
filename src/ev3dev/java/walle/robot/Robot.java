package ev3dev.java.walle.robot;

import ev3dev.hardware.port.MotorPort;
import ev3dev.hardware.port.SensorPort;
import ev3dev.hardware.sensor.ev3.EV3UltrasonicSensor;
import ev3dev.java.walle.actuators.DifferentialDrive;
import ev3dev.java.walle.actuators.Grabber;
import ev3dev.java.walle.sensors.ColorSensor;
import ev3dev.java.walle.sensors.TouchSensor;
import ev3dev.java.walle.strategies.CherchePallet;
import ev3dev.java.walle.strategies.CherchePallet2;
import ev3dev.java.walle.strategies.Strat1;
import ev3dev.java.walle.strategies.StratF;
import ev3dev.java.walle.utils.State;

/**
 * La classe principale qui fait appel a toutes les strategies
 *
 */
public class Robot {

	@SuppressWarnings("unused")
	private State s;
	private DifferentialDrive dd;
	private TouchSensor ts;
	private EV3UltrasonicSensor ds;
	//private ColorSensor cs;
	private Grabber g;
	private CherchePallet cp;
	private CherchePallet2 cp2;
	private Strat1 s1;
	private StratF sf;

	public Robot() {
		s = new State();
		dd = new DifferentialDrive(MotorPort.B, MotorPort.C);
		ts = new TouchSensor(SensorPort.S1);
		//cs = new ColorSensor(SensorPort.S4);
		g = new Grabber(MotorPort.D);
		ds = new EV3UltrasonicSensor(SensorPort.S2);
		cp = new CherchePallet(dd, ds, ts, g);
		cp2 = new CherchePallet2(dd, ds, ts, g);
		s1 = new Strat1(dd, ts, g, ds);
		sf = new StratF(dd,ts,ds,g);
	}

	/**
	 * Methode utilise pour tester differentes choses
	 */
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

	/*public void setE(){
		dd.changeSpeed(500, 1500);
		g.init();
	}*/
	
	/**
	 * Le test de differential drive
	 */
	public void ddTest() {
		dd.test();
	}

	/**
	 * Le test de la premiere strategie de recherche
	 */
	public void cherchePallet() {
		cp.run();
	}

	/**
	 * Le test de la strategie 1
	 */
	public void strat1() {
		s1.run();
	}
	/*
	public void testColor() {
		cs.test();
	}
	public void reCalibrate() {
		cs.reCalibrate();
	}*/
	
	/**
	 * Le test de la strategie finale
	 */
	public void stratF(){
		sf.run();
	}
	
	/**
	 * Test de la rotation
	 */
	public void testR(){
		dd.testRotation();
	}

	/**
	 * Le test de la deuxieme strategie de recherche
	 */
	public void cherchePallet2(){
		cp2.run();
	}
}