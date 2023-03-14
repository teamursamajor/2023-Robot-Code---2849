package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;
import static frc.robot.Constants.*;

public class AutoBalanceCommand extends CommandBase {

    private final DriveSubsystem driveSubsystem;

    double actualAngle;
    double pitchAngle;
    int counter;
    double speed;
    double angleDiff;
    double angleDiffMax;
    boolean balanced;
    double minValue = -5; // Change as needed
    double maxValue = 5; // change as needed
    double range = 2;
    double baseSpeed = 0.1;
    double offset = 0;
    double lastAngle;
    boolean onRamp;
    int balancedCountLimit = 30;
    int balancedCount;
    int rampCountLimit = 50;
    int rampCounter;
    int testCounter;
    int timer;
    double pidCalc;
    PIDController ctrl;

    public AutoBalanceCommand(DriveSubsystem driveSubsystem) {
        this.driveSubsystem = driveSubsystem;
        addRequirements(driveSubsystem);
        // p 0.2
        // d 0.002
        // divide 12

        // .18
        // 0.0
        // .0018
        ctrl = new PIDController(0.175, 0.0, 0.0005);
        ctrl.setSetpoint(0.0);
        ctrl.setTolerance(3);
        debugTab.addNumber("Ramp speed", () -> {
            return speed;
        });
        debugTab.addBoolean("on ramp", () -> {
            return onRamp;
        });
        debugTab.addNumber("Actual Angle", () -> {
            return actualAngle;
        });
        debugTab.addBoolean("Balanced", () -> {
            return balanced;
        });
        debugTab.addNumber("PID Calculation", () -> {
            return pidCalc;
        });
        debugTab.addNumber("PID Drive", () -> {
            return pidCalc / 12;
        });
        debugTab.addBoolean("PID atSetPoint", () -> {
            return ctrl.atSetpoint();
        });
    }

    @Override
    public void initialize() {
        System.err.println("AutoBalance init");
        timer = 0;
        counter = 0;
        rampCounter = 0;
        lastAngle = 0;
        pitchAngle = 0;
        actualAngle = 0;
        balanced = false;
        onRamp = false;
        balancedCount = 0;
        testCounter = 0;
        driveSubsystem.drive(0.0, 0.0, 0.0);
        // driveSubsystem.calibrate();
        driveSubsystem.drive(-.5, 0.0, 0.0);
    }

    @Override
    public void execute() {
        System.err.println("AutoBalance init");
        testCounter++;
        timer = (timer + 1) % 10;

        pitchAngle = driveSubsystem.getAnglePitch();
        if (timer == 0) {
            lastAngle = actualAngle;
        }
        actualAngle = pitchAngle - offset;

        angleDiff = lastAngle - actualAngle;
        speed = (double) actualAngle / 125;
        if (((actualAngle > maxValue) || (actualAngle < minValue))) {
            rampCounter++;
            if (rampCounter > rampCountLimit) {
                // out of range long enough that we know robot is on ramp
                onRamp = true;
                ctrl.reset();
            }
        } else if (onRamp) {
            if (ctrl.atSetpoint()) {
                balancedCount++;
                speed = 0;
                // driveSubsystem.drive(0.0,0.0,0.0);
                if (balancedCount > balancedCountLimit) {
                    balanced = true;
                }
            } else {
                balancedCount = 0;
            }
        }
        if (onRamp) {
            pidCalc = ctrl.calculate(driveSubsystem.getAnglePitch());
            /*
             * if(Math.abs(speed) < baseSpeed && actualAngle < 0) {
             * speed = -baseSpeed;
             * } else if (Math.abs(speed) < baseSpeed && actualAngle > 0) {
             * speed = baseSpeed;
             * }
             */
            driveSubsystem.drive((pidCalc / 16), 0.0, 0.0);
        }
        /*
         * DO NOT DELETE
         * if (Math.abs(actualAngle) < 4 && actualAngle < 0) {
         * speed = -0.2;
         * } else if (Math.abs(actualAngle) < 4 && actualAngle > 0) {
         * speed = 0.2;
         * }
         * // Out of range so robot is at an angle on ramp
         * if ((actualAngle > maxValue) || (actualAngle < minValue)) {
         * rampCounter++;
         * if (rampCounter > rampCountLimit) {
         * //out of range long enough that we know robot is on ramp
         * onRamp = true;
         * }
         * // robot is tipping back, increase speed
         * if ((actualAngle > (previousAngle + range)) && onRamp) {
         * speed *= 1.3;
         * // robot is in the same spot
         * } else if ((actualAngle < previousAngle + range) && (actualAngle >
         * previousAngle - range) && onRamp) {
         * counter += 1;
         * // robot is stuck at point on ramp, increase speed
         * if (counter > 9) {
         * speed *= 1.15;
         * }
         * // reset counter
         * } else {
         * counter = 0;
         * }
         * // set speed
         * SmartDashboard.putNumber("Ramp speed", (speed ));
         * driveSubsystem.drive(speed, 0.0, 0.0);
         * } else if ((onRamp)) {
         * driveSubsystem.drive(0.0, 0.0, 0.0);
         * balancedCount++;
         * if (balancedCount > balancedCountLimit) {
         * balanced = true;
         * }
         * }
         * previousAngle = actualAngle;
         */
    }

    @Override
    public void end(boolean interrupted) {
        System.err.println("AutoBalance init");
        driveSubsystem.drive(0.0, 0.0, 0.0);
    }

    @Override
    public boolean isFinished() {
        // return balanced;
        return false;
    }

}
