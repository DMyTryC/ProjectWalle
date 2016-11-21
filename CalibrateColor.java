package ev3dev.java.walle.sensors;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Scanner;

import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import ev3dev.java.walle.utils.Scalaire;

public class CalibrateColor {
	private CalibrateColor() {
		s = new Scanner(System.in);
	}

	public static void calibrate(SampleProvider average) {
		try {
			
			boolean again = true;
			
			if (!goMessage()) System.exit(0);
			
			System.out.println("Press enter to calibrate blue...");
			s.next();
			float[] blue = new float[average.sampleSize()];
			average.fetchSample(blue, 0);
			System.out.println("Blue calibrated");
			
			System.out.println("Press enter to calibrate white...");
			s.next();
			float[] white = new float[average.sampleSize()];
			average.fetchSample(white, 0);
			System.out.println("White calibrated");
			
			System.out.println("Press enter to calibrate yellow...");
			s.next();
			float[] yellow = new float[average.sampleSize()];
			average.fetchSample(yellow, 0);	
			System.out.println("Yellow calibrated");
			
			System.out.println("Press enter to calibrate red...");
			s.next();
			float[] red = new float[average.sampleSize()];
			average.fetchSample(red, 0);
			System.out.println("Red calibrated");
			
			System.out.println("Press enter to calibrate green...");
			s.next();
			float[] green = new float[average.sampleSize()];
			average.fetchSample(green, 0);
			System.out.println("Green calibrated");

			System.out.println("Press enter to calibrate black...");
			s.next();
			float[] black = new float[average.sampleSize()];
			average.fetchSample(black, 0);
			System.out.println("Black calibrated");
			
			System.out.println("Press enter to calibrate gray...");
			s.next();
			float[] gray = new float[average.sampleSize()];
			average.fetchSample(black, 0);
			System.out.println("Gray calibrated");
			
			
			while (again) {
				float[] sample = new float[average.sampleSize()];
				System.out.println("\nPress enter to detect a color...");
				s.next();
				average.fetchSample(sample, 0);
				double minscal = Double.MAX_VALUE;
				String color = "";
				
				double scalaire = Scalaire.scalaire(sample, blue);
				//s.next();
				//System.out.println(scalaire);
				
				if (scalaire < minscal) {
					minscal = scalaire;
					color = "blue";
				}
				
				scalaire = Scalaire.scalaire(sample, red);
				//System.out.println(scalaire);
				//s.next();
				if (scalaire < minscal) {
					minscal = scalaire;
					color = "red";
				}
				
				scalaire = Scalaire.scalaire(sample, green);
				//System.out.println(scalaire);
				//s.next();
				if (scalaire < minscal) {
					minscal = scalaire;
					color = "green";
				}
				
				scalaire = Scalaire.scalaire(sample, black);
				//System.out.println(scalaire);
				//s.next();
				if (scalaire < minscal) {
					minscal = scalaire;
					color = "black";
				}
				
				scalaire = Scalaire.scalaire(sample, white);
				//System.out.println(scalaire);
				//s.next();
				if (scalaire < minscal) {
					minscal = scalaire;
					color = "white";
				}
				
				scalaire = Scalaire.scalaire(sample, yellow);
				//System.out.println(scalaire);
				//s.next();
				if (scalaire < minscal) {
					minscal = scalaire;
					color = "yellow";
				}
				
				scalaire = Scalaire.scalaire(sample, gray);
				//System.out.println(scalaire);
				//s.next();
				if (scalaire < minscal) {
					minscal = scalaire;
					color = "gray";
				}
				
				System.out.println("The color is " + color + " \n");
				System.out.println("Do you want to exit and copy data to a file? Y/N");
				
				if(s.next().charAt(0) == 'Y') {
					again = false;
				}
			}
			
			/** Sortir les valeurs dans un fichier */
			
			Properties prop = new Properties();
			OutputStream output = null;

			try {

				output = new FileOutputStream("config.properties");

				// set the properties value
				
				for (int i = 0; i < 3; ++i) {
					prop.setProperty("blue"+i, ""+blue[i]);
				}
				for (int i = 0; i < 3; ++i) {
					prop.setProperty("red"+i, ""+red[i]);
				}
				for (int i = 0; i < 3; ++i) {
					prop.setProperty("yellow"+i, ""+yellow[i]);
				}
				for (int i = 0; i < 3; ++i) {
					prop.setProperty("white"+i, ""+white[i]);
				}
				for (int i = 0; i < 3; ++i) {
					prop.setProperty("gray"+i, ""+gray[i]);
				}
				for (int i = 0; i < 3; ++i) {
					prop.setProperty("black"+i, ""+black[i]);
				}
				for (int i = 0; i < 3; ++i) {
					prop.setProperty("green"+i, ""+green[i]);
				}

				// save properties to project root folder
				prop.store(output, null);

			} catch (IOException io) {
				io.printStackTrace();
			} finally {
				if (output != null) {
					try {
						output.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
		} catch (Throwable t) {
			t.printStackTrace();
			Delay.msDelay(10000);
			System.exit(0);
		}
	}
	
	public static void load() {
		
	}
	
	private static boolean goMessage() {
		
		System.out.println("The code for this sample");
		System.out.println("shows how to work with the");
		System.out.println("Color Sensor");
		System.out.println("To run the");
		System.out.println("code one needs an EV3");
		System.out.println("brick with a EV3 color sensor"); 
		System.out.println("attached to port 4.");
		  
		System.out.println("QUIT : No");
		System.out.println("GO : Yes");
		
		return (s.next().charAt(0) == 'Y');
	}

	private static Scanner s;

}