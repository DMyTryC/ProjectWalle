package ev3dev.java.walle;
import ev3dev.hardware.sensor.ev3.EV3TouchSensor;

/**
 * Extends the EV3TouchSensor to provide it with isPressed() functionality.
 */

public class TouchSensor extends EV3TouchSensor
{
    public TouchSensor(String port)
    {
        super(port);
    }

    public boolean isPressed()
    {
        float[] sample = new float[1];
        fetchSample(sample, 0);

        return sample[0] != 0;
    }
}
