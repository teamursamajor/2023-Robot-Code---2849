package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.SPI;

import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.*;

public class DriveSubsystem extends SubsystemBase {
    private WPI_VictorSPX motorFrontLeft, motorFrontRight, motorBackLeft, motorBackRight;
    private WPI_TalonFX motorFrontLeftTalon, motorFrontRightTalon, motorBackLeftTalon, motorBackRightTalon;
    
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
        /*motorFrontLeft = new WPI_VictorSPX(3);
        motorFrontRight = new WPI_VictorSPX(2);
        motorBackLeft = new WPI_VictorSPX(0);
        motorBackRight = new WPI_VictorSPX(1);
        */

        motorFrontLeftTalon = new WPI_TalonFX(4);
        motorBackLeftTalon = new WPI_TalonFX(1);
        motorFrontRightTalon = new WPI_TalonFX(0);
        motorBackRightTalon = new WPI_TalonFX(3);

        //motorFrontLeft.setInverted(true);
        //motorBackLeft.setInverted(true);

        m_drive = new MecanumDrive(motorFrontLeftTalon, motorBackLeftTalon, motorFrontRightTalon,
                motorBackRightTalon);
        m_drive.setSafetyEnabled(false);
        zeroYaw();
    }
//-30
    public void drive(double fowardBack, double leftRight, double rotation) {
        // ChassisSpeeds chassisSpeed = new ChassisSpeeds(fowardBack, leftRight,
        // rotation);
        // MecanumDriveWheelSpeeds wheelSpeeds =
        // m_kinematics.toWheelSpeeds(chassisSpeed);
        m_drive.driveCartesian(fowardBack, leftRight, rotation);

    }

    public void driveField(double fowardBack, double leftRight, double rotation) {
        m_drive.driveCartesian(fowardBack, leftRight, rotation, new Rotation2d(getAngleYaw()));
    }

    public float getAnglePitch(){
        return ahrs.getPitch();
    }

    public void zeroYaw() {
        ahrs.zeroYaw();
    }

    public float getAngleYaw() {
        return ahrs.getYaw();
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

    public boolean isCalibrate(){
        return ahrs.isCalibrating();
    }

}
