package frc.robot.commands;

import static frc.robot.Constants.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class DriveCommand extends CommandBase {
    DriveSubsystem drive;
    private double slowScale = 0.25;
    private double dpad = 0.75;

    public DriveCommand(DriveSubsystem drive) {
        this.drive = drive;
        addRequirements(drive);
    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub

    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        double fowardBack = XBOX_CONTROLLER.getLeftY();
        double leftRight = XBOX_CONTROLLER.getLeftX();
        double rotation = XBOX_CONTROLLER.getRightX();
        if(XBOX_CONTROLLER.povDown().getAsBoolean()){
            fowardBack = dpad;
            leftRight = 0;
            rotation = 0;
        }else if(XBOX_CONTROLLER.povUp().getAsBoolean()){
            fowardBack = -dpad;
            leftRight = 0;
            rotation = 0;
        }else if(XBOX_CONTROLLER.povLeft().getAsBoolean()){
            fowardBack = 0;
            leftRight = -dpad;
            rotation = 0;
        }else if(XBOX_CONTROLLER.povRight().getAsBoolean()){
            fowardBack = 0;
            leftRight = dpad;
            rotation = 0;
        }
        if (XBOX_CONTROLLER.getRightTriggerAxis() >= 0.75) {
            fowardBack *= slowScale;
            leftRight *= slowScale;
            rotation *= slowScale;
        }
            
        drive.drive(fowardBack, leftRight, rotation);
        
    }
    
    @Override
    public void end(boolean interrupted) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isFinished() {
        // TODO Auto-generated method stub
        return false;
    }
}


