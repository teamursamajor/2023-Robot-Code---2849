package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase {
    // control for closing claw
    private Servo clawServo =  new Servo(0);
    private TalonFX winchMotor = new TalonFX(0); // REPLACE
    
    // this will somehow tell us if we are overextending the arm
    private DigitalInput topLimitSwitch = new DigitalInput(0); // CHANGE PORT IT IS A FILLER
    private DigitalInput bottomLimitSwitch = new DigitalInput(1); //CHANGE PORT IT IS A FILLER
    // this will control the arm extention


    public ArmSubsystem() {
        clawServo.setBounds(2, 1.8, 1.5, 1.2, 1);
        winchMotor.setNeutralMode(NeutralMode.Brake);
    }

    public void setMotor(double speed){
        winchMotor.set(TalonFXControlMode.PercentOutput, speed);
    }

    public void setServo(double position) {
        System.out.println("change");
        clawServo.set(position);
    }

    public boolean getTopSwitch() {
        return topLimitSwitch.get();
    }

    public boolean getBottomSwitch() {
        return bottomLimitSwitch.get();
    }

}
