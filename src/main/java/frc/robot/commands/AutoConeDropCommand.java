package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LimeLightSubsystem;
import static frc.robot.Constants.*;

public class AutoConeDropCommand extends CommandBase {
    double armLength; // horizontal length of arm when arm is fully extended
    double xValue; // limelght deteced x value
    double centerX = 7; // x value of where reflective tape needs to be in order to be aligned with arm
    double range = 0.75; // decied on a range
    double distance; // used methods in the subsystem to get the distance
    boolean isHigh;
    boolean xAligned;
    boolean alignFinished;
    boolean yawAligned;
    boolean distanceAligned;
    double correctDistance; //-9,to change
    private final DriveSubsystem DRIVE_SUBSYSTEM;
    private final LimeLightSubsystem LIME_LIGHT;
    private final ArmSubsystem ARM_SUBSYSTEM;

    //11 3/4 ft
    //16 5/4 ft over ramp

    public AutoConeDropCommand(DriveSubsystem driveSubsystem, LimeLightSubsystem limeLightSubsystem,
            ArmSubsystem armSubsystem, boolean isHigh) {
        this.isHigh = isHigh; // true is going for high pole, false if going for mid
        DRIVE_SUBSYSTEM = driveSubsystem;
        LIME_LIGHT = limeLightSubsystem;
        ARM_SUBSYSTEM = armSubsystem;
        
        addRequirements(DRIVE_SUBSYSTEM);
        addRequirements(LIME_LIGHT);
        addRequirements(ARM_SUBSYSTEM);
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
            correctDistance = 1.97; //placeholder
        } else {
            LIME_LIGHT.assignMid();
            correctDistance = 0.64; //placeholder//negative???
        }

        xAligned = false;
        yawAligned = false;
        distanceAligned = false;
    }

    @Override
    public void execute() {
        SmartDashboard.putNumber("num of targets", LIME_LIGHT.getSize());
        SmartDashboard.putNumber("x", LIME_LIGHT.getYaw());
        SmartDashboard.putNumber("y", LIME_LIGHT.getPitch());
        //SmartDashboard.putNumber("Distance", ()->{return distance;});
        SmartDashboard.putNumber("pipline index", LIME_LIGHT.pipline());
        SmartDashboard.putBoolean("alignFinished 2", alignFinished);
        SmartDashboard.putBoolean("yaw align", yawAligned);
        SmartDashboard.putBoolean("x aligned", xAligned);
        SmartDashboard.putBoolean("y aligned", distanceAligned);
        if(!LIME_LIGHT.checkTargets()){
            alignFinished = true;
        }else if (isHigh) {
            LIME_LIGHT.assignHigh();
        }else {
            LIME_LIGHT.assignMid();
        }

        if(DRIVE_SUBSYSTEM.getAngleYaw() >= DRIVE_SUBSYSTEM.getYawAlign()-range && DRIVE_SUBSYSTEM.getAngleYaw() <= DRIVE_SUBSYSTEM.getYawAlign()+range){
            yawAligned = true;
            DRIVE_SUBSYSTEM.drive(0.0, 0.0, 0.0);
        }else if(DRIVE_SUBSYSTEM.getAngleYaw()>DRIVE_SUBSYSTEM.getYawAlign()){
            yawAligned = false;
            DRIVE_SUBSYSTEM.drive(0.0, 0.0, -.1);
        }else{
            yawAligned = false;
            DRIVE_SUBSYSTEM.drive(0.0, 0.0, .1);
        }


        if(!alignFinished && yawAligned){
            if (isHigh) {
                distance = LIME_LIGHT.getDistanceHigh();
            } else {
                distance = LIME_LIGHT.getDistanceMid();
            }
    
            double x = LIME_LIGHT.getYaw();
            if (!xAligned) {
                if (x <= (centerX + range) && x >= (centerX - range)) {
                    // stop robot set speed 0
                    //is aligned
                    DRIVE_SUBSYSTEM.driveSim(0, 0, 0);
                    xAligned = true;
                } else if (x > centerX + range) {
                    DRIVE_SUBSYSTEM.driveSim(0, .1, 0);
                } else if (x < centerX - range) {
                    DRIVE_SUBSYSTEM.driveSim(0, -.1, 0);
                }
            }
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
                
            double y = LIME_LIGHT.getPitch();
            /*if(xAligned && isHigh) {
                alignFinished = true;
            } 
            */ 
            if( xAligned && distance >= correctDistance - .05 && distance <= correctDistance + .05) {
                distanceAligned = true;
                alignFinished = true;
            } else if(xAligned &&  distance >= correctDistance + .05) {
                DRIVE_SUBSYSTEM.drive(-0.1, 0, 0);
            } else {
                DRIVE_SUBSYSTEM.drive(0.1, 0, 0);
            }
        }
    }

    @Override
    public void end(boolean interrupted) {
        // stop robot
        // drop cone

        Timer.delay(3);
        DRIVE_SUBSYSTEM.driveSim(0,0,0);
        if(distanceAligned){
            if(isHigh){
                ARM_SUBSYSTEM.setClawSol(true);
            }else{
                ARM_SUBSYSTEM.setArmSol(true);
                Timer.delay(1);
                ARM_SUBSYSTEM.setClawSol(true);
                ARM_SUBSYSTEM.setArmSol(false);
            }
        } 

    }

    @Override
    public boolean isFinished() {
        return alignFinished;
    }

}
