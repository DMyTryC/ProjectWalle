package ev3dev.java.walle;

import lejos.utility.Delay;
import ev3dev.hardware.port.MotorPort;

public class Tourne {
	public static void main(String[] args) {
		DifferentialDrivePos dd = new DifferentialDrivePos(MotorPort.B, MotorPort.C);

		dd.rotate(775);
	}
}
