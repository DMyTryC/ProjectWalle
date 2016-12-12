package ev3dev.java.walle.actuators;

import lejos.utility.Delay;
import ev3dev.hardware.motor.EV3LargeRegulatedMotor;

/**
 * Classe de controle du dГ©placement du robot.
 */

public class DifferentialDrive {
	private EV3LargeRegulatedMotor mLeftMotor;
	private EV3LargeRegulatedMotor mRightMotor;

	private static int SPEED = 500;
	private final static int DELAY = 2000;
	private static int RAMP_UP = 1500;
	private static int RAMP_DOWN = 100;
	private int angleactuel = 0;

	/**
	 * Connecte le hardware.
	 * 
	 * @param left_port
	 *            Port de la roue gauche
	 * @param right_port
	 *            Port de la roue droite
	 */
	public DifferentialDrive(String left_port, String right_port) {
		mLeftMotor = new EV3LargeRegulatedMotor(left_port);
		mRightMotor = new EV3LargeRegulatedMotor(right_port);
		mLeftMotor.resetTachoCount();
		mRightMotor.resetTachoCount();
		set();
	}

	/**
	 * Set la vitesse et les rampspeed Г  SPEED et RAMP.
	 */
	private void set() {
		System.out.println("[Setting]\tSetting phase :");
		System.out.println("[Setting]\tLeft speed = " + mLeftMotor.getSpeed());
		System.out
				.println("[Setting]\tRight speed = " + mRightMotor.getSpeed());

		mLeftMotor.setSpeed(SPEED);
		System.out.println("[Setting]\tLeft speed has been set to "
				+ mLeftMotor.getSpeed());
		Delay.msDelay(200);
		mRightMotor.setSpeed(SPEED);
		System.out.println("[Setting]\tRight speed has been set to "
				+ mRightMotor.getSpeed());

		mLeftMotor.setRampUp(RAMP_UP);
		System.out.println("[Setting]\tLeft rampup speed has been set to "
				+ mLeftMotor.getRampUp());

		mRightMotor.setRampUp(RAMP_UP);
		System.out.println("[Setting]\tRight rampup speed has been set to "
				+ mRightMotor.getRampUp());

		mLeftMotor.setRampDown(RAMP_DOWN);
		System.out.println("[Setting]\tLeft rampdown speed has been set to "
				+ mLeftMotor.getRampDown());

		mRightMotor.setRampDown(RAMP_DOWN);
		System.out.println("[Setting]\tRight rampdown speed has been set to "
				+ mRightMotor.getRampDown());

		System.out.println("[Setting]\tSetting phase over.");
	}

	/**
	 * Test les differentes fonctionalités.
	 */
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

	/**
	 * Va tout droit sans s'arrГЄter.
	 */
	public void forward() {
		//set();
		System.out.println("[Moving]\tGoing forward.");
		mLeftMotor.forward();
		mRightMotor.forward();
	}

	/**
	 * Recule sans s'arrГЄter.
	 */
	public void backward() {
		//set();
		System.out.println("[Moving]\tGoing backward.");
		mLeftMotor.backward();
		mRightMotor.backward();
	}

	/** Virage **/

	/**
	 * Tourne d'un angle prГ©dГ©fini sans s'arrГЄter
	 * 
	 * @param ratio
	 *            Ratio de vitesse entre la roue gauche et la roue droite. La
	 *            vitesse de la roue droite est divisГ©e par ratio.
	 */
	public void turn(int ratio) {
		set();
		if (ratio > 0) {
			System.out.println("[Moving]\tTurning right.");
			mRightMotor.setSpeed(SPEED / ratio);

			mLeftMotor.forward();
			mRightMotor.forward();
		} else if (ratio < 0) {
			System.out.println("[Moving]\tTurning left.");
			mLeftMotor.setSpeed(SPEED / ratio);

			mRightMotor.forward();
			mLeftMotor.forward();
		} else
			;
	}

	/** Rotation **/

	/**
	 * Tourne sur soi-mГЄme dans le sens horraire sans s'arrГЄter.
	 */
	public void rotateClockwise() {
		//set();
		System.out.println("[Moving]\tRotating Clockwise.");
		mLeftMotor.forward();
		mRightMotor.backward();
	}

	/**
	 * Tourne sur soi-mГЄme dans le sens anti-horraire sans s'arrГЄter.
	 */
	public void rotateCounterClockwise() {
		//set();
		System.out.println("[Moving]\tRotating Counter Clockwise.");
		mLeftMotor.backward();
		mRightMotor.forward();
	}

	/*****************************
	 * Relative-types fonctions
	 *****************************/

	/** Ligne droite **/

	/**
	 * Avance tout droit d'un angle de roue dГ©terminГ©. Ne retourne qu'Г  la
	 * fin du mouvement.
	 * 
	 * @param angle
	 *            Angle de rotation des roues.
	 */
	public void goTo(int angle) {
		goTo(angle, false);
	}

	/**
	 * Avance tout droit d'un angle de roue dГ©terminГ©. Retourne en fonction du
	 * paramГЁtre instantreturn.
	 * 
	 * @param angle
	 *            Angle de rotation des roues
	 * @param instantreturn
	 *            indique si le programme doit retourner sans attendre la fin du
	 *            mouvement.
	 */
	public void goTo(int angle, boolean instantreturn) {
		//set();
		System.out.println("[Moving]\tGoing straight for " + angle
				+ " degrees.");
		mRightMotor.rotate(angle, true);
		mLeftMotor.rotate(angle, instantreturn);
		if (!instantreturn) {
			System.out.println("[Stop]\t\tStopping...");
		}
	}

	/** Virage **/

	/**
	 * Tourne d'un angle prГ©dГ©fini sans s'arrГЄter. Retourne Г  la fin du
	 * mouvement.
	 * 
	 * @param angle
	 *            Angle de rotation de la roue gauche.
	 * @param ratio
	 *            Ratio de vitesse entre la roue gauche et la roue droite. La
	 *            vitesse de la roue droite est divisГ©e par ratio.
	 */
	public void turnTo(int angle, int ratio) {
		turnTo(angle, ratio, false);
	}

	/**
	 * Tourne d'un angle prГ©dГ©fini sans s'arrГЄter. Retourne Г  la fin du
	 * mouvement. Retourne en fonction du paramГЁtre instantreturn.
	 * 
	 * @param angle
	 *            Angle de rotation de la roue gauche.
	 * @param ratio
	 *            Ratio de vitesse entre la roue gauche et la roue droite. La
	 *            vitesse de la roue droite est divisГ©e par ratio.
	 * @param instantreturn
	 *            indique si le programme doit retourner sans attendre la fin du
	 *            mouvement.
	 */
	public void turnTo(int angle, int ratio, boolean instantreturn) {
		//set();
		if (ratio > 0) {
			System.out.println("[Moving]\tTrurning right of " + angle
					+ " degrees.");
			mRightMotor.setSpeed(SPEED / ratio);
			System.out.println("[Setting]\tSpeed of right wheel setted to : "
					+ mRightMotor.getSpeed());

			mLeftMotor.rotate(angle, true);
			mRightMotor.rotate(angle / ratio, instantreturn);
			if (!instantreturn) {
				System.out.println("[Stop]\t\tStopping...");
			}
		} else if (ratio < 0) {
			System.out.println("[Moving]\tTrurning left of " + angle
					+ " degrees.");
			mLeftMotor.setSpeed(SPEED / ratio);
			System.out.println("[Setting]\tSpeed of right wheel setted to : "
					+ mRightMotor.getSpeed());

			mRightMotor.rotate(angle, true);
			mLeftMotor.rotate(angle / ratio, instantreturn);
			if (!instantreturn) {
				System.out.println("[Stop]\t\tStopping...");
			}
		} else
			;
	}

	/** Rotation **/

	/**
	 * Tourne sur soi-mГЄme d'un angle dГ©terminГ©. Retourne Г  la fin du
	 * mouvement.
	 * 
	 * @param angle
	 *            Angle des roues dans le sens horraire.
	 */
	public void rotateTo(int angle) {
		rotateTo(angle, false);
	}

	/**
	 * Tourne sur soi-mГЄme d'un angle dГ©terminГ© Retourne en fonction du
	 * paramГЁtre instantreturn.
	 * 
	 * @param angle
	 *            Angle des roues dans le sens horraire.
	 * @param instantreturn
	 *            indique si le programme doit retourner sans attendre la fin du
	 *            mouvement.
	 */

	public void rotateTo(int angle, boolean instantreturn) {
		//set();
		System.out.println("[Moving]\tRotating of " + angle + " degrees.");
		mLeftMotor.rotate(angle, true);
		mRightMotor.rotate(-angle, instantreturn);

		angleactuel += angle;
		angleactuel %= 360;
		if (!instantreturn) {
			System.out.println("[Stop]\t\tStopping...");
		}
	}

	/**
	 * Methode qui permet de retourner a la position 0
	 */
	public void goBackToZero() {
		//set();
		changeSpeed(300,1000,1000);
/**		angleactuel = mLeftMotor.getTachoCount() % 360;

		mLeftMotor.rotate(-angleactuel, true);
		mRightMotor.rotate(angleactuel, false);

		angleactuel = 0; */
		
		angleactuel = (mLeftMotor.getTachoCount() - mRightMotor.getTachoCount()) % (775*2);
		angleactuel /= 2;
		System.out.println(angleactuel);
		mRightMotor.rotate(angleactuel, true);
		mLeftMotor.rotate(-angleactuel, false);
		angleactuel = 0;
		mLeftMotor.rotate(-50,true);
		mRightMotor.rotateTo(50);
		mLeftMotor.resetTachoCount();
		mRightMotor.resetTachoCount();
		
		
		
		changeSpeed(500, 1500, 250);
		System.out.println("[Stop]\t\tStopping...");
	}

	/** Stop **/

	/**
	 * ArrГЄte les moteurs.
	 */
	public void stop() {
		System.out.println("[Stop]\t\tStopping...");
		if (mLeftMotor.getSpeed() > mRightMotor.getSpeed()) {
			mLeftMotor.stop();
			mRightMotor.stop();
		} else {
			mRightMotor.stop();
			mLeftMotor.stop();
		}
		angleactuel = mRightMotor.getTachoCount()%360;
	}

	/**
	 * Inidique si un des moteurs est en mouvement.
	 * 
	 * @return Vrai si un des moteurs tourne.
	 */
	public boolean isMoving() {
		return ((mLeftMotor.isMoving()) || (mRightMotor.isMoving()));
	}

	/**
	 * Change la vitesse et le RampTime
	 * 
	 * @param speed
	 *            La nouvelle vitesse
	 * @param time
	 *            Le nouveau RampTime
	 */
	public void changeSpeed(int speed, int timeUp, int timeDown) {
		SPEED = speed;
		RAMP_UP = timeUp;
		RAMP_DOWN = timeDown;
		set();
	}

	/**
	 * Methode pour tester la rotation/ la methode goBackToZero()
	 */
	public void testRotation() {
		changeSpeed(500, 2000, 1000);
		rotateTo(1000, true);
		for (int i = 40; isMoving() && i > 0; i--) {
			System.out.println(i);
		}
		stop();
		while(isMoving()) {
			
		}
		goBackToZero();
		Delay.msDelay(5000);
		rotateTo(1700);
		goBackToZero();
	}
}
