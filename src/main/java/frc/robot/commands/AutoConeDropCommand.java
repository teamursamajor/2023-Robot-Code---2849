package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LimeLightSubsystem;

public class AutoConeDropCommand  extends CommandBase{
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
    private final ArmSubsystem ARM_SUBSYSTEM;

    public AutoConeDropCommand(DriveSubsystem driveSubsystem, LimeLightSubsystem limeLightSubsystem,ArmSubsystem armSubsystem, boolean isHigh){
        this.isHigh = isHigh;  //true is going for high pole, false if going for mid
        DRIVE_SUBSYSTEM = driveSubsystem;
        LIME_LIGHT = limeLightSubsystem;
        ARM_SUBSYSTEM = armSubsystem;
        addRequirements(DRIVE_SUBSYSTEM);
        addRequirements(LIME_LIGHT);
    }

    @Override
    public void initialize() {
        //stop robot, turn off controls.
        alignFinished = false;
        if (!LIME_LIGHT.checkTargets()){
            alignFinished = true;
        }
        if(isHigh){
            LIME_LIGHT.assignHigh();
        }else{
            LIME_LIGHT.assignMid();
        }
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
                DRIVE_SUBSYSTEM.drive(0, 0, 0);
                xAligned = true;
            } else if (x > centerX+range) {
                DRIVE_SUBSYSTEM.drive(0, .25, 0);
            } else if (x < centerX-range) {
                DRIVE_SUBSYSTEM.drive(0, .25, 0);
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
