package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSubsystem;

public class AutoPistonClawCommand extends CommandBase{
    boolean isFinished = false;
    boolean position;
    private final ArmSubsystem ARM_SUBSYSTEM;
    
    public AutoPistonClawCommand(ArmSubsystem ARM_SUBSYSTEM,boolean position){
        this.ARM_SUBSYSTEM = ARM_SUBSYSTEM;
        this.position = position;
        addRequirements(ARM_SUBSYSTEM);
    }
    @Override
    public void initialize() {
       
        
    }

    @Override
    public void execute() {
        ARM_SUBSYSTEM.setClawSol(position);
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
