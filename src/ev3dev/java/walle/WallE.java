package ev3dev.java.walle;

import ev3dev.hardware.Battery;
import ev3dev.hardware.port.MotorPort;
import ev3dev.hardware.port.SensorPort;
import ev3dev.hardware.motor.EV3LargeRegulatedMotor;
import ev3dev.hardware.sensor.ev3.EV3IRSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class WallE {
    
    //Robot Definition
	private final static EV3LargeRegulatedMotor grab = new EV3LargeRegulatedMotor(MotorPort.D);
	private final static EV3LargeRegulatedMotor mA = new EV3LargeRegulatedMotor(MotorPort.C);
    private final static EV3LargeRegulatedMotor mB = new EV3LargeRegulatedMotor(MotorPort.B);
    private final static EV3IRSensor ir1 = new EV3IRSensor(SensorPort.S2);

    //Configuration
    private final static int motorSpeed = 500;
    private final static int rampSpeed = 2000;
    
    public static void main(String[] args) {
        
        final SampleProvider sp = ir1.getDistanceMode();
        int distance = 255;

        final int distance_threshold = 350;
        
        
        //Robot control loop
        final int iteration_threshold = 200;
        for(int i = 0; i <= iteration_threshold; i++) {
            forward();

            float [] sample = new float[sp.sampleSize()];
            sp.fetchSample(sample, 0);
            distance = (int)sample[0];
            if(distance <= distance_threshold){
                backwardWithTurn();
            }

            System.out.print("\033[0GDistance: " + distance + "    ");
        }

        mA.stop();
        mB.stop();
        System.exit(0);
    }
    
    private static void forward(){
        mA.setSpeed(motorSpeed);
        mB.setSpeed(motorSpeed);
        mA.setRampUp(rampSpeed);
        mB.setRampUp(rampSpeed);
        mA.setRampDown(rampSpeed);
        mB.setRampDown(rampSpeed);
        
        mA.forward();
        mB.forward();
    }
    
    private static void backwardWithTurn(){
        mA.backward();
        mB.backward();
        Delay.msDelay(1000);
        mA.stop();
        mB.stop();
        mA.backward();
        mB.forward();
        Delay.msDelay(1000);
        mA.stop();
        mB.stop();
    }
}