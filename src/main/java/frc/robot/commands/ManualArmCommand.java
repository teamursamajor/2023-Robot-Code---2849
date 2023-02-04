package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSubsystem;

public class ManualArmCommand extends CommandBase {

    boolean isUp;
    double speed;
    private final ArmSubsystem ARM_SUBSYSTEM;
    public ManualArmCommand(ArmSubsystem ARM_SUBSYSTEM, boolean isUp){
        this.ARM_SUBSYSTEM = ARM_SUBSYSTEM;
        this.isUp = isUp;
        addRequirements(ARM_SUBSYSTEM);
    }
    
    @Override
    public void initialize() {
        ARM_SUBSYSTEM.setMotorVictor(0.0);
        if(isUp){
            speed = .25;
        }else{
            speed = -.10;
        }
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        //ARM_SUBSYSTEM.setMotor(speed);
        ARM_SUBSYSTEM.setMotorVictor(speed);
        //System.out.println("Bottom Limit:"+ ARM_SUBSYSTEM.getBottomSwitch() + "Top Limit: "+ ARM_SUBSYSTEM.getTopSwitch());

    }

    @Override
    public void end(boolean interrupted) {
        // TODO Auto-generated method stub
        ARM_SUBSYSTEM.setMotor(0.0);

    }


}
