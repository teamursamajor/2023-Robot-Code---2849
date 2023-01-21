package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class AutoBalence extends CommandBase{

    private final DriveSubsystem driveSubsystem;
    
    float actualAngle;
    float rollAngle;
    float previousAngle;

    public AutoBalence(DriveSubsystem driveSubsystem){
        this.driveSubsystem = driveSubsystem;
        addRequirements(driveSubsystem);
    }

    @Override
    public void initialize(){
        driveSubsystem.calibrate();
    }

    @Override
    public void execute() {
        
        rollAngle = driveSubsystem.getAngleRoll();
        if(rollAngle<0){
            actualAngle = rollAngle * -1;
        }else{
            actualAngle = rollAngle * -1;
        }

        System.out.println("Roll: "+ rollAngle);
        System.out.println("Actual angle: "+actualAngle);
    }
 
    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return false;
    }

}

