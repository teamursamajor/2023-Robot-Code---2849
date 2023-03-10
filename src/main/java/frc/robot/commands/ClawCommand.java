package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSubsystem;

public class ClawCommand extends CommandBase {
    boolean isFinished = false;
    private final ArmSubsystem ARM_SUBSYSTEM;

    public ClawCommand(ArmSubsystem ARM_SUBSYSTEM) {
        this.ARM_SUBSYSTEM = ARM_SUBSYSTEM;
        addRequirements(ARM_SUBSYSTEM);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        ARM_SUBSYSTEM.toggleClawSol();
        isFinished = true;
    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {

        return isFinished;
    }
}
