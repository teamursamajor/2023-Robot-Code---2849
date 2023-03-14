package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class AutoLeaveBackwards extends CommandBase {
    private DriveSubsystem driveSubsystem;
    private boolean finished;

    public AutoLeaveBackwards(DriveSubsystem driveSubsystem) {
        this.driveSubsystem = driveSubsystem;
        addRequirements(driveSubsystem);
    }

    @Override
    public void initialize() {
        finished = false;
        System.err.println("AutoLeaveBackwards init");
    }

    @Override
    public void execute() {
        System.err.println("AutoLeaveBackwards exec");
        Timer.delay(.5);
        driveSubsystem.drive(.75, 0, 0);
        Timer.delay(3.8); // Change as needed
        driveSubsystem.drive(0, 0, 0);
        Timer.delay(0.125);
        finished = true;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public void end(boolean interrupted) {
        System.err.println("AutoLeaveBackwards end");
        driveSubsystem.drive(0, 0, 0);
    }
}
