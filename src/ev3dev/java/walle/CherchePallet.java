package ev3dev.java.walle;

import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;
import ev3dev.hardware.port.MotorPort;
import ev3dev.hardware.sensor.ev3.EV3UltrasonicSensor;
import ev3dev.java.walle.main.MainMoveAndGrab;

public class CherchePallet {

	private DifferentialDrivePos dd;
	private EV3UltrasonicSensor us;
	private TouchSensor ts;
	Grabber g;

	private float d1 = 0;
	private float d2 = 0;
	private static final double ERROR = 0.15;
	private static final double ERROR2 = 0.20;

	public CherchePallet(String left_port, String right_port, String sensor_port, String touch_port, String grabber_port) {
		dd = new DifferentialDrivePos(left_port, right_port);
		us = new EV3UltrasonicSensor(sensor_port);
		ts = new TouchSensor(touch_port);
		g = new Grabber(grabber_port);
	}

	public void run() {
		dd.rotate(1550);
		
		SampleProvider distance = us.getDistanceMode();
		MeanFilter mf = new MeanFilter(distance,4);
		float[] sample = new float[distance.sampleSize()];
		
		while(dd.isMoving()){
			mf.fetchSample(sample, 0);
			d1 = sample[0];
			double abs = Math.abs(d1 - d2);
			double rel = abs/d1;
			System.out.println("Rel : " + rel);
			
			if (d2 != 0) {
				if (d2 > d1 && rel > ERROR && rel < ERROR2 ) {
					System.out.println("Trouve");
					dd.stop();
					dd.rotate(-100);
					g.open();
					while(!ts.isPressed()){
						dd.forward();
					}
					dd.stop();
					g.close();
					break;
				}
			}
			d2 = d1;
		}
	}
}
