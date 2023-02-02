package frc.robot.commands;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class UltraSonicCommand extends CommandBase {
    
    private AnalogInput input = new AnalogInput(2);
    private AnalogPotentiometer ultrasonic = new AnalogPotentiometer(input,180,30);


    @Override
    public void initialize() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        System.out.println("UltraSonic: " + ultrasonic.get());
    }

    @Override
    public boolean isFinished() {
        return true;
        
    }

    @Override
    public void end(boolean interrupted) {
        // TODO Auto-generated method stub
        
    }
}