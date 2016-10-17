package ev3dev.java.walle;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Scanner;

import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;
import lejos.utility.Delay;
import ev3dev.hardware.port.SensorPort;
import ev3dev.hardware.sensor.ev3.EV3ColorSensor;

public class TestColor {
	
static Scanner s = new Scanner(System.in);

public static boolean goMessage() {
		
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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			
			boolean again = true;
			
			if (!goMessage()) System.exit(0);
			
			EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S4);

			SampleProvider average = new MeanFilter(colorSensor.getRGBMode(), 1);
			colorSensor.setFloodlight(Color.WHITE);
			
			System.out.println("Press enter to calibrate blue...");
			s.next();
			float[] blue = new float[average.sampleSize()];
			average.fetchSample(blue, 0);
			
			System.out.println("Press enter to calibrate white...");
			s.next();
			float[] white = new float[average.sampleSize()];
			average.fetchSample(white, 0);
			
			System.out.println("Press enter to calibrate yellow...");
			s.next();
			float[] yellow = new float[average.sampleSize()];
			average.fetchSample(yellow, 0);			
			
			System.out.println("Press enter to calibrate red...");
			s.next();
			float[] red = new float[average.sampleSize()];
			average.fetchSample(red, 0);
			
			System.out.println("Press enter to calibrate green...");
			s.next();
			float[] green = new float[average.sampleSize()];
			average.fetchSample(green, 0);

			System.out.println("Press enter to calibrate black...");
			s.next();
			float[] black = new float[average.sampleSize()];
			average.fetchSample(black, 0);
			System.out.println("Black calibrated");
			
			
			while (again) {
				float[] sample = new float[average.sampleSize()];
				System.out.println("\nPress enter to detect a color...");
				s.next();
				average.fetchSample(sample, 0);
				double minscal = Double.MAX_VALUE;
				String color = "";
				
				double scalaire = TestColor.scalaire(sample, blue);
				//s.next();
				//System.out.println(scalaire);
				
				if (scalaire < minscal) {
					minscal = scalaire;
					color = "blue";
				}
				
				scalaire = TestColor.scalaire(sample, red);
				//System.out.println(scalaire);
				//s.next();
				if (scalaire < minscal) {
					minscal = scalaire;
					color = "red";
				}
				
				scalaire = TestColor.scalaire(sample, green);
				//System.out.println(scalaire);
				//s.next();
				if (scalaire < minscal) {
					minscal = scalaire;
					color = "green";
				}
				
				scalaire = TestColor.scalaire(sample, black);
				//System.out.println(scalaire);
				//s.next();
				if (scalaire < minscal) {
					minscal = scalaire;
					color = "black";
				}
				
				scalaire = TestColor.scalaire(sample, white);
				//System.out.println(scalaire);
				//s.next();
				if (scalaire < minscal) {
					minscal = scalaire;
					color = "white";
				}
				
				scalaire = TestColor.scalaire(sample, yellow);
				//System.out.println(scalaire);
				//s.next();
				if (scalaire < minscal) {
					minscal = scalaire;
					color = "yellow";
				}
				
				System.out.println("The color is " + color + " \n");
				System.out.println("Do you want to exit and copy data to a file? Y/N");
				
				if(s.next().charAt(0) == 'Y') {
					colorSensor.setFloodlight(false);
					again = false;
					colorSensor.close();
				}
			}
			
			/** Sortir les valeurs dans un fichier */
			
			Properties prop = new Properties();
			OutputStream output = null;

			try {

				output = new FileOutputStream("config.properties");

				// set the properties value
				prop.setProperty("blue", blue.toString());
				prop.setProperty("red", blue.toString());
				prop.setProperty("green", blue.toString());
				prop.setProperty("black", blue.toString());
				prop.setProperty("yellow", blue.toString());
				prop.setProperty("white", blue.toString());

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
	
	public static double scalaire(float[] v1, float[] v2) {
		return Math.sqrt (Math.pow(v1[0] - v2[0], 2.0) +
				Math.pow(v1[1] - v2[1], 2.0) +
				Math.pow(v1[2] - v2[2], 2.0));
	}

}
