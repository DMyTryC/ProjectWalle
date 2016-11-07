package ev3dev.java.walle;

import lejos.utility.Delay;
import ev3dev.hardware.motor.EV3MediumRegulatedMotor;

public class Grabber {

	private EV3MediumRegulatedMotor gm;
	private static final int SPEED = 700;
	private static final int DELAY = 200;
	private static final int RAMP = 500;
	

	public Grabber(String sensorPort) {
		gm = new EV3MediumRegulatedMotor(sensorPort);
		this.init();
	}

	private void set() {
		Delay.msDelay(DELAY);
		System.out.println("Speed = " + gm.getSpeed());
		gm.setSpeed(SPEED);
		gm.setRampUp(RAMP);
		gm.setRampDown(RAMP);
		System.out.println("Speed has been set to " + gm.getSpeed());
		
	}
	private void init() {
		
		set();
		System.out.println("INIT...");
	    
		do {
			gm.forward();
			System.out.print("\r"+gm.isStalled());
		} while (!(gm.isStalled()));
	    System.out.println("\nSTALLED.");
		
	    gm.resetTachoCount();
		System.out.println("RESET COUNT. Position = " + gm.getTachoCount());
		
		set();
		open();
	}

	public void test() {
		this.close();
	}
	
	public void open(){
		System.out.println("OPENNING...");
		gm.rotateTo(-1800);
		System.out.println("OPENED. Position = " + gm.getTachoCount());
	}
	
	public void close(){
		System.out.println("CLOSING...");
		gm.rotateTo(-3400);
		System.out.println("CLOSED. Position = " + gm.getTachoCount());
	}
}
