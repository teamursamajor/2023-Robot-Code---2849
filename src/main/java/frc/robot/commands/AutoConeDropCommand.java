package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LimeLightSubsystem;

public class AutoConeDropCommand extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    double armLength; // horizontal length of arm when arm is fully extended
    double xValue; // limelght deteced x value
    double centerX = 0; // x value of where reflective tape needs to be in order to be aligned with arm
    double range = 2; // decied on a range
    double distance; // used methods in the subsystem to get the distance
    boolean isHigh;
    boolean xAligned;
    boolean alignFinished;
    private final DriveSubsystem DRIVE_SUBSYSTEM;
    private final LimeLightSubsystem LIME_LIGHT;
    private final ArmSubsystem ARM_SUBSYSTEM;

    public AutoConeDropCommand(DriveSubsystem driveSubsystem, LimeLightSubsystem limeLightSubsystem,
            ArmSubsystem armSubsystem, boolean isHigh) {
        this.isHigh = isHigh; // true is going for high pole, false if going for mid
        DRIVE_SUBSYSTEM = driveSubsystem;
        LIME_LIGHT = limeLightSubsystem;
        ARM_SUBSYSTEM = armSubsystem;
        addRequirements(DRIVE_SUBSYSTEM);
        addRequirements(LIME_LIGHT);
    }

    @Override
    public void initialize() {
        // stop robot
        LIME_LIGHT.reflectiveTapePipline();
        DRIVE_SUBSYSTEM.driveSim(0, 0, 0);
        alignFinished = false;
        if (!LIME_LIGHT.checkTargets()) {
            alignFinished = true;
        }else if (isHigh) {
            LIME_LIGHT.assignHigh();
        } else {
            LIME_LIGHT.assignMid();
        }
        xAligned = false;
    }

    @Override
    public void execute() {
        if(!LIME_LIGHT.checkTargets()){
            alignFinished = true;
        }else if (isHigh) {
            LIME_LIGHT.assignHigh();
        }else {
            LIME_LIGHT.assignMid();
        }

        if(!alignFinished){
            if (isHigh) {
                distance = LIME_LIGHT.getDistanceHigh();
            } else {
                distance = LIME_LIGHT.getDistanceMid();
            }
            SmartDashboard.putNumber("num of targets", LIME_LIGHT.getSize());
            SmartDashboard.putNumber("x", LIME_LIGHT.getYaw() );
            SmartDashboard.putNumber("y", LIME_LIGHT.getPitch() );
            SmartDashboard.putNumber("Distance", distance );
    
            double x = LIME_LIGHT.getYaw();
            if (!xAligned) {
                if (x <= (centerX + range) && x >= (centerX - range)) {
                    // stop robot set speed 0
                    //is aligned
                    DRIVE_SUBSYSTEM.driveSim(0, 0, 0);
                    xAligned = true;
                } else if (x > centerX + range) {
                    DRIVE_SUBSYSTEM.driveSim(0, -.25, 0);
                } else if (x < centerX - range) {
                    DRIVE_SUBSYSTEM.driveSim(0, .25, 0);
                }
            } else {
                /* 
                if (distance <= armLength + range && distance >= armLength - range) {
                    alignFinished = true;
                } else if (distance > armLength + range) {
                    // go foward
                    DRIVE_SUBSYSTEM.drive(.15, 0, 0);
                } else if (distance < armLength - range) {
                    // go backward
                    DRIVE_SUBSYSTEM.drive(-.15, 0, 0);
                }
                */
                alignFinished = true;
            }
        }
        

    }

    @Override
    public void end(boolean interrupted) {
        // stop robot
        // drop cone
        DRIVE_SUBSYSTEM.driveSim(0,0,0);

    }

    @Override
    public boolean isFinished() {
        return alignFinished;
    }

}
