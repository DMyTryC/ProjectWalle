package ev3dev.java.walle;

import lejos.utility.Delay;
import ev3dev.hardware.motor.EV3MediumRegulatedMotor;

public class Grabber {

	private EV3MediumRegulatedMotor gm;
	private static final int SPEED = 400;
	private static final int DELAY = 500;

	public Grabber(String sensorPort) {
		gm = new EV3MediumRegulatedMotor(sensorPort);

		System.out.println("run");
		System.out.println("speed1 : " + gm.getSpeed());
		Delay.msDelay(DELAY);
		gm.setSpeed(SPEED);
		System.out.println("speed2 : " + gm.getSpeed());
	}

	public void open() {
		gm.forward();
		Delay.msDelay(DELAY);
		while (!(gm.isStalled())) {
			gm.forward();
		}
	}

	public void test() {
		this.open();
		Delay.msDelay(DELAY);
		this.close(3400);
	}

	public void run() {
		
	}

	public void open(int angle) {
		gm.rotate(angle);
	}

	public void close(int angle) {
		gm.rotate(-angle);
	}
}
