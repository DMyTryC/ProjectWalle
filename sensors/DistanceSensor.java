package ev3dev.java.walle.sensors;
import ev3dev.hardware.sensor.ev3.EV3UltrasonicSensor;

/**
 * TODO : Intégrer le travail fait dans cherchePalet par @Dimitri
 */

public class DistanceSensor 
{
	@SuppressWarnings("unused")
	private EV3UltrasonicSensor us;
	
    public DistanceSensor(String port)
    {
        us = new EV3UltrasonicSensor(port);
    }

    public int getDistance()
    {
    	return 0;
    }
}
