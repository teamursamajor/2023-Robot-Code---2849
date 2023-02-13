package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class AutoLeaveCommand extends CommandBase {
    private DriveSubsystem driveSubsystem;
    private boolean finished;

    public AutoLeaveCommand(DriveSubsystem driveSubsystem) {
        this.driveSubsystem = driveSubsystem;
    }

    @Override
    public void initialize() {
        finished = false;
    }

    @Override
    public void execute() {
        driveSubsystem.drive(0.5,0.0,0.0);
        Timer.delay(1);
        finished = true;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public void end(boolean interrupted) {
        driveSubsystem.drive(0.0, 0.0, 0.0);
    }
}
