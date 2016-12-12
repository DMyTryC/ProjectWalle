/**
 * 
 */
package ev3dev.java.walle.strategies;

import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MinimumFilter;
import lejos.utility.Delay;
import ev3dev.hardware.sensor.ev3.EV3UltrasonicSensor;
import ev3dev.java.walle.actuators.DifferentialDrive;
import ev3dev.java.walle.actuators.Grabber;
import ev3dev.java.walle.sensors.TouchSensor;

/**
 * La deuxieme methode de recherche d'un pallet
 */
public class CherchePallet2 {
	private DifferentialDrive dd;
	private EV3UltrasonicSensor us;
	private TouchSensor ts;
	private Grabber g;

	private float min;
	private static final float ERROR = 40;

	/**
	 * Constructeur qui sera utilise pour initialiser les parties hardware
	 * 
	 * @param dd
	 *            les moteurs
	 * @param us
	 *            le distance sensor
	 * @param ts
	 *            le touch sensor
	 * @param g
	 *            le grabber
	 */
	public CherchePallet2(DifferentialDrive dd, EV3UltrasonicSensor us,
			TouchSensor ts, Grabber g) {
		this.dd = dd;
		this.us = us;
		this.ts = ts;
		this.g = g;
	}

	/**
	 * La methode principale qui va fermer les pinces et qui apres va chercher
	 * un objet jusqu'au moment ou il trouve quelque chose Si on trouve un objet
	 * on va avancer vers lui
	 */
	public void run() {
		g.close();

		float dist = 0;
		SampleProvider distance = us.getDistanceMode();
		SampleProvider min_sample = new MinimumFilter(distance, 1);
		float[] sample = new float[min_sample.sampleSize()];

		dd.changeSpeed(500, 2000, 100);
		dd.rotateTo(775, true);
		
		min = 100000;
		while (dd.isMoving()) {
			min_sample.fetchSample(sample, 0);
			if (sample[0] < min)
				min = sample[0];
		}
		System.out.println("Distance min : " + min);
		
		float[] sample2 = new float[distance.sampleSize()];
		distance.fetchSample(sample2, 0);
		dist = sample2[0];
		
		dd.changeSpeed(100, 2000, 1000);
		dd.rotateTo(775, true);
		while (dd.isMoving()) {
			distance.fetchSample(sample2, 0);
			dist = sample2[0];
			if ((dist >= min - ERROR) && (dist <= min + ERROR)) {
				dd.stop();
			}
		}
		Delay.msDelay(200);
		dd.changeSpeed(500, 2000, 100);
		
		distance.fetchSample(sample2, 0);
		dist = sample2[0];
		Delay.msDelay(200);
		g.open();
		dd.forward();
		while (dd.isMoving()) {
			distance.fetchSample(sample2, 0);
			dist = sample2[0];
			dd.forward();
			if (ts.isPressed()) {
				dd.stop();
				g.close();
				Delay.msDelay(300);
				dd.goBackToZero();
			} else if (dist < 280) {
				dd.stop();
				g.close();
				dd.goTo(-1500);
				dd.rotateTo(388);
				dd.goTo(300);
				dd.stop();
				while(dd.isMoving());
			}
		}
		dd.stop();

		Delay.msDelay(300);
		dd.goBackToZero();
	}
}
