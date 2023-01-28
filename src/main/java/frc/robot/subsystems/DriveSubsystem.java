package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;

import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.*;

public class DriveSubsystem extends SubsystemBase {
    private WPI_VictorSPX motorFrontLeft, motorFrontRight, motorBackLeft, motorBackRight;
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

    public DriveSubsystem() {
        
        motorFrontLeft = new WPI_VictorSPX(3);
        motorFrontRight = new WPI_VictorSPX(2);
        motorBackLeft = new WPI_VictorSPX(0);
        motorBackRight = new WPI_VictorSPX(1);
        motorFrontLeft.setInverted(true);
        motorBackLeft.setInverted(true);
        m_drive = new MecanumDrive(motorFrontLeft, motorBackLeft, motorFrontRight,
                motorBackRight);
        m_drive.setSafetyEnabled(false);

    }

    public void drive(double fowardBack, double leftRight, double rotation) {
        // ChassisSpeeds chassisSpeed = new ChassisSpeeds(fowardBack, leftRight,
        // rotation);
        // MecanumDriveWheelSpeeds wheelSpeeds =
        // m_kinematics.toWheelSpeeds(chassisSpeed);
        m_drive.driveCartesian(fowardBack, leftRight, rotation);

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
