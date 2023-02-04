package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;

import edu.wpi.first.hal.SimDevice;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase {
    // control for closing claw
    private DoubleSolenoid clawSol = new DoubleSolenoid(1,PneumaticsModuleType.REVPH, 0,15);
    private Servo clawServo =  new Servo(0);
    private TalonFX winchMotor = new TalonFX(0); // REPLACE
    private VictorSPX winchMotorVictor = new VictorSPX(0);
    
    // this will somehow tell us if we are overextending the arm
    private DigitalInput topLimitSwitch = new DigitalInput(1); // CHANGE PORT IT IS A FILLER
    private DigitalInput bottomLimitSwitch = new DigitalInput(0); //CHANGE PORT IT IS A FILLER
    // this will control the arm extention

    Compressor comp = new Compressor(1, PneumaticsModuleType.REVPH);

    public ArmSubsystem() {
        clawServo.setBounds(2, 1.8, 1.5, 1.2, 1);
        winchMotor.setNeutralMode(NeutralMode.Brake);
        winchMotorVictor.setNeutralMode(NeutralMode.Brake);
        System.out.println(clawSol.isFwdSolenoidDisabled());
        comp.enableDigital();
        setClawSol(true);
    }

    public void setMotor(double speed){
        winchMotor.set(TalonFXControlMode.PercentOutput, speed);
    }

    public void setMotorVictor(double speed) {
        winchMotorVictor.set(VictorSPXControlMode.PercentOutput, speed);
    }

    public void setServo(double position) {
        System.out.println("change");
        clawServo.set(position);
    }

    public boolean getTopSwitch() {
        System.out.println("Top Limit Switch: " + topLimitSwitch.get());
        return topLimitSwitch.get();
    }

    public boolean getBottomSwitch() {
        System.out.println("Bottom Limit Switch: " + bottomLimitSwitch.get());
        return bottomLimitSwitch.get();
    }

    public void setClawSol(boolean closeOrNot){
        clawSol.set(closeOrNot ? kForward : kReverse);
        System.out.println(clawSol.get());
    }

    public void turnOffClawSol(){
        clawSol.set(kOff);
    }

    public boolean getClawSol(){
        return true;
    }

    public void toggleClawSol(){
        clawSol.toggle();
    }
    public void setClawSol(){
        clawSol.set(kForward);
    }

}
