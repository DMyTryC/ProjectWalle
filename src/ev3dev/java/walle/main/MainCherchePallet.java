package ev3dev.java.walle.main;

import ev3dev.hardware.port.MotorPort;
import ev3dev.hardware.port.SensorPort;
import ev3dev.java.walle.CherchePallet;

public class MainCherchePallet {
	public static void main(String[] args) {
		CherchePallet c = new CherchePallet(MotorPort.B, MotorPort.C,SensorPort.S2, SensorPort.S1,MotorPort.D);
		
		c.run();
	}
}
