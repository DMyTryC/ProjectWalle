package ev3dev.java.walle;
import ev3dev.hardware.motor.EV3LargeRegulatedMotor;

public class DifferentialDrive
{
    private EV3LargeRegulatedMotor mLeftMotor;
    private EV3LargeRegulatedMotor mRightMotor;

    private final static int SPEED = 100;


    public DifferentialDrive(String left_port, String right_port)
    {
        mLeftMotor = new EV3LargeRegulatedMotor(left_port);
        mRightMotor = new EV3LargeRegulatedMotor(right_port);

        mLeftMotor.setSpeed(SPEED);
        mRightMotor.setSpeed(SPEED);
    }


    public void forward()
    {
        mLeftMotor.forward();
        mRightMotor.forward();
    }


    public void stop()
    {
        mLeftMotor.stop();
        mRightMotor.stop();
    }


    public void rotateClockwise()
    {
    	mLeftMotor.setSpeed(20);
    	mRightMotor.setSpeed(20);
    	mLeftMotor.setRampUp(200);
    	mRightMotor.setRampUp(200);
    	mLeftMotor.setRampDown(200);
    	mRightMotor.setRampDown(200);
        mLeftMotor.forward();
        mRightMotor.backward();
        mLeftMotor.setSpeed(SPEED);
        mRightMotor.setSpeed(SPEED);
    }


    public void rotateCounterClockwise()
    {
    	mLeftMotor.setSpeed(20);
    	mRightMotor.setSpeed(20);
    	mLeftMotor.setRampUp(200);
    	mRightMotor.setRampUp(200);
    	mLeftMotor.setRampDown(200);
    	mRightMotor.setRampDown(200);
        mLeftMotor.backward();
        mRightMotor.forward();
        mLeftMotor.setSpeed(SPEED);
        mRightMotor.setSpeed(SPEED);
    }
}
