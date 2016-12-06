package ev3dev.java.walle.strategies;

import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MinimumFilter;
import ev3dev.hardware.sensor.ev3.EV3UltrasonicSensor;
import ev3dev.java.walle.actuators.DifferentialDrive;
import ev3dev.java.walle.actuators.Grabber;
import ev3dev.java.walle.sensors.TouchSensor;

public class CherchePallet {

	private DifferentialDrive dd;
	private EV3UltrasonicSensor us;
	private TouchSensor ts;
	private Grabber g;

	private float min;
	private float dist = 0;
	private static final float ERROR = 30;
	private static final float ERROR2 = 20;
	private boolean found = false;
	private float dist_old = 0;

	public CherchePallet(DifferentialDrive dd, EV3UltrasonicSensor us,
			TouchSensor ts, Grabber g) {
		this.dd = dd;
		this.us = us;
		this.ts = ts;
		this.g = g;
	}

	public void run() {
		g.init();
		g.close();

		while (!found) {
			cherche();
			if (found) {
				avance();
			}
		}
	}

	private void cherche() {

		SampleProvider distance = us.getDistanceMode();
		SampleProvider min_sample = new MinimumFilter(distance, 5);
		float[] sample = new float[min_sample.sampleSize()];

		for (int i = 0; i < 8; i++) {
			dd.rotateTo(-97, true);

			System.out.println(dd.isMoving());

			while (dd.isMoving()) {
				min_sample.fetchSample(sample, 0);
			}

			min = sample[0];
			System.out.println("Distance min : " + min);

			if (min > 250 && min < 450 + dist_old) {
				dd.rotateTo(97, true);

				float[] sample2 = new float[distance.sampleSize()];

				while (dd.isMoving()) {
					distance.fetchSample(sample2, 0);
					dist = sample2[0];

					if ((dist >= min - ERROR) && (dist <= min + ERROR)) {
						System.out.println("Trouve");
						dd.stop();
						narrowSearch();
						found = true;
						i = 10;
					}
				}
			}
		}
		dist_old += 100;
	}

	private void narrowSearch() {
		dd.changeSpeed(50, 200);
		dd.rotateTo(-15, true);

		SampleProvider distance = us.getDistanceMode();
		float[] sample = new float[distance.sampleSize()];

		while (dd.isMoving()) {
			distance.fetchSample(sample, 0);
			float dg = sample[0];
			if ((dg >= min - ERROR2) && (dg <= min + ERROR2)) {
				dd.stop();
			}
		}
		dd.changeSpeed(500, 500);
	}
	private void avance() {
		SampleProvider distance = us.getDistanceMode();
		float[] sample = new float[distance.sampleSize()];
		float d;
		g.open();
		dd.forward();
		while (dd.isMoving()) {
			distance.fetchSample(sample, 0);
			d = sample[0];
			if (ts.isPressed()) {
				dd.stop();
				g.close();
			}
			else if (d < 200) {
				dd.stop();
				dd.goTo(-600);
				dd.rotateTo(150);
				dd.goTo(700);
				g.close();
				found = false;
			}
		}
	}
}
