package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class AutoBalanceCommand extends CommandBase {

    private final DriveSubsystem driveSubsystem;

    float actualAngle;
    float pitchAngle;
    float previousAngle;
    int counter;
    boolean balanced;
    double minValue = -2; // Change as needed
    double maxValue = 2; // change as needed
    double range = 2;
    boolean onRamp; 
    int balancedCountLimit = 20;
    int balancedCount;
    int rampCountLimit = 5;
    int rampCounter;

    public AutoBalanceCommand(DriveSubsystem driveSubsystem) {
        this.driveSubsystem = driveSubsystem;
        addRequirements(driveSubsystem);
    }


    @Override
    public void initialize() {
        previousAngle = 0f;
        counter = 0;
        rampCounter = 0;
        balanced = false;
        onRamp = false;
        balancedCount = 0;
        driveSubsystem.drive(0.0, 0.0, 0.0);
        // driveSubsystem.calibrate();
        driveSubsystem.drive(.3, 0.0, 0.0);
    }

    @Override
    public void execute() {
        pitchAngle = driveSubsystem.getAnglePitch();
        actualAngle = pitchAngle + 4; 

        double speed = (double) actualAngle / 30;
        /*
         * if((actualAngle<minValue)&&(speed>-.15)){
         * speed = -.2;
         * }
         */
        
        //set minimum speed
        if (Math.abs(actualAngle) < 4 && actualAngle < 0) {
            speed = -0.2;
        } else if (Math.abs(actualAngle) < 4 && actualAngle > 0) {
            speed = 0.2;
        }


        // Out of range so robot is at an angle on ramp
        if ((actualAngle > maxValue) || (actualAngle < minValue)) {
            rampCounter++;
            if (rampCounter > rampCountLimit) {
                //out of range long enough that we know robot is on ramp
                onRamp = true;
            }
            // robot is tipping back, increase speed
            if ((actualAngle > (previousAngle + range)) && onRamp) {
                speed *= 1.3;
                // robot is in the same spot
            } else if ((actualAngle < previousAngle + range) && (actualAngle > previousAngle - range) && onRamp) {
                counter += 1;
                // robot is stuck at point on ramp, increase speed
                if (counter > 9) {
                    speed *= 1.15;
                }
                // reset counter
            } else {
                counter = 0;
            }
            // set speed
            SmartDashboard.putNumber("Ramp speed", (speed ));  
            driveSubsystem.drive(speed, 0.0, 0.0);
        } else if ((onRamp)) {
            driveSubsystem.drive(0.0, 0.0, 0.0);
            balancedCount++;
            if (balancedCount > balancedCountLimit) {
                balanced = true;
            }
        }
        previousAngle = actualAngle;
    }

    @Override
    public void end(boolean interrupted) {
        driveSubsystem.drive(0.0, 0.0, 0.0);
        System.out.println("end");
    }

   /*  @Override
    public boolean isFinished() {
        return balanced;
    }
    */

        @Override
        public boolean isFinished() {
            // TODO Auto-generated method stub
            return super.isFinished();
        }
}
