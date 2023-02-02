package frc.robot.commands;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class LimitSwitchCommand extends CommandBase { 
    DigitalInput limitswitch=new DigitalInput(9); 
 
    @Override
    public void execute() {
        // TODO Auto-generated method stub
        System.out.println("limit switch: " + limitswitch.get());
    }
}
