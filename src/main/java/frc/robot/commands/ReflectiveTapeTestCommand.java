package frc.robot.commands;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.MjpegServer;
import edu.wpi.first.cscore.VideoSource;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LimeLightSubsystem;
import static frc.robot.Constants.*;

public class ReflectiveTapeTestCommand extends CommandBase {
    private LimeLightSubsystem LIME_LIGHT;
    boolean high;
    boolean isfinished = false;
    double distance;

    public ReflectiveTapeTestCommand(LimeLightSubsystem LIME_LIGHT, boolean high) {
        this.LIME_LIGHT = LIME_LIGHT;
        this.high = high;
        addRequirements(LIME_LIGHT);
    }

    @Override
    public void initialize() {
        isfinished = false;
        LIME_LIGHT.reflectiveTapePipline();
        if(!LIME_LIGHT.checkTargets()){
            isfinished = true;
        } else if (high) {
            LIME_LIGHT.assignHigh();
        } else {
            LIME_LIGHT.assignMid();
        }
    }

    @Override
    public void execute() {
        if(!LIME_LIGHT.checkTargets()){
            isfinished = true;
        }
        else if (high) {
            LIME_LIGHT.assignHigh();
        } else {
            LIME_LIGHT.assignMid();
        }

        if(!isfinished){
            if (high) {
                distance = LIME_LIGHT.getDistanceHigh();
            } else {
                distance = LIME_LIGHT.getDistanceMid();
            }
        }

        /* 
        LIME_LIGHT.aprilTagsTest();
        SmartDashboard.putNumber("num of targets", LIME_LIGHT.getSize());
        SmartDashboard.putBoolean("Target", isfinished);
        */
 
    }
    @Override
    public void end(boolean interrupted) {
        
    }
    @Override
    public boolean isFinished() {
        return isfinished;
    }
}                                   
