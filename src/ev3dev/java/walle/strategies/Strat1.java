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
 */
public class Strat1 {
	private DifferentialDrive dd;
	private TouchSensor ts;
	private EV3UltrasonicSensor us;
	private Grabber g;
	private CherchePallet cp;
	
	/**
	 * Constructeur qui sera utilise pour initialiser les parties hardware
	 * et la fonction de recherche du premier palet
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
		cp = new CherchePallet(dd, us, ts, g);
	}

	/**
	 * Methode utilise pour mettre le premier pallet dans la zone but
	 * On va avancer et prendre le premier pallet apres on va tourner pour eviter le deuxieme pallet
	 * et on va aller vers la zone du but pour mettre le pallet dans la zone.
	 */
	private void run1() {
		dd.changeSpeed(500, 2000, 100);
		g.open();

		while (!ts.isPressed()){
			dd.forward();
		}
		dd.stop();
		g.close();

		SampleProvider distance = us.getDistanceMode();
		float[] sample = new float[distance.sampleSize()];
		
		dd.rotateTo(-80);
		dd.goTo(300);
		dd.rotateTo(80);
		
		distance.fetchSample(sample, 0);
		float d = sample[0];
		
		while (d > 300) {
			distance.fetchSample(sample, 0);
			d = sample[0];
			dd.forward();
		}
		dd.stop();
		g.open();
		dd.goTo(-300);
		g.close();
	}
	
	/**
	 * Methode principale pour la strategie 1, apres avoir mis le premier palet
	 * on va mettre le deuxieme palet dans la zone du but
	 */
	private void run2() {
		dd.rotateTo(200);
		cp.run();
		dd.goBackToZero();

		SampleProvider distance = us.getDistanceMode();
		float[] sample = new float[distance.sampleSize()];

		distance.fetchSample(sample, 0);
		float d = sample[0];
		
		dd.forward();
		
		while (d > 300) {
			distance.fetchSample(sample, 0);
			d = sample[0];
			dd.forward();
		}
		dd.stop();
		g.open();
		dd.goTo(-700);
		g.close();
	}
	
	/**
	 * La methode principale de recherche du pallet
	 */
	public void run(){
		run1();			
		run2();
		run2();
		run2();
		run2();
		run2();
	}
}
