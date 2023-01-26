package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase {
    // control for closing claw
    private Servo clawServo;
    
    // this will somehow tell us if we are overextending the arm
    LimitSwitchSource winchLimit;
    // this will control the arm extention
    private TalonFX winchMotor;

    public ArmSubsystem() {
        clawServo = new Servo(0); // REPLACE
        winchLimit = LimitSwitchSource.RemoteTalon;
        winchMotor = new TalonFX(0); // REPLACE
    }
}
