package ev3dev.java.walle.strategies;

import lejos.robotics.SampleProvider;
import ev3dev.hardware.port.MotorPort;
import ev3dev.hardware.port.SensorPort;
import ev3dev.hardware.sensor.ev3.EV3UltrasonicSensor;
import ev3dev.java.walle.actuators.DifferentialDrive;
import ev3dev.java.walle.actuators.Grabber;
import ev3dev.java.walle.sensors.ColorSensor;
import ev3dev.java.walle.sensors.TouchSensor;
import ev3dev.java.walle.utils.State;

public class Strat1 {
	private State s;
	private DifferentialDrive dd;
	private TouchSensor ts;
	private EV3UltrasonicSensor us;
	private Grabber g;
	private boolean degaj = false;

	public Strat1(DifferentialDrive dd, TouchSensor ts, Grabber g, EV3UltrasonicSensor us) {
		this.dd = dd;
		this.ts = ts;
		this.g = g;
		this.us = us;
	}

	public void run() {
		dd.changeSpeed(500, 1500);
		g.open();

		while (!ts.isPressed()){
			dd.forward();
		}
		dd.stop();
		g.close();

		SampleProvider distance = us.getDistanceMode();
		float[] sample = new float[distance.sampleSize()];

		distance.fetchSample(sample, 0);
		float d = sample[0];
		
		while (d > 280) {
			if (d < 350 && degaj == false) {
				dd.rotateTo(-90);
				dd.goTo(1000);
				dd.rotateTo(90);
				degaj = true;
			}
			dd.forward();
			distance.fetchSample(sample, 0);
			d = sample[0];
		}
		dd.stop();
		g.open();
		dd.goTo(-200);
		g.close();
	}
}
