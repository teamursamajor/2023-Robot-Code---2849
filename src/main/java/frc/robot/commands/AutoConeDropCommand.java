package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LimeLightSubsystem;

public class AutoConeDropCommand extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    double armLength;  //horizontal length of arm when arm is fully extended
    double xValue;  //limelght deteced x value
    double centerX;  //x value of where reflective tape needs to be in order to be aligned with arm
    double range;  //decied on a range
    double distance; // used methods in the subsystem to gt the distance
    double ofSetAngle; // ty
    boolean isHigh; 
    boolean xAligned;
    boolean alignFinished;
    private final DriveSubsystem DRIVE_SUBSYSTEM;
    private final LimeLightSubsystem LIME_LIGHT; 

    public AutoConeDropCommand(DriveSubsystem driveSubsystem, LimeLightSubsystem limeLightSubsystem, boolean isHigh){
        this.isHigh = isHigh;  //true is going for high pole, flase if going for mid
        DRIVE_SUBSYSTEM = driveSubsystem;
        LIME_LIGHT = limeLightSubsystem;
        addRequirements(DRIVE_SUBSYSTEM);
        addRequirements(LIME_LIGHT);
    }

    @Override
    public void initialize() {
        //stop robot, turn off controls.
        alignFinished = false;
        xAligned = false;
    }

    @Override
    public void execute() {
        if(isHigh){
            distance = LIME_LIGHT.getDistanceHigh();
        }else{
            distance = LIME_LIGHT.getDistanceMid();
        }
        double x = LIME_LIGHT.getYaw();

        if(!xAligned){
            if (x == Double.MIN_VALUE) {
                System.out.println("Couldn't detect limelight");
                return;
            } else if (x <= (centerX+range) && x >= (centerX-range)) {
                // stop robot set speed 0
                xAligned = true;
            } else if (x > centerX+range) {
                //move left
            } else if (x < centerX-range) {
                //move right
            }
        }else{
            if(distance <= armLength + range && distance >= armLength - range){
                alignFinished  =true;
            }else if(distance>armLength+range){
                //go foward
            }else if(distance<armLength-range){
                //go backward
            }
        }

    
    }

    @Override
    public void end(boolean interrupted) {
       //stop robot
       //drop cone
    }


    @Override
    public boolean isFinished() {

        return alignFinished;
    }



    

}
