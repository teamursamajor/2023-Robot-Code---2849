package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.MecanumDriveKinematics;
import edu.wpi.first.math.kinematics.MecanumDriveWheelSpeeds;
import edu.wpi.first.wpilibj.SPI;

import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import static frc.robot.Constants.*;

public class DriveSubsystem extends SubsystemBase {
    private WPI_VictorSPX motorFrontLeft, motorFrontRight, motorBackLeft, motorBackRight;
    private WPI_TalonFX motorFrontLeftTalon, motorFrontRightTalon, motorBackLeftTalon, motorBackRightTalon;
    
    private double frontLeft, frontRight, backLeft, backRight;

    //private final MecanumDrive m_drive;

    AHRS ahrs = new AHRS(SPI.Port.kMXP);
    Translation2d m_frontLeftLocation = new Translation2d(0.381, 0.381);
    Translation2d m_frontRightLocation = new Translation2d(0.381, -0.381);
    Translation2d m_backLeftLocation = new Translation2d(-0.381, 0.381);
    Translation2d m_backRightLocation = new Translation2d(-0.381, -0.381);

    MecanumDriveKinematics kinematics = new MecanumDriveKinematics(
  m_frontLeftLocation, m_frontRightLocation, m_backLeftLocation, m_backRightLocation
    );

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

        motorFrontLeftTalon.configFactoryDefault();
		motorFrontLeftTalon.configNeutralDeadband(0.001);

        motorBackLeftTalon.configFactoryDefault();
		motorBackLeftTalon.configNeutralDeadband(0.001);

        motorFrontRightTalon.configFactoryDefault();
		motorFrontRightTalon.configNeutralDeadband(0.001);

        motorBackRightTalon.configFactoryDefault();
		motorBackRightTalon.configNeutralDeadband(0.001);
        

        motorFrontLeftTalon.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor , 0 , 10);
        motorFrontRightTalon.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor , 0 , 10);
        motorBackLeftTalon.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor , 0 , 10);
        motorBackRightTalon.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor , 0 , 10);

        motorFrontLeftTalon.config_kF(0, 1023.0/20660.0, 30);
		motorFrontLeftTalon.config_kP(0, .1, 30);
		motorFrontLeftTalon.config_kI(0, 0.001, 30);
		motorFrontLeftTalon.config_kD(0, 5, 30);

        motorBackLeftTalon.config_kF(0, 1023.0/20660.0, 30);
		motorBackLeftTalon.config_kP(0, .1, 30);
		motorBackLeftTalon.config_kI(0, 0.001, 30);
		motorBackLeftTalon.config_kD(0, 5, 30);

        motorFrontRightTalon.config_kF(0, 1023.0/20660.0, 30);
		motorFrontRightTalon.config_kP(0, .1, 30);
		motorFrontRightTalon.config_kI(0, 0.001, 30);
		motorFrontRightTalon.config_kD(0, 5, 30);

        motorBackRightTalon.config_kF(0, 1023.0/20660.0, 30);
		motorBackRightTalon.config_kP(0, .1, 30);
		motorBackRightTalon.config_kI(0, 0.001, 30);
		motorBackRightTalon.config_kD(0, 5, 30);


        motorFrontLeftTalon.setInverted(true);
        motorBackLeftTalon.setInverted(true);

        /*m_drive = new MecanumDrive(motorFrontLeftTalon, motorBackLeftTalon, motorFrontRightTalon,
                motorBackRightTalon);
        m_drive.setSafetyEnabled(false);
        */
        zeroYaw();
        
    }
//-30
    public void drive(double fowardBack, double leftRight, double rotation) {
        // ChassisSpeeds chassisSpeed = new ChassisSpeeds(fowardBack, leftRight,
        // rotation);
        // MecanumDriveWheelSpeeds wheelSpeeds =
        // m_kinematics.toWheelSpeeds(chassisSpeed);
        double fowardBackSpeed = fowardBack *1500;
        double leftRightSpeed = leftRight * 750;
        double rotationSpeed = rotation * 1500;

        ChassisSpeeds speeds = new ChassisSpeeds(fowardBackSpeed, leftRightSpeed, rotationSpeed);
        
        MecanumDriveWheelSpeeds wheelSpeeds = kinematics.toWheelSpeeds(speeds);
        System.out.println("Front Left: "+wheelSpeeds.frontLeftMetersPerSecond);
        System.out.println("Front Right: "+wheelSpeeds.frontRightMetersPerSecond);
        System.out.println("Back Left: "+wheelSpeeds.rearLeftMetersPerSecond);
        System.out.println("Back Right: "+wheelSpeeds.rearRightMetersPerSecond);


        motorFrontLeftTalon.set(TalonFXControlMode.Velocity, wheelSpeeds.frontLeftMetersPerSecond);
        motorFrontRightTalon.set(TalonFXControlMode.Velocity, wheelSpeeds.frontRightMetersPerSecond);
        motorBackRightTalon.set(TalonFXControlMode.Velocity, wheelSpeeds.rearRightMetersPerSecond);
        motorBackLeftTalon.set(TalonFXControlMode.Velocity, wheelSpeeds.rearLeftMetersPerSecond);

        

    

        //m_drive.driveCartesian(.5, 0, 0);

        /*System.out.println("Front Left: "+motorFrontLeftTalon.getSelectedSensorVelocity()/motorFrontLeftTalon.getMotorOutputPercent());
        System.out.println("Front Right: "+motorFrontRightTalon.getSelectedSensorVelocity()/motorFrontRightTalon.getMotorOutputPercent());
        System.out.println("Back Left: "+motorBackLeftTalon.getSelectedSensorVelocity()/motorBackLeftTalon.getMotorOutputPercent());
        System.out.println("Back Right: "+motorBackRightTalon.getSelectedSensorVelocity()/motorBackRightTalon.getMotorOutputPercent());
        */

    }

    public void driveField(double fowardBack, double leftRight, double rotation) {  
        //m_drive.driveCartesian(fowardBack, leftRight, rotation, new Rotation2d(getAngleYaw()));
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
