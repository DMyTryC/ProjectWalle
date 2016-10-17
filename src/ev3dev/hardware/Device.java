package ev3dev.hardware;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Base class for sensor drivers & motors. Provides mechanism to release resources when closed
 * 
 * @author Andy Shaw
 * @author Juan Antonio Breña Moral
 *
 */
public class Device implements Closeable {

    protected ArrayList<Closeable> closeList = new ArrayList<Closeable>();

    /**
     * Add the specified resource to the list of objects that will be closed
     * when the sensor is closed.
     * @param res
     */
    protected void releaseOnClose(Closeable res)
    {
        closeList.add(res);
    }
    
    /**
     * Close the sensor. Close associated resources.

     */
    public void close()
    {
        for(Closeable res : closeList)
            try {
                res.close();
            } catch(IOException e)
            {
                // this really should not happen
                throw new DeviceException("Error during close", e);
            }
        
    }

}
