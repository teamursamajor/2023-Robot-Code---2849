package frc.robot.subsystems;

import edu.wpi.first.wpilibj.ADIS16448_IMU;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.ADIS16448_IMU.CalibrationTime;
import edu.wpi.first.wpilibj.ADIS16448_IMU.IMUAxis;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.*;

public class GyroSubsystem extends SubsystemBase {
    private ADIS16448_IMU gyro;

    public GyroSubsystem(){
        gyro = new ADIS16448_IMU(IMUAxis.kY, GYRO_PORT, CalibrationTime._1s);
    }

    public void calibrate(){
        gyro.calibrate();
        gyro.reset();
    }

    public double getXAxis(){
        return gyro.getGyroAngleX();
    }

    public double getYAxis(){
        return gyro.getGyroAngleY();
    }
    public double getZAxis(){
        return gyro.getGyroAngleZ();
    }


    public double getAngle() {
        return gyro.getAngle();
    }


}
