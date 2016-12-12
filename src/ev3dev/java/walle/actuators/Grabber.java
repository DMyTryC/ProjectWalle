package ev3dev.java.walle.actuators;

import lejos.utility.Delay;
import ev3dev.hardware.motor.EV3MediumRegulatedMotor;

/**
 * Classe qu gère la pince du robot1
 */
public class Grabber {

	private EV3MediumRegulatedMotor gm;
	private static int SPEED = 1000;
	private boolean isOpen;
	

	/**
	 * Connecte et calibre le hardware
	 * @param motorPort Le port du moteur
	 */
	public Grabber(String motorPort) {
		gm = new EV3MediumRegulatedMotor(motorPort);
		init();
	}

	/**
	 * Set la vitesse du moteur à SPEED.
	 */
	private void set() {
		gm.setSpeed(SPEED);
		System.out.println("Speed has been set to " + gm.getSpeed());
	}
	
	/**
	 * Place la pince en position initiale et règle les distance d'ouverture et fermeture
	 */
	private void init() {
		
		set();
		System.out.println("INIT...");
	    
		gm.forward();
	    Delay.msDelay(200);
		do {
			gm.forward();
			Delay.msDelay(200);
			System.out.print("\r"+gm.isStalled());
		} while (!(gm.isStalled()));
	    System.out.println("\nSTALLED.");
		
	    gm.resetTachoCount();
		System.out.println("RESET COUNT. Position = " + gm.getTachoCount());
		
		set();
		open();
	}
	
	/**
	 * Ouvre la pince.
	 */
	public void open(){
		System.out.println("OPENNING...");
		gm.rotateTo(-2500, true);
		while(gm.isMoving())
			if (gm.isStalled())
				gm.stop();
		System.out.println("OPENED. Position = " + gm.getTachoCount());
		isOpen = true;
	}
	
	/**
	 * Ferme la pince.
	 */
	public void close(){
		System.out.println("CLOSING...");
		gm.rotateTo(-3300, true);
		while(gm.isMoving())
			if (gm.isStalled())
				gm.stop();
		System.out.println("CLOSED. Position = " + gm.getTachoCount());
		isOpen = false;
	}
	
	/**
	 * Indique si la pince est ouverte.
	 * @return Vrai si la pince est ouverte.
	 */
	public boolean isOpen(){
		return isOpen;
	}
	
	/**
	 * Change la vitesse.
	 * @param speed La nouvelle vitesse
	 */
	private void changeSpeed(int speed){
		SPEED = speed;
		set();
	}
}