package ev3dev.java.walle.sensors;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import lejos.robotics.SampleProvider;
import ev3dev.java.walle.utils.Scalaire;

public class CalibrateColor {

	private static float[] blue = new float[3];
	private static float[] red = new float[3];
	private static float[] yellow = new float[3];
	private static float[] white = new float[3];
	private static float[] gray = new float[3];
	private static float[] black = new float[3];
	private static float[] green = new float[3];

	private static Scanner s = new Scanner(System.in);

	public static float[] getBlue() {
		return blue;
	}

	public static float[] getRed() {
		return red;
	}

	public static float[] getYellow() {
		return yellow;
	}

	public static float[] getWhite() {
		return white;
	}

	public static float[] getGray() {
		return gray;
	}

	public static float[] getBlack() {
		return black;
	}

	public static float[] getGreen() {
		return green;
	}

	private CalibrateColor() {
	}

	public static void calibrate(SampleProvider average) {

		boolean again = true;

		System.out.println("Press enter to calibrate blue...");
		s.next();
		float[] blue = new float[average.sampleSize()];
		average.fetchSample(blue, 0);
		System.out.println("Blue calibrated : " + blue[0] + ", " + blue[1]
				+ ", " + blue[2] + ".");

		System.out.println("Press enter to calibrate white...");
		s.next();
		float[] white = new float[average.sampleSize()];
		average.fetchSample(white, 0);
		System.out.println("White calibrated : " + white[0] + ", " + white[1]
				+ ", " + white[2] + ".");

		System.out.println("Press enter to calibrate yellow...");
		s.next();
		float[] yellow = new float[average.sampleSize()];
		average.fetchSample(yellow, 0);
		System.out.println("Yellow calibrated : " + yellow[0] + ", "
				+ yellow[1] + ", " + yellow[2] + ".");

		System.out.println("Press enter to calibrate red...");
		s.next();
		float[] red = new float[average.sampleSize()];
		average.fetchSample(red, 0);
		System.out.println("Red calibrated : " + red[0] + ", " + red[1] + ", "
				+ red[2] + ".");

		System.out.println("Press enter to calibrate green...");
		s.next();
		float[] green = new float[average.sampleSize()];
		average.fetchSample(green, 0);
		System.out.println("Green calibrated : " + green[0] + ", " + green[1]
				+ ", " + green[2] + ".");

		System.out.println("Press enter to calibrate black...");
		s.next();
		float[] black = new float[average.sampleSize()];
		average.fetchSample(black, 0);
		System.out.println("Black calibrated : " + black[0] + ", " + black[1]
				+ ", " + black[2] + ".");

		System.out.println("Press enter to calibrate gray...");
		s.next();
		float[] gray = new float[average.sampleSize()];
		average.fetchSample(gray, 0);
		System.out.println("Gray calibrated : " + gray[0] + ", " + gray[1]
				+ ", " + gray[2] + ".");

		while (again) {
			float[] sample = new float[average.sampleSize()];
			System.out.println("\nPress enter to detect a color...");

			s.next();
			average.fetchSample(sample, 0);

			testColor(sample);

			System.out
					.println("Do you want to exit the test and copy data to a file? Y/N");

			if (s.next().charAt(0) == 'Y') {
				again = false;
			}
		}

		write();
	}

	public static String testColor(float[] sample) {

		double minscal = Double.MAX_VALUE;
		String color = "";
		
		System.out.println("Sample : " + sample[0] +", " + sample[1] + ", " + sample[2] + ".");

		double scalaire = Scalaire.scalaire(sample, blue);
		System.out.println("Blue : " + scalaire);
		// s.next();

		if (scalaire < minscal) {
			minscal = scalaire;
			color = "blue";
		}

		scalaire = Scalaire.scalaire(sample, red);
		System.out.println("Red : " + scalaire);
		// s.next();
		if (scalaire < minscal) {
			minscal = scalaire;
			color = "red";
		}

		scalaire = Scalaire.scalaire(sample, green);
		System.out.println("Green : " + scalaire);
		// s.next();
		if (scalaire < minscal) {
			minscal = scalaire;
			color = "green";
		}

		scalaire = Scalaire.scalaire(sample, black);
		System.out.println("Black : " + scalaire);
		// s.next();
		if (scalaire < minscal) {
			minscal = scalaire;
			color = "black";
		}

		scalaire = Scalaire.scalaire(sample, white);
		System.out.println("White : " + scalaire);
		// s.next();
		if (scalaire < minscal) {
			minscal = scalaire;
			color = "white";
		}

		scalaire = Scalaire.scalaire(sample, getYellow());
		System.out.println("Yellow : " + scalaire);
		// s.next();
		if (scalaire < minscal) {
			minscal = scalaire;
			color = "yellow";
		}

		scalaire = Scalaire.scalaire(sample, gray);
		System.out.println("Gray : " + scalaire);
		// s.next();
		if (scalaire < minscal) {
			minscal = scalaire;
			color = "gray";
		}

		System.out.println("The color is " + color + " \n");
		return color;
	}

	private static void write() {
		/** Sortir les valeurs dans un fichier */

		PrintWriter w;
		try {
			w = new PrintWriter("colors.data2", "UTF-8");
			try {

				for (int i = 0; i < 3; ++i) {
					w.println("blue" + i + "=" + blue[i]);
					System.out.println("blue" + i + "=" + blue[i]);
				}
				for (int i = 0; i < 3; ++i) {
					w.println("red" + i + "=" + red[i]);
					System.out.println("red" + i + "=" + red[i]);
				}
				for (int i = 0; i < 3; ++i) {
					w.println("yellow" + i + "=" + yellow[i]);
					System.out.println("yellow" + i + "=" + yellow[i]);
				}
				for (int i = 0; i < 3; ++i) {
					w.println("white" + i + "=" + white[i]);
					System.out.println("white" + i + "=" + white[i]);
				}
				for (int i = 0; i < 3; ++i) {
					w.println("gray" + i + "=" + gray[i]);
					System.out.println("gray" + i + "=" + gray[i]);
				}
				for (int i = 0; i < 3; ++i) {
					w.println("black" + i + "=" + black[i]);
					System.out.println("black" + i + "=" + black[i]);
				}
				for (int i = 0; i < 3; ++i) {
					w.println("green" + i + "=" + green[i]);
					System.out.println("green" + i + "=" + green[i]);
				}
				w.close();

			} catch (Exception io) {
				io.printStackTrace();
			} finally {
				if (w != null) {
					try {
						w.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public static void load() {
		float[] colors = new float[21];
		try (BufferedReader br = new BufferedReader(new FileReader(
				"colors.data"))) {

			String sCurrentLine;

			for (int i = 0; (sCurrentLine = br.readLine()) != null && i < 21; ++i) {
				float current = Float.parseFloat(sCurrentLine
						.substring(sCurrentLine.lastIndexOf("=") + 1));
				System.out.println(current);
				colors[i] = current;
			}

			int i = 0;

			for (int j = 0; i < 3; ++i, ++j) {
				blue[j] = colors[i];
			}
			for (int j = 0; i < 6; ++i, ++j) {
				red[j] = colors[i];
			}
			for (int j = 0; i < 9; ++i, ++j) {
				yellow[j] = colors[i];
			}
			for (int j = 0; i < 12; ++i, ++j) {
				white[j] = colors[i];
			}
			for (int j = 0; i < 15; ++i, ++j) {
				gray[j] = colors[i];
			}
			for (int j = 0; i < 18; ++i, ++j) {
				black[j] = colors[i];
			}
			for (int j = 0; i < 21; ++i, ++j) {
				green[j] = colors[i];
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
