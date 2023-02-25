package frc.robot.commands;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.MjpegServer;
import edu.wpi.first.cscore.VideoSource;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LimeLightSubsystem;

public class LimeLightTestCommand extends CommandBase {
    private LimeLightSubsystem LIME_LIGHT;
    boolean high;
    boolean isfinished = false;
    public LimeLightTestCommand(LimeLightSubsystem LIME_LIGHT, boolean high) {
        this.LIME_LIGHT = LIME_LIGHT;
        this.high = high;
        addRequirements(LIME_LIGHT);
    }

    @Override
    public void initialize() {
       // System.out.println("Initalize)");
       //  if(!LIME_LIGHT.checkTargets()){
         //   isfinished = true;
       // } else if (high) {
       //     LIME_LIGHT.assignHigh();
       // } else {
       //     LIME_LIGHT.assignMid();
       // }
    }

    @Override
    public void execute() {
        System.out.println("execute");
        //testapriltags
    //    if(!LIME_LIGHT.checkTargets()){
     //       isfinished = true;
    //    } else if (high) {
     //       LIME_LIGHT.assignHigh();
    //    } else {
     //       LIME_LIGHT.assignMid();
    //    }
//test apriltags
    //    SmartDashboard.putNumber("x", LIME_LIGHT.getYaw() );
     //   SmartDashboard.putNumber("y", LIME_LIGHT.getPitch() );

LIME_LIGHT.aprilTagsTest();
 
    }
    @Override
    public void end(boolean interrupted) {
    }
        

    @Override
    public boolean isFinished() {
        return isfinished;
    }
}                                   
