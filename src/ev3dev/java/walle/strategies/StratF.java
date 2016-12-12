/**
 * 
 */
package ev3dev.java.walle.strategies;

import lejos.robotics.SampleProvider;
import ev3dev.hardware.sensor.ev3.EV3UltrasonicSensor;
import ev3dev.java.walle.actuators.DifferentialDrive;
import ev3dev.java.walle.actuators.Grabber;
import ev3dev.java.walle.sensors.TouchSensor;

/**
 * La strategie finale
 */
public class StratF {
	private DifferentialDrive dd;
	private TouchSensor ts;
	private EV3UltrasonicSensor us;
	private Grabber g;
	Strat1 s1;
	CherchePallet cp;
	
	/**
	 *Le constructeur pour initialiser les atributs d'instance
	 * 
	 */
	
	public StratF(DifferentialDrive dd, TouchSensor ts, EV3UltrasonicSensor us,
			Grabber g) {
		s1 = new Strat1(dd, ts, g, us);
		cp = new CherchePallet(dd, us, ts, g);
		this.dd = dd;
		this.ts = ts;
		this.us = us;
		this.g = g;
	}

	// int count = 0;

	/**
	 * Methode pour deposer un pallet trouve auparavant
	 */
	private void deposer() {
		SampleProvider distance = us.getDistanceMode();
		float[] sample = new float[distance.sampleSize()];

		distance.fetchSample(sample, 0);
		float d = sample[0];
		
		dd.forward();
		while (dd.isMoving()){
			distance.fetchSample(sample, 0);
			d = sample[0];
			if (d < 300){
				dd.stop();
			}
		}
		dd.stop();
		g.open();
		dd.goTo(-200);
	}
	
	/**
	 * La methode principale pour la strategie
	 */
	public void run() {
		s1.run();
		for (int i = 0; i < 2; i++) {
			dd.goBackToZero();
			dd.goTo(-1500);
			cp.run();
			dd.goBackToZero();
			deposer();
		}

	}
}
