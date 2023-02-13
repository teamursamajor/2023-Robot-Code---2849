package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSubsystem;

public class ManualArmCommand extends CommandBase {

    boolean isUp;
    double speed;

    private final ArmSubsystem ARM_SUBSYSTEM;

    public ManualArmCommand(ArmSubsystem ARM_SUBSYSTEM, boolean isUp) {
        this.ARM_SUBSYSTEM = ARM_SUBSYSTEM;
        this.isUp = isUp;
        addRequirements(ARM_SUBSYSTEM);

    }

    @Override
    public void initialize() {
        ARM_SUBSYSTEM.setMotorTalon(0.0);
        if (isUp) {
            speed = .25;
        } else {
            speed = -.1;
        }

    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        ARM_SUBSYSTEM.setMotorTalon(speed);
        // System.out.println("Bottom Limit:"+ ARM_SUBSYSTEM.getBottomSwitch() + "Top
        // Limit: "+ ARM_SUBSYSTEM.getTopSwitch());
        ARM_SUBSYSTEM.getTopSwitch();
        ARM_SUBSYSTEM.getBottomSwitch();

    }

    @Override
    public void end(boolean interrupted) {
        // TODO Auto-generated method stub
        System.out.println("end");
        ARM_SUBSYSTEM.setMotorTalon(0.0);

    }

    /*
     * @Override
     * public boolean isFinished() {
     * return isFinished;
     * }
     */

}
