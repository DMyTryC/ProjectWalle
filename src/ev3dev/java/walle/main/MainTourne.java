package ev3dev.java.walle.main;

import ev3dev.hardware.port.MotorPort;
import ev3dev.java.walle.DifferentialDrivePos;

public class MainTourne {
	public static void main(String[] args) {
		DifferentialDrivePos dd = new DifferentialDrivePos(MotorPort.B, MotorPort.C);

		dd.rotate(775);
	}
}
