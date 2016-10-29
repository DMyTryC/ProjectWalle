package ev3dev.java.walle;

import ev3dev.hardware.motor.EV3LargeRegulatedMotor;

public class DifferentialDrivePos {
	private EV3LargeRegulatedMotor mLeftMotor;
	private EV3LargeRegulatedMotor mRightMotor;
	private final static int SPEED = 200;
	private final static int RAMP = 2000;

	public DifferentialDrivePos(String left_port, String right_port) {
		mLeftMotor = new EV3LargeRegulatedMotor(left_port);
		mRightMotor = new EV3LargeRegulatedMotor(right_port);

		mLeftMotor.setSpeed(SPEED);
		mRightMotor.setSpeed(SPEED);
	}
	
	public void stop(){
		mLeftMotor.stop();
		mRightMotor.stop();
	}

	public void rotate(int angle) {
		mLeftMotor.setSpeed(SPEED);
		mRightMotor.setSpeed(SPEED);
		mLeftMotor.setRampUp(RAMP);
		mRightMotor.setRampUp(RAMP);
		mLeftMotor.setRampDown(RAMP);
		mRightMotor.setRampDown(RAMP);
		mLeftMotor.rotate(angle, true);
		mRightMotor.rotate(-angle, true);
		System.out.println("R " + mRightMotor.getSpeed());
		System.out.println("L " + mLeftMotor.getSpeed());
	}

	public void rotateTo(int angle) {
		mLeftMotor.setSpeed(SPEED);
		mRightMotor.setSpeed(SPEED);
		mLeftMotor.setRampUp(RAMP);
		mRightMotor.setRampUp(RAMP);
		mLeftMotor.setRampDown(RAMP);
		mRightMotor.setRampDown(RAMP);
		mLeftMotor.rotateTo(angle, true);
		mRightMotor.rotateTo(-angle, true);
		System.out.println("R " + mRightMotor.getSpeed());
		System.out.println("L " + mLeftMotor.getSpeed());
	}
}
