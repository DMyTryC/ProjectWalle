package ev3dev.java.walle;

import lejos.robotics.SampleProvider;
import ev3dev.hardware.port.MotorPort;
import ev3dev.hardware.port.SensorPort;
import ev3dev.hardware.sensor.ev3.EV3UltrasonicSensor;

public class CherchePallet {

	DifferentialDrivePos dd;
	EV3UltrasonicSensor us;
	
	private float d1 = 0;
	private float d2 = 0;

	public CherchePallet() {
		dd = new DifferentialDrivePos(MotorPort.B, MotorPort.C);
		us = new EV3UltrasonicSensor(SensorPort.S2);
	}

	public void run() {
		dd.rotate(775);

		// get an instance of this sensor in measurement mode
		SampleProvider distance = us.getDistanceMode();

		// initialise an array of floats for fetching samples
		float[] sample = new float[distance.sampleSize()];

		// fetch a sample
		for (int i = 0; i < 50; i++) {
			distance.fetchSample(sample, 0);
			System.out.println("Dist " + sample[0]);
			d1 = sample[0];
			if (d2 != 0){
				float diff = Math.abs(d1 - d2);
				System.out.println("diff" + diff);
				if (diff > 80){
					dd.stop();
				}
			}
			d2 = d1;
		}
	}

	public static void main(String[] args) {
		CherchePallet c = new CherchePallet();
		c.run();
	}

}
