package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSubsystem;

public class ManualActuatorClawCommand extends CommandBase {
    private final ArmSubsystem ARM_SUBSYSTEM;
    boolean isOpening;
    double position;

    public ManualActuatorClawCommand(ArmSubsystem ARM_SUBSYSTEM, boolean isOpening) {
        this.ARM_SUBSYSTEM = ARM_SUBSYSTEM;
        this.isOpening = isOpening;
        addRequirements(ARM_SUBSYSTEM);
    }

    @Override
    public void initialize() {
        System.out.println("inital");
        // TODO Auto-generated method stub
        if(isOpening) {
            position = 0.0;
        } else {
            position = 1.0;
        }
    }

    @Override
    public void execute() {
        ARM_SUBSYSTEM.setServo(position);
    }

    @Override
    public void end(boolean interrupted) {

    }
}
