package ev3dev.java.walle.sensors;
import ev3dev.hardware.sensor.ev3.EV3TouchSensor;

/**
 * Etends le EV3TouchSensor pour fournir isPressed().
 */

public class TouchSensor extends EV3TouchSensor
{
    public TouchSensor(String port)
    {
        super(port);
    }

    /**
     * Indique si le touchSensor est enfoncé.
     * @return Vrai si le touchSensor est enfoncé.
     */
    public boolean isPressed()
    {
        float[] sample = new float[1];
        fetchSample(sample, 0);

        return sample[0] != 0;
    }
}