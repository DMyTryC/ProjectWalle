package ev3dev.java.walle;

import ev3dev.hardware.Battery;
import ev3dev.hardware.port.MotorPort;
import ev3dev.hardware.port.SensorPort;
import ev3dev.hardware.motor.EV3LargeRegulatedMotor;
import ev3dev.hardware.sensor.ev3.*;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class WallE {
    
    //Robot Definition
	private final static EV3LargeRegulatedMotor grab = new EV3LargeRegulatedMotor(MotorPort.D);
	private final static EV3LargeRegulatedMotor mA = new EV3LargeRegulatedMotor(MotorPort.C);
    private final static EV3LargeRegulatedMotor mB = new EV3LargeRegulatedMotor(MotorPort.B);
    private final static EV3IRSensor ir1 = new EV3IRSensor(SensorPort.S2);
    private final static EV3TouchSensor ts = new EV3TouchSensor(SensorPort.S1);
    private final static EV3ColorSensor cs = new EV3ColorSensor(SensorPort.S4);

    //Configuration
    private final static int motorSpeed = 700;
    private final static int rampSpeed = 500;
    
    public static void main(String[] args) {
        
        final SampleProvider sp = ir1.getDistanceMode();
        int distance = 255;

        final int distance_threshold = 350;
        
        
        //Robot control loop
        final int iteration_threshold = 100;
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
        Delay.msDelay(1500);
        mA.stop();
        mB.stop();
        mA.backward();
        mB.forward();
        Delay.msDelay(1500);
        mA.stop();
        mB.stop();
    }
}