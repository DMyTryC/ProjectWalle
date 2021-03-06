package ev3dev.java.walle.sensors;

import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;
import lejos.utility.Delay;
import ev3dev.hardware.sensor.ev3.EV3ColorSensor;
import ev3dev.java.walle.utils.Scalaire;

import java.util.Scanner;

public class ColorSensor {
	private static float[] path_color;
	private static SampleProvider average;
	private static EV3ColorSensor colorSensor;
	private final static double ERROR = 30;

	Scanner sc = new Scanner(System.in);

	public ColorSensor(String port) {
		colorSensor = new EV3ColorSensor(port);
		average = new MeanFilter(colorSensor.getRGBMode(), 10);
		colorSensor.setFloodlight(Color.WHITE);

		System.out
				.println("Do you want to load direcltly or recalibrate ? L/C");
		if (sc.next().charAt(0) == 'L')
			CalibrateColor.load();
		else {
			CalibrateColor.calibrate(average);
		}
	}

	/**
	 * Compare la couleur sous le robot à celle de path_color.
	 * @return Vrai si le robot se trouve sur la bonne couleur
	 */
	public boolean onPath() {
		path_color = CalibrateColor.getYellow();
		float[] sample = new float[average.sampleSize()];
		average.fetchSample(sample, 0);

		double scalaire = Scalaire.scalaire(sample, path_color);

		System.out.println("Scalaire : " + scalaire);
		System.out.println();

		System.out.println(scalaire < ERROR);

		return Scalaire.scalaire(sample, path_color) < ERROR;

	}

	/**
	 * Affiche continuellement pendant 20 secondes le résultat de onPath.
	 */
	public void test() {
		for (int i = 0; i < 100; ++i) {
			Delay.msDelay(200);
			System.out.println(onPath());
		}

	}
	/**
	 * Recalibre les couleurs.
	 */
	public void reCalibrate() {
		CalibrateColor.calibrate(average);
	}
}
