package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.List;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonTrackedTarget;

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
    private PhotonCamera camera = new PhotonCamera("photonvision");
    private List<PhotonTrackedTarget> targetList;
    PhotonTrackedTarget target;


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

    




     /*  public double getX() {
        NetworkTable table = NetworkTableInstance.getDefault().getTable("photonvision");
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
  } */

  //yaw -> x
  //Pitch -> y
  // distance from camera hiehgt, target height and camera pitch(radians)
  //choose target based of its y
  public boolean checkTargets() {
    var result = camera.getLatestResult();
    targetList = result.getTargets();
    return (result.getTargets().size() >= 2);
  }

  private ArrayList<PhotonTrackedTarget> assignTargets(){
    ArrayList<PhotonTrackedTarget> groupTargets = new ArrayList<>();
    ArrayList<PhotonTrackedTarget> copy = new ArrayList<>(targetList);
    int minIndex = 0;
    double min = copy.get(0).getYaw();
    for(int i = 0; i < copy.size(); i++){
      if(Math.abs(copy.get(i).getYaw())< min){
        min = copy.get(i);
        minIndex = i;
      }
    }
    groupTargets.add(copy.get(minIndex));
    copy.remove(minIndex);
    min = copy.get(0).getYaw();
    minIndex = 0;
    for(int i = 0; i < copy.size(); i++){
      if(Math.abs(copy.get(i).getYaw())< min){
        min = copy.get(i);
        minIndex = i;
      }
    }
    groupTargets.add(copy.get(minIndex));
    return groupTargets;
  }

  public void assignMid(){
    ArrayList <PhotonTrackedTarget> groupTargets = assignTargets();
    if(groupTargets.get(0).getPitch() > groupTargets.get(1).getPitch()){
      target = groupTargets.get(1);
    }else{
      target = groupTargets.get(0);
    }
  }

  public void assignHigh(){
    ArrayList <PhotonTrackedTarget> groupTargets = assignTargets();
    if(groupTargets.get(0).getPitch() > groupTargets.get(1).getPitch()){
      target = groupTargets.get(0);
    }else{
      target = groupTargets.get(1);
    }
  }

  public double getYaw(){
    return target.getYaw();
  }

  public double getPitch(){
    return target.getPitch();
  }

  private double getOffsetAngle(){
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry ty = table.getEntry("ty");
    double targetOffsetAngle_Vertical = ty.getDouble(Double.MIN_VALUE);
    return targetOffsetAngle_Vertical;
  }

  

  



    
}
