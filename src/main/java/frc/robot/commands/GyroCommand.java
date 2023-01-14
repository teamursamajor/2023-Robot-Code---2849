package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.GyroSubsystem;

public class GyroCommand extends CommandBase {
    private GyroSubsystem gyroSubsystem;


    public GyroCommand(GyroSubsystem gyroSubsystem){
        this.gyroSubsystem = gyroSubsystem;
        addRequirements(gyroSubsystem);
    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub
        super.initialize();
        gyroSubsystem.calibrate();
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        super.execute();
        System.out.println(gyroSubsystem.getXAxis());
    }

    @Override
    public void end(boolean interrupted) {
        // TODO Auto-generated method stub
        super.end(interrupted);
    }


}
