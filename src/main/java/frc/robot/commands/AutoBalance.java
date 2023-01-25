package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class AutoBalance extends CommandBase {

    private final DriveSubsystem driveSubsystem;
    
    float actualAngle;
    float rollAngle;
    float previousAngle;
    int counter;
    boolean balanced;
    double minValue = 0; //Change as needed
    double maxValue = 5;  //change as needed
    double range = 2;
    boolean onRamp;
    int countLimit = 8;

    public AutoBalance(DriveSubsystem driveSubsystem){
        this.driveSubsystem = driveSubsystem;
        addRequirements(driveSubsystem);
    }

    @Override
    public void initialize(){
        previousAngle = 0f;
        counter = 0;
        balanced = false;
        onRamp = false;
        driveSubsystem.drive(0.0,0.0,0.0);
        driveSubsystem.calibrate();
        driveSubsystem.drive(.25,0.0,0.0);
    }

    @Override
    public void execute() {
        
        rollAngle = driveSubsystem.getAngleRoll();
        actualAngle = rollAngle * -1; //angle is inverted
        double speed = (double)actualAngle/180;

        //Out of range so robot is at an angle on ramp
        if((actualAngle > maxValue)|| (actualAngle<minValue))  {
            onRamp = true;
            //robot is tipping back, increase speed
            if(actualAngle > (previousAngle+range)) {
                speed *= 1.3;
            //robot is in the same spot
            } else if((actualAngle < previousAngle + range) && (actualAngle > previousAngle - range)) {
                counter += 1;
                //robot is stuck at point on ramp, increase speed
                if(counter > 9) {
                    speed *= 1.15;
                }
            //reset counter
            }else{
                counter = 0;
            }
            //set speed
            driveSubsystem.drive(speed, 0.0, 0.0);
        }  else if ((onRamp)){
            balanced = true;
        }
        previousAngle = actualAngle;
    }
 
    @Override
    public void end(boolean interrupted) {
        driveSubsystem.drive(0.0, 0.0, 0.0);
    }

    @Override
    public boolean isFinished() {
        return balanced;
    }

}

