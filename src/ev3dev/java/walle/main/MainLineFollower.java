package ev3dev.java.walle.main;
import ev3dev.hardware.port.MotorPort;
import ev3dev.hardware.port.SensorPort;
import ev3dev.java.walle.Controller;


public class MainLineFollower
{
    public static void main(String[] args)
    {
        Controller controller = new Controller(SensorPort.S4, MotorPort.B, MotorPort.C);

        controller.run();  
    }
}