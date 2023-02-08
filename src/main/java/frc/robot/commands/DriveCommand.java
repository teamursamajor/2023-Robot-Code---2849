package frc.robot.commands;
import static frc.robot.Constants.*;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class DriveCommand extends CommandBase {
    boolean isFinished = false;
    DriveSubsystem drive; 

    public DriveCommand (DriveSubsystem drive) {
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
        drive.drive(XBOX_CONTROLLER.getLeftY(), -XBOX_CONTROLLER.getLeftX(), XBOX_CONTROLLER.getRightX());
        
    }
    @Override
    public void end(boolean interrupted) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public boolean isFinished() {
        // TODO Auto-generated method stub
        return isFinished; 
    }
}
