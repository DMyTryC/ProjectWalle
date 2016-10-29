package ev3dev.java.walle;

import ev3dev.hardware.motor.EV3LargeRegulatedMotor;

public class Grabber {

	private EV3LargeRegulatedMotor gm;
	private static final int SPEED = 200;

	public Grabber(String sensorPort) {
		gm = new EV3LargeRegulatedMotor(sensorPort);

		gm.setSpeed(SPEED);
	}

	public void open() {
		gm.setSpeed(SPEED);
		gm.setRampUp(0);
		gm.setRampDown(0);

		gm.forward();
		if (gm.isStalled()) {
			gm.stop();
			System.out.println("Can't open anymore");
		}
	}

	public void close() {
		System.out.println("run");
		System.out.println("speed1 : " + gm.getSpeed());
		gm.setSpeed(SPEED);
		gm.setRampUp(0);
		gm.setRampDown(0);
		System.out.println("speed2 : " + gm.getSpeed());
		
		gm.backward();
		if (gm.isStalled()) {
			gm.stop();
			System.out.println("Can't close anymore");
		}
	}

	public void open(int angle) {
		gm.rotate(angle);
	}

	public void close(int angle) {
		gm.rotate(-angle);
	}
}
