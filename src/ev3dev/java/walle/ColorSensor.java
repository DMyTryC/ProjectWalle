package ev3dev.java.walle;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;
import ev3dev.hardware.sensor.ev3.EV3ColorSensor;
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
		System.out.println("Press enter to calibrate path color...");
		sc.next();
		path_color = new float[average.sampleSize()];
		average.fetchSample(path_color, 0);
    }
	
    public boolean onPath()
    {
    	float[] sample = new float[average.sampleSize()];
		average.fetchSample(sample, 0);
		
		double scalaire = scalaire(sample, path_color);

		System.out.println("Scalaire : " + scalaire );
		System.out.println();
		
		System.out.println(scalaire < ERROR);
		
		return scalaire(sample, path_color) < ERROR;
    	
    }
    
    public static double scalaire(float[] v1, float[] v2) {
		return Math.sqrt (Math.pow(v1[0] - v2[0], 2.0) +
				Math.pow(v1[1] - v2[1], 2.0) +
				Math.pow(v1[2] - v2[2], 2.0));
	}
    
}
