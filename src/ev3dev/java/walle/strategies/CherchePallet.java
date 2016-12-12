package ev3dev.java.walle.strategies;

import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MinimumFilter;
import ev3dev.hardware.sensor.ev3.EV3UltrasonicSensor;
import ev3dev.java.walle.actuators.DifferentialDrive;
import ev3dev.java.walle.actuators.Grabber;
import ev3dev.java.walle.sensors.TouchSensor;

/**
 * Strategie qui permet de rechercher un palet en tournant sur soit meme
 * 
 * Cette strategie consistera en tourner 8 fois a un angle de 45 et si le robot
 * trouve un objet a une distance entre 250 et (450 + dist_old) il va retourner
 * en arriere en faisant un narrowSearch() du pallet ou du mur pour trouver la
 * position exacte du objet trouve. Apres avoir trouve un pallet ou un mur, on
 * va avancer vers l'objet et si c'est un mur, alors on va s'arreter et on va
 * aller vers une autre place et recommencer la recherche
 * 
 * @member min le minimum de distance qu'on trouvera a chaque fois qu'on tourne
 * @member ERROR l'erreur utilise pendant la recherche du pallet quand on
 *         retourne en arriere
 * @member ERROR2 l'erreur utilise pendant la narrowSearch() d'un pallet
 * @member found une variable qui sera initialise a true si le robot trouve un
 *         pallet ou un mur
 * @member dist_old une variable qui sera utilise pour augmenter la distance
 *         maximum de recherche par 100 apres chaque tour
 */
public class CherchePallet {

	private DifferentialDrive dd;
	private EV3UltrasonicSensor us;
	private TouchSensor ts;
	private Grabber g;

	private float min;
	private static final float ERROR = 40;
	private static final float ERROR2 = 30;
	private boolean found = false;
	private float dist_old = 0;

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
	public CherchePallet(DifferentialDrive dd, EV3UltrasonicSensor us,
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
		dd.changeSpeed(500, 2000, 100);
		g.close();
		found = false;
		while (!found) {
			cherche();
			if (found) {
				avance();
			}
		}
	}

	/**
	 * La methode qui va chercher un objet
	 * 
	 * On va avoir un minimumFilter qui prendra des samples pendant que le robot
	 * tourne Le robot va tourner 8 fois a 45 degrees. En temps que le robot
	 * tourne on va prendre une valeur min Le min sera initialise a la valeur de
	 * sample[0] qui sera le min de l'echantillon Si le minimum sera entre 250
	 * et (450 + dist_old) on va tourner en arriere pour trouver la position
	 * minimum avec une erreur de +- 30 mm ou on trouvera forcement le plus
	 * proche objet dans l'echantillon pris dans les 45 degrees Quand on
	 * trouvera l'objet on fera un narrowSearch() pour etre exactement en face
	 * du objet
	 * 
	 * Si on ne trouve pas un objet entre 250 et (450 + dist_old) on va
	 * augmenter la dist_old par 100 pour pouvoir rechercher des objets plus
	 * loin
	 */
	private void cherche() {
		float dist = 0;
		SampleProvider distance = us.getDistanceMode();
		SampleProvider min_sample = new MinimumFilter(distance, 5);
		float[] sample = new float[min_sample.sampleSize()];

		for (int i = 0; i < 4; i++) {
			dd.rotateTo(194, true);
			min = 1000000000;
			while (dd.isMoving()) {
				min_sample.fetchSample(sample, 0);
				if (sample[0] < min)
					min = sample[0];
			}
			System.out.println("Distance min : " + min);

			if (min > 300 && min < 600 + dist_old) {
				float[] sample2 = new float[distance.sampleSize()];
				dd.rotateTo(-200, true);

				while (dd.isMoving()) {
					distance.fetchSample(sample2, 0);
					dist = sample2[0];

					if ((dist >= min - ERROR) && (dist <= min + ERROR)) {
						System.out.println("Trouve");
						dd.stop();
						dd.rotateTo(-20);
						narrowSearch();
						if (found == true)
							i = 10;
						else dd.rotateTo(200);
					}
				}
			}
		}
		dist_old += 100;
	}

	/**
	 * Methode qui permet de faire une recherche plus precise de la position du
	 * objet trouve pour pouvoir trouver la position exacte (+- erreur de 20 mm)
	 * du objet
	 */
	private void narrowSearch() {
		dd.changeSpeed(50, 1000, 100);
		SampleProvider distance = us.getDistanceMode();
		float[] sample = new float[distance.sampleSize()];
		distance.fetchSample(sample, 0);
		float dg = sample[0];

		found = false;
		dd.rotateTo(194,true);
		
		while (dd.isMoving()) {
			distance.fetchSample(sample, 0);
			dg = sample[0];
			
			if ((dg >= min - ERROR2) && (dg <= min + ERROR2)){
				dd.stop();
				found = true;
			}
		}
		dd.changeSpeed(500, 2000, 100);
	}

	/**
	 * Methode qui sera utilise pour avancer vers un objet trouve Si la distance
	 * au objet sera plus petite de 20 cm, on va aller en arriere, tourner a 70
	 * degrees et avancer pour pouvoir refaire la recherche
	 */
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
			} else if (d < 280) {
				dd.stop();
				g.close();
				dd.goTo(-1500);
				dd.rotateTo(388);
				dd.goTo(300);
				found = false;
				dist_old = 0;
			}
		}
	}
}
