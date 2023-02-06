package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.List;

import org.photonvision.PhotonUtils;

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
    double heightOfCam = 30; //the hieght of the limelight camera meters to the ground, Change
    double camAngle = 15.0; // angles of limelight camera, CHNAGE
    private PhotonCamera camera = new PhotonCamera("photonvision");
    private List<PhotonTrackedTarget> targetList;
    PhotonTrackedTarget target;


    //give the horizontal distance from robot to the mid pole
    //angleOffSet is f=given by limelight (ty)
    public double getDistanceMid(){
       
        //calculateDistanceToTarget(cameraHeight: meters, targetHeight: meters, cameraPitch: radians, targetPitch: radians)
        double cameraPitchInRadians = camAngle * (Math.PI/180);
        double targetPitchRadians = target.getPitch() * (Math.PI/180);
        return PhotonUtils.calculateDistanceToTargetMeters(heightOfCam, heightMid, cameraPitchInRadians, targetPitchRadians);
        //return (heightMid-heightOfCam)/(Math.tan(totAngleToRadian));
    }

    public double getDistanceHigh () {
      double cameraPitchInRadians = camAngle * (Math.PI/180);
      double targetPitchRadians = target.getPitch() * (Math.PI/180);
      return PhotonUtils.calculateDistanceToTargetMeters(heightOfCam, heightHigh, cameraPitchInRadians, targetPitchRadians);
    }

    


  //yaw -> x
  //Pitch -> y
  // distance from camera hiehgt, target height and camera pitch(radians)
  //choose target based of its y

  //Checks if there are targets
  public boolean checkTargets() {
    var result = camera.getLatestResult();
    targetList = result.getTargets();
    return (result.getTargets().size() >= 1);
  }

  //finds the two targets closest to the center
  private ArrayList<PhotonTrackedTarget> assignTargets(){
    ArrayList<PhotonTrackedTarget> groupTargets = new ArrayList<>();
    ArrayList<PhotonTrackedTarget> copy = new ArrayList<>(targetList);
    int minIndex = 0;
    double firstMin = copy.get(0).getYaw();
    double secondMin;
    for(int i = 0; i < copy.size(); i++){
      if(Math.abs(copy.get(i).getYaw())< firstMin){
        firstMin = copy.get(i).getYaw();
        minIndex = i;
      }
    }
    groupTargets.add(copy.get(minIndex));
    copy.remove(minIndex);
    secondMin = copy.get(0).getYaw();
    minIndex = 0;
    for(int i = 0; i < copy.size(); i++){
      if(Math.abs(copy.get(i).getYaw())< secondMin){
        secondMin = copy.get(i).getYaw();
        minIndex = i;
      }
    }
    if(Math.abs(firstMin-secondMin)<5){
      groupTargets.add(copy.get(minIndex));
    }
    return groupTargets;
  }

  //assigns the mid Target
  public void assignMid(){
    ArrayList <PhotonTrackedTarget> groupTargets = assignTargets();
    if(groupTargets.size() == 2){
      if(groupTargets.get(0).getPitch() > groupTargets.get(1).getPitch()){
        target = groupTargets.get(1);
      }else{
        target = groupTargets.get(0);
      }
    }
    target = groupTargets.get(0);
    
  }

  //assign high target
  public void assignHigh(){
    ArrayList <PhotonTrackedTarget> groupTargets = assignTargets();
    if(groupTargets.size() == 2){
      if(groupTargets.get(0).getPitch() > groupTargets.get(1).getPitch()){
        target = groupTargets.get(0);
      }else{
        target = groupTargets.get(1);
      }
    }
    target = groupTargets.get(0);
  }

  public double getYaw(){
    return target.getYaw();
  }

  public double getPitch(){
    return target.getPitch();
  }

  
  

  



    
}
