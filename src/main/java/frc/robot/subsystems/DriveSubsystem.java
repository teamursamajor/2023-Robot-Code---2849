package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.I2C;


import edu.wpi.first.hal.SimDevice;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.MecanumDriveWheelSpeeds;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.motorcontrol.Victor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.*;

public class DriveSubsystem extends SubsystemBase {
    private TalonFX motorFrontLeftTalonFX, motorFrontRightTalonFX, motorBackLeftTalonFX, motorBackRightTalonFX;
    private SimDevice motorFrontLeftSimDevice, motorFrontRightSimDevice, motorBackLeftSimDevice,
            motorBackRightSimDevice;
    private WPI_VictorSPX motorFrontLeftVictor, motorFrontRightVictor, motorBackLeftVictor, motorBackRightVictor;
    private MotorType motorType;
    private double frontLeft, frontRight, backLeft, backRight;

    private final MecanumDrive m_drive;

    AHRS ahrs = new AHRS(SPI.Port.kMXP);

    // private final PWMSparkMax m_frontLeft = new
    // PWMSparkMax(MECANUM_FRONT_LEFT_PORT);
    // private final PWMSparkMax m_rearLeft = new
    // PWMSparkMax(MECANUM_BACK_LEFT_PORT);
    // private final PWMSparkMax m_frontRight = new
    // PWMSparkMax(MECANUM_FRONT_RIGHT_PORT);
    // private final PWMSparkMax m_rearRight = new
    // PWMSparkMax(MECANUM_BACK_RIGHT_PORT);

    public DriveSubsystem(MotorType motor) {
        switch (motor) {
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
                break;
            case VICTOR:
                motorFrontLeftVictor = new WPI_VictorSPX(3);
                motorFrontRightVictor = new WPI_VictorSPX(2);
                motorBackLeftVictor = new WPI_VictorSPX(0);
                motorBackRightVictor = new WPI_VictorSPX(1);
                // motorFrontLeftVictor.setInverted(true);
                // motorFrontRightVictor.setInverted(true);
                break;
        }

        m_drive = new MecanumDrive(motorFrontLeftVictor, motorBackLeftVictor, motorFrontRightVictor,
                motorBackRightVictor);

    }

    public void drive(double fowardBack, double leftRight, double rotation) {
        // ChassisSpeeds chassisSpeed = new ChassisSpeeds(fowardBack, leftRight,
        // rotation);
        // MecanumDriveWheelSpeeds wheelSpeeds =
        // m_kinematics.toWheelSpeeds(chassisSpeed);
        m_drive.driveCartesian(leftRight, fowardBack, rotation);

    }

    public float getAnglePitch(){
        return ahrs.getPitch();
    }

    public float getAngleRoll(){
        return ahrs.getRoll();
    }

    public boolean isCalibrating() {
        return ahrs.isCalibrating();
    }

    public boolean isConnected() {
        return ahrs.isConnected();
    }

    public void calibrate(){
        ahrs.calibrate();
    }

}
