package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSubsystem;

public class AutoArmCommand extends CommandBase {
    boolean isFinished;
    boolean minLimitUpdated;
    boolean maxLimitUpdated;
    boolean minLimit;
    boolean maxLimit;
    boolean goingUp = false;
    boolean inAPosition = true;
    private final ArmSubsystem ARM_SUBSYSTEM;
    public AutoArmCommand(ArmSubsystem ARM_SUBSYSTEM){
        this.ARM_SUBSYSTEM = ARM_SUBSYSTEM;
        addRequirements(ARM_SUBSYSTEM);
    }
    
    
    @Override
    public void initialize() {
        // TODO Auto-generated method stub
        isFinished = false;
        minLimit = ARM_SUBSYSTEM.getBottomSwitch();
        maxLimit = ARM_SUBSYSTEM.getTopSwitch();
        if(!minLimit && !maxLimit){
            inAPosition = false;
        }else if(minLimit){
            goingUp = true;
        }else{
            goingUp = false;
        }
    }

    @Override
    public void execute() {
        minLimitUpdated = ARM_SUBSYSTEM.getBottomSwitch();
        maxLimitUpdated = ARM_SUBSYSTEM.getTopSwitch();
        if(!inAPosition && !minLimitUpdated){
            ARM_SUBSYSTEM.setMotorVictor(-.15);
        }else if(!inAPosition && minLimitUpdated){
            isFinished = true;
        }else if(goingUp){
            if(maxLimitUpdated){
                isFinished = true;
            }else{
                ARM_SUBSYSTEM.setMotorVictor(.25);
            }
        }else if(!goingUp){
            if(minLimitUpdated){
                isFinished = true;
            }else{
                ARM_SUBSYSTEM.setMotorVictor(-.15);
            }
        }

    } 

    @Override
    public void end(boolean interrupted) {
        ARM_SUBSYSTEM.setMotorVictor(0.0);
        // brake motor so arm does not fall back down
    }

    @Override
    public boolean isFinished() {
        return isFinished; 
    }
}
