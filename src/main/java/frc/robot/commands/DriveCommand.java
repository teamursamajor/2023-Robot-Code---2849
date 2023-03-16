package frc.robot.commands;

import static frc.robot.Constants.*;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class DriveCommand extends CommandBase {
    DriveSubsystem drive;
    private double slowScale = 0.5;

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
        if (XBOX_CONTROLLER.getRightTriggerAxis() >= 0.75) {
            drive.drive(XBOX_CONTROLLER.getLeftY() * slowScale, XBOX_CONTROLLER.getLeftX() * slowScale,
                    XBOX_CONTROLLER.getRightX() * slowScale);
        }else if(XBOX_CONTROLLER.getLeftTriggerAxis() >= 0.75){
            drive.drive(-.1,0,0);
        }else{
            
            drive.drive(XBOX_CONTROLLER.getLeftY(), XBOX_CONTROLLER.getLeftX(), XBOX_CONTROLLER.getRightX());
        }
        
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


