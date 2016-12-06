package ev3dev.java.walle.actuators;

import lejos.utility.Delay;
import ev3dev.hardware.motor.EV3MediumRegulatedMotor;

public class Grabber {

	private EV3MediumRegulatedMotor gm;
	private static int SPEED = 500;
	private static int RAMP = 200;
	private boolean isOpen;
	

	public Grabber(String sensorPort) {
		gm = new EV3MediumRegulatedMotor(sensorPort);
	}

	private void set() {
		gm.setSpeed(SPEED);
		gm.setRampUp(RAMP);
		gm.setRampDown(RAMP);
		System.out.println("Speed has been set to " + gm.getSpeed());
		
	}
	public void init() {
		
		set();
		System.out.println("INIT...");
	    
		gm.forward();
	    Delay.msDelay(200);
		do {
			gm.forward();
			Delay.msDelay(200);
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
		gm.rotateTo(-2500);
		System.out.println("OPENED. Position = " + gm.getTachoCount());
		isOpen = true;
	}
	
	public void close(){
		System.out.println("CLOSING...");
		gm.rotateTo(-3300);
		System.out.println("CLOSED. Position = " + gm.getTachoCount());
		isOpen = false;
	}
	
	public boolean isOpen(){
		return isOpen();
	}
	
	public void changeSpeed(int speed, int time){
		SPEED = speed;
		RAMP = time;
		gm.setRampDown(200);
		set();
	}
	
	public boolean isStalled(){
		if (gm.isStalled()){
			return true;
		}
		return false;
	}
}