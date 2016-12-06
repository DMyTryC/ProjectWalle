package ev3dev.java.walle.strategies;

import lejos.robotics.SampleProvider;
import ev3dev.hardware.sensor.ev3.EV3UltrasonicSensor;
import ev3dev.java.walle.actuators.DifferentialDrive;
import ev3dev.java.walle.actuators.Grabber;
import ev3dev.java.walle.sensors.TouchSensor;

/**
 * Une classe qui sera apellee au debut du match pour mettre le premier pallet dans
 * la zone du but
 *
 * @member degaj
 * 			variable utilise pour aller autour du deuxieme pallet dans la premiere ligne
 */
public class Strat1 {
	private DifferentialDrive dd;
	private TouchSensor ts;
	private EV3UltrasonicSensor us;
	private Grabber g;
	
	private boolean degaj = false;
	
	/**
	 * Constructeur qui sera utilise pour initialiser les parties hardware
	 * 
	 * @param dd
	 * 			les moteurs
	 * @param us
	 * 			le distance sensor
	 * @param ts
	 * 			le touch sensor
	 * @param g
	 * 			le grabber
	 */
	public Strat1(DifferentialDrive dd, TouchSensor ts, Grabber g, EV3UltrasonicSensor us) {
		this.dd = dd;
		this.ts = ts;
		this.g = g;
		this.us = us;
	}

	/**
	 * Methode utilise pour mettre le premier pallet dans la zone but
	 * On va avancer et prendre le premier pallet apres on va eviter le deuxieme pallet
	 * et on va aller vers la zone du but pour mettre le pallet dans la zone.
	 */
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
