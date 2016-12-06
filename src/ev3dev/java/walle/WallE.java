package ev3dev.java.walle;

import java.util.Scanner;

import ev3dev.java.walle.robot.Robot;

public class WallE {

	public static void main(String[] args) {
		Robot r = new Robot();
		boolean go = true;
		Scanner s = new Scanner(System.in);
		while (go) {
			System.out.println("Choisissez le programme a exectuer : ");
			System.out.println("1 : Tester les couleurs");
			System.out.println("2 : Tester les moteurs");
			System.out.println("3 : Recalibrer les couleurs");
			System.out.println("4 : Tester DifferentialDrive");
			System.out.println("5 : Tester cherchePalet");

			int choix = Integer.parseInt(s.nextLine());

			switch (choix) {
			case 1:
				r.testColor();
				break;
			case 2:
				r.variousTest();
				break;
			case 3:
				r.reCalibrate();
				break;
			case 4:
				r.ddTest();
				break;
			case 5:
				r.cherchePalet();
				break;
			}
			
			System.out.println("Voulez vous continuer ? Y/N");
			go = (s.nextLine() == "Y");
		}
		s.close();
	}
}
