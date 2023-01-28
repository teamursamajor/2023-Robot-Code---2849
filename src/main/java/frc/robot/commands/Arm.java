package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSubsystem;

public class Arm extends CommandBase {

    boolean isUp;
    private final ArmSubsystem ARM_SUBSYSTEM;
    public Arm(ArmSubsystem ARM_SUBSYSTEM, boolean isUp){
        this.ARM_SUBSYSTEM = ARM_SUBSYSTEM;
        this.isUp = isUp;
        addRequirements(ARM_SUBSYSTEM);
    }
    
    @Override
    public void initialize() {
        super.initialize();
        ARM
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        super.execute();
    }

    @Override
    public void end(boolean interrupted) {
        // TODO Auto-generated method stub
        super.end(interrupted);
    }


}
