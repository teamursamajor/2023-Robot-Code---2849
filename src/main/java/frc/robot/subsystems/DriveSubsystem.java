package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.MecanumDriveKinematics;
import edu.wpi.first.math.kinematics.MecanumDriveWheelSpeeds;
import edu.wpi.first.wpilibj.SPI;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.*;

public class DriveSubsystem extends SubsystemBase {
    private WPI_TalonFX motorFrontLeftTalon, motorFrontRightTalon, motorBackLeftTalon, motorBackRightTalon;
    private float yawAligned;

    AHRS ahrs = new AHRS(SPI.Port.kMXP);
    Translation2d m_frontLeftLocation = new Translation2d(0.381, 0.381);
    Translation2d m_frontRightLocation = new Translation2d(0.381, -0.381);
    Translation2d m_backLeftLocation = new Translation2d(-0.381, 0.381);
    Translation2d m_backRightLocation = new Translation2d(-0.381, -0.381);

    MecanumDriveKinematics kinematics = new MecanumDriveKinematics(
            m_frontLeftLocation, m_frontRightLocation, m_backLeftLocation, m_backRightLocation);

    // private final PWMSparkMax m_frontLeft = new
    // PWMSparkMax(MECANUM_FRONT_LEFT_PORT);
    // private final PWMSparkMax m_rearLeft = new
    // PWMSparkMax(MECANUM_BACK_LEFT_PORT);
    // private final PWMSparkMax m_frontRight = new
    // PWMSparkMax(MECANUM_FRONT_RIGHT_PORT);
    // private final PWMSparkMax m_rearRight = new
    // PWMSparkMax(MECANUM_BACK_RIGHT_PORT);

    public DriveSubsystem() {

        motorFrontLeftTalon = new WPI_TalonFX(4);
        motorBackLeftTalon = new WPI_TalonFX(1);
        motorFrontRightTalon = new WPI_TalonFX(0);
        motorBackRightTalon = new WPI_TalonFX(3);

        motorFrontLeftTalon.setNeutralMode(NeutralMode.Coast);
        motorBackLeftTalon.setNeutralMode(NeutralMode.Coast);
        motorFrontRightTalon.setNeutralMode(NeutralMode.Coast);
        motorBackRightTalon.setNeutralMode(NeutralMode.Coast);

        motorFrontLeftTalon.configFactoryDefault();
        motorFrontLeftTalon.configNeutralDeadband(.01);

        motorBackLeftTalon.configFactoryDefault();
        motorBackLeftTalon.configNeutralDeadband(.01);

        motorFrontRightTalon.configFactoryDefault();
        motorFrontRightTalon.configNeutralDeadband(0.01);

        motorBackRightTalon.configFactoryDefault();
        motorBackRightTalon.configNeutralDeadband(0.01);

        motorFrontLeftTalon.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 10);
        motorFrontRightTalon.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 10);
        motorBackLeftTalon.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 10);
        motorBackRightTalon.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 10);

        motorFrontLeftTalon.config_kF(0, 1023.0 / 20660.0, 30);
        motorFrontLeftTalon.config_kP(0, .1, 30);
        motorFrontLeftTalon.config_kI(0, 0.001, 30);
        motorFrontLeftTalon.config_kD(0, 5, 30);

        motorBackLeftTalon.config_kF(0, 1023.0 / 20660.0, 30);
        motorBackLeftTalon.config_kP(0, .1, 30);
        motorBackLeftTalon.config_kI(0, 0.001, 30);
        motorBackLeftTalon.config_kD(0, 5, 30);

        motorFrontRightTalon.config_kF(0, 1023.0 / 20660.0, 30);
        motorFrontRightTalon.config_kP(0, .1, 30);
        motorFrontRightTalon.config_kI(0, 0.001, 30);
        motorFrontRightTalon.config_kD(0, 5, 30);

        motorBackRightTalon.config_kF(0, 1023.0 / 20660.0, 30);
        motorBackRightTalon.config_kP(0, .1, 30);
        motorBackRightTalon.config_kI(0, 0.001, 30);
        motorBackRightTalon.config_kD(0, 5, 30);

        zeroYaw();
        driverTab.addNumber("Pitch", () -> {
            return getAnglePitch();
        });
        driverTab.addNumber("Roll", () -> {
            return getAngleRoll();
        });
        driverTab.addNumber("Yaw", () -> {
            return getAngleYaw();
        });
        debugTab.addNumber("Front Left Talon", () -> {
            return motorFrontLeftTalon.getSelectedSensorVelocity();
        });
        debugTab.addNumber("Front Right Talon", () -> {
            return motorFrontRightTalon.getSelectedSensorVelocity();
        });
        debugTab.addNumber("Back Left Talon", () -> {
            return motorBackLeftTalon.getSelectedSensorVelocity();
        });
        debugTab.addNumber("Back Right Talon", () -> {
            return motorBackRightTalon.getSelectedSensorVelocity();
        });
    }

    public void driveDistance(double fowardBackDist, double leftRightDist, double rotation) {

    }

    public void drive(double fowardBack, double leftRight, double rotation) {
        double fowardBackSpeed = 0;
        double leftRightSpeed = 0;
        double rotationSpeed = 0;
        if (Math.abs(fowardBack) >= .1) {
            fowardBackSpeed = fowardBack * 6000;
        }
        if (Math.abs(leftRight) >= .1) {
            leftRightSpeed = leftRight * 5000;
        }
        if (Math.abs(rotation) >= .1) {
            rotationSpeed = rotation * 6000;
        }

        ChassisSpeeds speeds = new ChassisSpeeds(fowardBackSpeed, leftRightSpeed, rotationSpeed);
        MecanumDriveWheelSpeeds wheelSpeeds = kinematics.toWheelSpeeds(speeds);
        double multipleSped = 1.17;

        motorFrontLeftTalon.set(TalonFXControlMode.Velocity, wheelSpeeds.frontLeftMetersPerSecond * multipleSped);
        motorFrontRightTalon.set(TalonFXControlMode.Velocity, wheelSpeeds.frontRightMetersPerSecond);
        motorBackRightTalon.set(TalonFXControlMode.Velocity, wheelSpeeds.rearRightMetersPerSecond);
        motorBackLeftTalon.set(TalonFXControlMode.Velocity, wheelSpeeds.rearLeftMetersPerSecond * multipleSped);

        // System.out.println("Front Left: " +
        // motorFrontLeftTalon.getSelectedSensorVelocity());
        // System.out.println("Front Right: " +
        // motorFrontRightTalon.getSelectedSensorVelocity());
        // System.out.println("Back Left: " +
        // motorBackLeftTalon.getSelectedSensorVelocity());
        // System.out.println("Back Right: " +
        // motorBackRightTalon.getSelectedSensorVelocity());

    }

    public float getAnglePitch() {
        return ahrs.getPitch();
    }

    public void zeroYaw() {
        ahrs.zeroYaw();
    }

    public float getAngleYaw() {
        return ahrs.getYaw();
    }

    public float getAngleRoll() {
        return ahrs.getRoll();
    }

    public boolean isCalibrating() {
        return ahrs.isCalibrating();
    }

    public boolean isConnected() {
        return ahrs.isConnected();
    }

    public void calibrate() {
        ahrs.calibrate();
    }

    public boolean isCalibrate() {
        return ahrs.isCalibrating();
    }

    public void setYawAlign(float yaw) {
        yawAligned = yaw;
    }

    public double getYawAlign() {
        return (double) yawAligned;
    }

}
