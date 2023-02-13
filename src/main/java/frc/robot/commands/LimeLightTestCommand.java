package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LimeLightSubsystem;

public class LimeLightTestCommand extends CommandBase{
    private LimeLightSubsystem LIME_LIGHT;
    boolean high;

    public LimeLightTestCommand(LimeLightSubsystem LIME_LIGHT, boolean high){
        this.LIME_LIGHT = LIME_LIGHT;
        this.high = high;
        addRequirements(LIME_LIGHT);
    }

    @Override
    public void initialize() {
        LIME_LIGHT.checkTargets();
        if(high){
            LIME_LIGHT.assignHigh();
        }else{
            LIME_LIGHT.assignMid();
        }
    }

    @Override
    public void execute() {
        System.out.println("x: " + LIME_LIGHT.getYaw());
        System.out.println("y: " + LIME_LIGHT.getPitch());
    }
    
}
