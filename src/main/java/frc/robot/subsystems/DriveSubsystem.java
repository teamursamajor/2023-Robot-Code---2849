package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.hal.SimDevice;
import edu.wpi.first.wpilibj.motorcontrol.Victor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.*;

public class DriveSubsystem extends SubsystemBase {
    private TalonFX motorFrontLeftTalonFX, motorFrontRightTalonFX, motorBackLeftTalonFX, motorBackRightTalonFX;
    private SimDevice motorFrontLeftSimDevice, motorFrontRightSimDevice, motorBackLeftSimDevice, motorBackRightSimDevice;
    private Victor motorFrontLeftVictor, motorFrontRightVictor, motorBackLeftVictor, motorBackRightVictor;
    private MotorType motorType;

    public DriveSubsystem(MotorType motor){
        switch(motor){
            case TALON_FX:
                motorFrontLeftTalonFX = new TalonFX(MECANUM_FRONT_LEFT_PORT);
                motorFrontRightTalonFX = new TalonFX(MECANUM_FRONT_RIGHT_PORT);
                motorBackRightTalonFX = new TalonFX(MECANUM_BACK_RIGHT_PORT);
                motorBackLeftTalonFX = new TalonFX(MECANUM_BACK_LEFT_PORT);
                break;
            case SIM_DEVICE:
                motorFrontLeftSimDevice = new SimDevice(MECANUM_FRONT_LEFT_PORT);
                motorFrontRightSimDevice = new SimDevice(MECANUM_FRONT_RIGHT_PORT);
                motorBackLeftSimDevice = new SimDevice(MECANUM_BACK_LEFT_PORT);
                motorBackRightSimDevice = new SimDevice(MECANUM_BACK_RIGHT_PORT);
            case VICTOR:
                motorFrontLeftVictor = new Victor(MECANUM_FRONT_LEFT_PORT);
                motorFrontRightVictor = new Victor(MECANUM_FRONT_RIGHT_PORT);
                motorBackLeftVictor = new Victor(MECANUM_BACK_LEFT_PORT);
                motorBackRightVictor = new Victor(MECANUM_BACK_RIGHT_PORT);
        }

        

            
    }


}
