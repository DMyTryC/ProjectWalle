package ev3dev.java.walle.actuators;

import lejos.utility.Delay;
import ev3dev.hardware.motor.EV3MediumRegulatedMotor;

public class Grabber {

	private EV3MediumRegulatedMotor gm;
	private static final int SPEED = 700;
	private static final int RAMP = 500;
	private boolean isOpen;
	

	public Grabber(String sensorPort) {
		gm = new EV3MediumRegulatedMotor(sensorPort);
		this.init();
	}

	private void set() {
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
			Delay.msDelay(500);
			System.out.print("\r"+gm.isStalled());
		} while (!(gm.isStalled()));
	    System.out.println("\nSTALLED.");
		
	    gm.resetTachoCount();
		System.out.println("RESET COUNT. Position = " + gm.getTachoCount());
		
		set();
		open();
	}
	
	public void open(){
		System.out.println("OPENNING...");
		gm.rotateTo(-2000);
		System.out.println("OPENED. Position = " + gm.getTachoCount());
		isOpen = true;
	}
	
	public void close(){
		System.out.println("CLOSING...");
		gm.rotateTo(-3400);
		System.out.println("CLOSED. Position = " + gm.getTachoCount());
		isOpen = false;
	}
	public boolean isOpen() {
		return isOpen;
	}
}
