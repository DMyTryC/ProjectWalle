package ev3dev.java.walle;

import java.util.Scanner;

import ev3dev.hardware.motor.EV3MediumRegulatedMotor;

public class Grabber {

	private EV3MediumRegulatedMotor gm;
	private static final int SPEED = 200;

	public Grabber(String sensorPort) {
		Scanner sc = new Scanner(System.in);
		gm = new EV3MediumRegulatedMotor(sensorPort);
		
		System.out.println("run");
		System.out.println("speed1 : " + gm.getSpeed());
		gm.setSpeed(SPEED);
		System.out.println("input");
		sc.next();
		gm.setSpeed(SPEED);
		System.out.println("speed2 : " + gm.getSpeed());
		sc.close();
	}

	public void open() {
		gm.forward();
		
		if (gm.isStalled()) {
			gm.stop();
			System.out.println("Can't open anymore");
		}
	}

	public void close() {
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
