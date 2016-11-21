package ev3dev.java.walle.sensors;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;
import ev3dev.hardware.sensor.ev3.EV3ColorSensor;
import ev3dev.java.walle.utils.Scalaire;

import java.util.Scanner;

public class ColorSensor
{
    private static float[] path_color;
    private static SampleProvider average;
    private static EV3ColorSensor colorSensor;
    private final static double ERROR = 30;
    Scanner sc = new Scanner(System.in);

    public ColorSensor(String port)
    {
    	colorSensor = new EV3ColorSensor(port);
    	average = new MeanFilter(colorSensor.getRGBMode(), 10);
		colorSensor.setFloodlight(Color.WHITE);
		
		System.out.println("Do you want to load direcltly or recalibrate ? L/C");
		if (sc.next().charAt(0) == 'L')
			load();
		else {
			calibrate();
			load();
		}
    }
	
    public boolean onPath()
    {
    	float[] sample = new float[average.sampleSize()];
		average.fetchSample(sample, 0);
		
		double scalaire = Scalaire.scalaire(sample, path_color);

		System.out.println("Scalaire : " + scalaire );
		System.out.println();
		
		System.out.println(scalaire < ERROR);
		
		return Scalaire.scalaire(sample, path_color) < ERROR;
    	
    }
    
    public void calibrate() {
    	CalibrateColor.calibrate(average);
    }
    
    public void load() {
    	CalibrateColor.load();
    }
    
}
