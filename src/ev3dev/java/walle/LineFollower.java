package ev3dev.java.walle;
import ev3dev.hardware.port.MotorPort;
import ev3dev.hardware.port.SensorPort;


public class LineFollower
{
    public static void main(String[] args)
    {
        Controller controller = new Controller(SensorPort.S4, MotorPort.B, MotorPort.C);
        
        controller.run();
        
    }
}