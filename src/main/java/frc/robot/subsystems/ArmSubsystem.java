package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import static frc.robot.Constants.*;

import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase {
    // control for closing claw
    private DoubleSolenoid clawSol = new DoubleSolenoid(PneumaticsModuleType.REVPH, 0, 15);
    private DoubleSolenoid armSol = new DoubleSolenoid(PneumaticsModuleType.REVPH, 8, 7);

    Compressor comp = new Compressor(1, PneumaticsModuleType.REVPH);

    public ArmSubsystem() {
        // clawServo.setBounds(2, 1.8, 1.5, 1.2, 1); KEEP THIS
        // winchMotorTalon.setNeutralMode(NeutralMode.Brake);
        /*PneumaticsControlModule control = new PneumaticsControlModule();
        PneumaticHub control1 = new PneumaticHub();
        */
        //debugTab.addNumber("Pressure", ()->{return comp.getPressure();});
        //comp.enableAnalog(75.0, 115.0);
        //comp.enableHybrid(75, 115);
        comp.enableDigital();
        System.out.println(clawSol.isFwdSolenoidDisabled());
        setClawSol(true);
        setArmSol(true);
    }

    public void setClawSol(boolean closeOrNot) {
        clawSol.set(closeOrNot ? kForward : kReverse);
    }

    public void setArmSol(boolean closeOrNot) {
        armSol.set(closeOrNot ? kForward : kReverse);
    }

    public void turnOffClawSol() {
        clawSol.set(kOff);
    }

    public void turnOffArmSol() {
        armSol.set(kOff);
    }

    public void toggleClawSol() {
        clawSol.toggle();
    }

    public void toggleArmSol() {
        armSol.toggle();
    }

}
