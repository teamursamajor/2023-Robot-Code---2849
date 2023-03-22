package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.hal.SimDevice;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.MecanumDriveKinematics;
import edu.wpi.first.math.kinematics.MecanumDriveWheelSpeeds;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.*;

public class DriveSubsystem extends SubsystemBase {
    private float yawAligned;

    AHRS ahrs = new AHRS(SPI.Port.kMXP);
    Translation2d m_frontLeftLocation = new Translation2d(0.381, 0.381);
    Translation2d m_frontRightLocation = new Translation2d(0.381, -0.381);
    Translation2d m_backLeftLocation = new Translation2d(-0.381, 0.381);
    Translation2d m_backRightLocation = new Translation2d(-0.381, -0.381);

    MecanumDriveKinematics kinematics = new MecanumDriveKinematics(
            m_frontLeftLocation, m_frontRightLocation, m_backLeftLocation, m_backRightLocation);
    private final Spark m_frontLeft = new Spark(3);
    private final Spark m_rearLeft = new Spark(1);
    private final Spark m_frontRight = new Spark(2);
    private final Spark m_rearRight = new Spark(0);

    public DriveSubsystem() {

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

        
        m_frontLeft.set(wheelSpeeds.frontLeftMetersPerSecond * multipleSped);
        m_frontRight.set(wheelSpeeds.frontRightMetersPerSecond);
        m_rearRight.set(wheelSpeeds.rearRightMetersPerSecond);
        m_rearLeft.set(wheelSpeeds.rearLeftMetersPerSecond * multipleSped);

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
