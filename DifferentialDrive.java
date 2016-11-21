package ev3dev.java.walle.actuators;
import lejos.utility.Delay;
import ev3dev.hardware.motor.EV3LargeRegulatedMotor;

public class DifferentialDrive
{
    private EV3LargeRegulatedMotor mLeftMotor;
    private EV3LargeRegulatedMotor mRightMotor;

	private final static int SPEED = 500;
	private final static int DELAY = 2000;
	private final static int RAMP = 1500;
	
    public DifferentialDrive(String left_port, String right_port) {
        mLeftMotor = new EV3LargeRegulatedMotor(left_port);
        mRightMotor = new EV3LargeRegulatedMotor(right_port);

        set();
    }
	
    private void set() {
    	System.out.println("[Setting]\tSetting phase :");
		System.out.println("[Setting]\tLeft speed = " + mLeftMotor.getSpeed());
		System.out.println("[Setting]\tRight speed = " + mRightMotor.getSpeed());
	
		mLeftMotor.setSpeed(SPEED);
		mRightMotor.setSpeed(SPEED);
		mLeftMotor.setRampUp(RAMP);
		mRightMotor.setRampUp(RAMP);
		mLeftMotor.setRampDown(RAMP);
		mRightMotor.setRampDown(RAMP);
	
		System.out.println("[Setting]\tLeft speed has been set to " + mLeftMotor.getSpeed());
		System.out.println("[Setting]\tRight speed has been set to " + mRightMotor.getSpeed());
		System.out.println("[Setting]\tSetting phase over.");
	}

    public void test() {
    	System.out.println("\n*******\nTESTING FORWARD COMMAND\n*******\n\n");
    	
    	System.out.println("\n----- Straight line ------\n");
    	
    	Delay.msDelay(DELAY);
    	
    	forward();
    	Delay.msDelay(DELAY);
    	stop();
    	
    	Delay.msDelay(DELAY);
    	
    	backward();
    	Delay.msDelay(DELAY);
    	stop();
    	
    	Delay.msDelay(DELAY);
    	
       	System.out.println("\n----- Curved line ------\n");
       	
       	Delay.msDelay(DELAY);
    	
       	turn(2);
       	Delay.msDelay(DELAY);
       	stop();
       	
       	Delay.msDelay(DELAY);
       	
       	turn(-2);
       	Delay.msDelay(DELAY);
       	stop();
       	
       	Delay.msDelay(DELAY);
       	
       	System.out.println("\n----- Rotation ------\n");
    	
       	Delay.msDelay(DELAY);
       	
    	rotateClockwise();
    	Delay.msDelay(DELAY);
    	stop();
    	
    	Delay.msDelay(DELAY);
    	
    	rotateCounterClockwise();
    	Delay.msDelay(DELAY);
    	stop();
    	
    	Delay.msDelay(DELAY);
    	
    	System.out.println("\n*******\nFORWARD COMMANDS : DONE\n*******\n\n");
    	
    	Delay.msDelay(DELAY);
    	 	
    	System.out.println("\n*******\nTESTING RELATIVE COMMAND\n*******\n\n");

    	System.out.println("\n----- Straight line ------\n");
    	
    	Delay.msDelay(DELAY);
    	
    	goTo(360);
    	
    	Delay.msDelay(DELAY);
    	
    	goTo(-360);
    	
    	Delay.msDelay(DELAY);
    	
       	System.out.println("\n----- Curved line ------\n");
       	
       	Delay.msDelay(DELAY);
    	
       	turnTo(360, 2);
       	
       	Delay.msDelay(DELAY);
       	
    	turnTo(360, -2);
    	
       	Delay.msDelay(DELAY);
       	
       	System.out.println("\n----- Rotation ------\n");
    	
       	Delay.msDelay(DELAY);
       	
    	rotateTo(360);
    	
    	Delay.msDelay(DELAY);
    	
    	rotateTo(-360);
    	
    	Delay.msDelay(DELAY);
    	
    	System.out.println("\n*******\nRELATIVE COMMANDS : DONE\n*******\n\n"); 
    }
    
    /***************************
     * Forward-types fonctions
     ***************************/
    
    /** Ligne droite **/
    
    
	public void forward()
	{
		set();
		System.out.println("[Moving]\tGoing forward.");
	    mLeftMotor.forward();
	    mRightMotor.forward();
	}
	
	public void backward()
	{
		set();
		System.out.println("[Moving]\tGoing backward.");
	    mLeftMotor.backward();
	    mRightMotor.backward();
	}
	
	/** Virage **/
	
	public void turn(int ratio) {
		set();
		if (ratio > 0) {
			System.out.println("[Moving]\tTurning right.");
			mRightMotor.setSpeed(SPEED/ratio);
			
			mLeftMotor.forward();
			mRightMotor.forward();
		}
		else if (ratio < 0) {
			System.out.println("[Moving]\tTurning left.");
			mLeftMotor.setSpeed(SPEED/ratio);
			
			mRightMotor.forward();
			mLeftMotor.forward();
		}
		else;
	}

	/** Rotation **/
	
	public void rotateClockwise()
	{
		set();
		System.out.println("[Moving]\tRotating Clockwise.");
	    mLeftMotor.forward();
	    mRightMotor.backward();
	}

	public void rotateCounterClockwise()
	{
		set();
		System.out.println("[Moving]\tRotating Counter Clockwise.");
	    mLeftMotor.backward();
	    mRightMotor.forward();
	}

	/*****************************
	 *  Relative-types fonctions 
	 *****************************/

	/** Ligne droite **/
	
	public void goTo (int angle) {
		goTo(angle, false);
	}
	
	public void goTo (int angle, boolean waitforreturn) {
		set();
		System.out.println("[Moving]\tGoing straight for " + angle + " degrees.");
		mRightMotor.rotate(angle, true);
		mLeftMotor.rotate(angle, waitforreturn);
		if (!waitforreturn) {
			System.out.println("[Stop]\t\tStopping...");
		}
	}
	
	/** Virage **/
	
	public void turnTo(int angle, int ratio) {
		turnTo(angle, ratio, false);
	}
	
	public void turnTo(int angle, int ratio, boolean waitforreturn) {
		set();
		if(ratio > 0) {
			System.out.println("[Moving]\tTrurning right of " + angle + " degrees.");
			mRightMotor.setSpeed(SPEED/ratio);
			System.out.println("[Setting]\tSpeed of right wheel setted to : " + mRightMotor.getSpeed());
			
			mLeftMotor.rotate(angle, true);
			mRightMotor.rotate(angle/ratio, waitforreturn);
			if (!waitforreturn) {
				System.out.println("[Stop]\t\tStopping...");
			}
		}
		else if (ratio < 0) {
			System.out.println("[Moving]\tTrurning left of " + angle + " degrees.");
			mLeftMotor.setSpeed(SPEED/ratio);
			System.out.println("[Setting]\tSpeed of right wheel setted to : " + mRightMotor.getSpeed());
			
			mRightMotor.rotate(angle, true);
			mLeftMotor.rotate(angle/ratio, waitforreturn);
			if (!waitforreturn) {
				System.out.println("[Stop]\t\tStopping...");
			}
		}
		else;
	}

	/** Rotation **/
	
	public void rotateTo(int angle) {
		rotateTo(angle, false);
	}

	public void rotateTo(int angle, boolean waitforreturn) {
		set();
		System.out.println("[Moving]\tRotating of " + angle + " degrees.");
		mLeftMotor.rotate(angle, true);
		mRightMotor.rotate(-angle, waitforreturn);
		if (!waitforreturn) {
			System.out.println("[Stop]\t\tStopping...");
		}
	}
	
	/** Stop **/
	
	public void stop() {
		System.out.println("[Stop]\t\tStopping...");
		if (mLeftMotor.getSpeed() > mRightMotor.getSpeed()) {
			mLeftMotor.stop();
			mRightMotor.stop();
		}
		else {
			mRightMotor.stop();
			mLeftMotor.stop();
		}
	}
}
