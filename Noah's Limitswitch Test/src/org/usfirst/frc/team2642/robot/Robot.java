package org.usfirst.frc.team2642.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.DigitalInput;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	RobotDrive myRobot;
	Joystick stick;
	int autoLoopCounter;
	Encoder liftEncoder;
	Jaguar lift;
	DigitalInput toteIn;
	boolean liftUp;
	boolean liftDown;
	DigitalInput fiveTote;
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	//myRobot = new RobotDrive(0,1);
    	stick = new Joystick(0);
    	liftEncoder = new Encoder(2, 3);
    	lift = new Jaguar(1);
    	toteIn = new DigitalInput(8);
    	fiveTote = new DigitalInput(9);
    	
    }
    
    /**
     * This function is run once each time the robot enters autonomous mode
     */
    public void autonomousInit() {
    	autoLoopCounter = 0;
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	if(autoLoopCounter < 100) //Check if we've completed 100 loops (approximately 2 seconds)
		{
			myRobot.drive(-0.5, 0.0); 	// drive forwards half speed
			autoLoopCounter++;
			} else {
			myRobot.drive(0.0, 0.0); 	// stop robot
		}
    }
    
    /**
     * This function is called once each time the robot enters tele-operated mode
     */
    public void teleopInit(){
    	liftEncoder.reset();
    	liftUp = false;
    	liftDown = false;
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	if(fiveTote.get() == false){
    		if(!toteIn.get() && !liftUp && !liftDown){
    			liftUp = true; 
    			liftDown = false;
    		}else if(liftUp){
    			if(liftEncoder.getDistance() > 150){
    				liftUp = false;
    				liftDown = true;
    			}
    			lift.set(0.5);
    		}else if(liftDown){
    			if(liftEncoder.getDistance() <= 5){
    				liftUp = false;
    				liftDown = false;
    			}
    			lift.set(-0.5);
    		}else{
    			lift.set(0);
    		}
    	}else if(fiveTote.get() == true){
    		if(!toteIn.get() && !liftUp && !liftDown){
    			liftUp = true; 
    			liftDown = false;
    		}else if(liftUp){
    			if(liftEncoder.getDistance() > 20){
    				liftUp = false;
    				liftDown = true;
    				}
    			lift.set(0.5);
    			}
    		}else{
    			lift.set(0);
    			}
    	//System.out.println(liftDown);
    	System.out.println(liftEncoder.getDistance());
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	LiveWindow.run();
    }
    
}
