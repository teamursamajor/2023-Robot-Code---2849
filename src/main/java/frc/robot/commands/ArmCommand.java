package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ArmCommand extends CommandBase {
    @Override
    public void initialize() {
        // TODO Auto-generated method stub
        boolean ifDone = false;
    }

    @Override
    public void execute() {
        boolean minLimit = true; // placeholder
        boolean maxLimit = false; // placeholder
    
        // 
       /*  if (maxLimit) {
           clawServo.set(-0.5); 
           ifDone = true; 
        } else if (minLimit) {
            clawServo.set(0.5);
            ifDone = true;
        }
    } */

    @Override
    public void end(boolean interrupted) {
        
        // brake motor so arm does not fall back down
    }

    @Override
    public boolean isFinished() {
        // TODO Auto-generated method stub
            return ifDone; 
    }
}
