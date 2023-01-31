package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LimeLightSubsystem extends SubsystemBase {
    double heightMid = 56.0; // the height of the mid pole in cm
    double heightHigh = 106; // the height of the high pole in cm
    double heightOfCam = 30; //the hieght of the limelight camera to the ground, CHNAGE
    double camAngle = 15.0; // angles of limelight camera, CHNAGE
    


    //give the horizontal distance from robot to the mid pole
    //angleOffSet is f=given by limelight (ty)
    public double getDistanceMid(){
        //placeholder so code is not mad
        //h2 is heightMid
        //a2 is angleOffSet
        //h1 is heightOfCam
        //a1 is camAngle
        //d = (h2-h1) / tan(a1+a2)   formula
        double totAngle = camAngle+getOffsetAngle();
        double totAngleToRadian = totAngle * (3.14159 / 180.0);
        return (heightMid-heightOfCam)/(Math.tan(totAngleToRadian));
    }

    public double getDistanceHigh () {
        //h2 is heightHigh
        //a2 is angleOffSet
        //h1 is heightOfCam
        //a1 is camAngle
        //d = (h2-h1) / tan(a1+a2)   formula
        double totAngle = camAngle+getOffsetAngle();
        double totAngleToRadian = totAngle * (3.14159 / 180.0);
        return (heightMid-heightOfCam)/(Math.tan(totAngleToRadian));
    }

    




      public double getX() {
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        NetworkTableEntry tx = table.getEntry("tx");
        NetworkTableEntry tv = table.getEntry("tv");
        double x;

        double isThereLimelight = tv.getDouble(0.0);
        if (isThereLimelight == 0) {
            x = Double.MIN_VALUE;
        } else {
            x = tx.getDouble(Double.MIN_VALUE);
        }
    
        SmartDashboard.putNumber("LimelightX", x);
        return x;
  }

  private double getOffsetAngle(){
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry ty = table.getEntry("ty");
    double targetOffsetAngle_Vertical = ty.getDouble(Double.MIN_VALUE);
    return targetOffsetAngle_Vertical;
  }

  

  



    
}
