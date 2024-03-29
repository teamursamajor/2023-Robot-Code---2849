package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.List;

import org.photonvision.PhotonUtils;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.math.geometry.Pose2d;
import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonPoseEstimator;
import static frc.robot.Constants.*;

public class LimeLightSubsystem extends SubsystemBase {
  double heightMid = 0.686; // the height of the mid pole in cm
  double heightHigh = 1.168; // the height of the high pole in cm
  double heightOfCam = .787; // the hieght of the limelight camera meters to the ground, Change
  double camAngle = 0; // angles of limelight camera, CHNAGE
  private PhotonCamera camera = new PhotonCamera("OV5647");
  private List<PhotonTrackedTarget> targetList = new ArrayList<>();
  PhotonTrackedTarget target;

  // PhotonPipelineResult result = camera.getLatestResult();

  // boolean hasTargets = result.hasTargets();
  // PhotonTrackedTarget target = result.getBestTarget();
  // int targetId = target.getFiducialId();

  private PhotonPoseEstimator photonPoseEstimator;

  public LimeLightSubsystem() {
    driverTab.addCamera("Limelight", camera.getName(), "http://photonvision.local:1182/stream.mjpg");
    debugTab.addNumber("Height Mid", () -> {
      return heightMid;
    });
    debugTab.addNumber("Height High", () -> {
      return heightHigh;
    });
    debugTab.addNumber("Height Cam", () -> {
      return heightOfCam;
    });
    debugTab.addNumber("Angle Cam", () -> {
      return camAngle;
    });
    /*
     * try {
     * // Attempt to load the AprilTagFieldLayout that will tell us where the tags
     * are on the field.
     * AprilTagFieldLayout fieldLayout = new
     * AprilTagFieldLayout(AprilTagFields.k2023ChargedUp.m_resourceFile);
     * // Create pose estimator
     * photonPoseEstimator =
     * new PhotonPoseEstimator(
     * fieldLayout, PoseStrategy.MULTI_TAG_PNP, camera, null);// TODO: get cam
     * position
     * photonPoseEstimator.setMultiTagFallbackStrategy(PoseStrategy.LOWEST_AMBIGUITY
     * );
     * } catch (IOException e) {
     * // The AprilTagFieldLayout failed to load. We won't be able to estimate poses
     * if we don't know
     * // where the tags are.
     * DriverStation.reportError("Failed to load AprilTagFieldLayout",
     * e.getStackTrace());
     * photonPoseEstimator = null;
     * }
     */
  }

  public int pipline() {
    return camera.getPipelineIndex();
  }

  // give the horizontal distance from robot to the mid pole
  // angleOffSet is f=given by limelight (ty)
  public double getDistanceMid() {

    // calculateDistanceToTarget(cameraHeight: meters, targetHeight: meters,
    // cameraPitch: radians, targetPitch: radians)
    double cameraPitchInRadians = camAngle * (Math.PI / 180);
    double targetPitchRadians = target.getPitch() * (Math.PI / 180);
    return PhotonUtils.calculateDistanceToTargetMeters(heightOfCam, heightMid, cameraPitchInRadians,
        targetPitchRadians);
    // return (heightMid-heightOfCam)/(Math.tan(totAngleToRadian));
  }

  public double getDistanceHigh() {
    double cameraPitchInRadians = camAngle * (Math.PI / 180);
    double targetPitchRadians = target.getPitch() * (Math.PI / 180);
    return PhotonUtils.calculateDistanceToTargetMeters(heightOfCam, heightHigh, cameraPitchInRadians,
        targetPitchRadians);
  }

  public void test() {

    checkTargets();

    assignMid();
  }

  // yaw -> x
  // Pitch -> y
  // distance from camera hiehgt, target height and camera pitch(radians)
  // choose target based of its y

  // Checks if there are targets
  public boolean checkTargets() {
    var result = camera.getLatestResult();
    targetList = result.getTargets();
    return (result.getTargets().size() >= 1);
  }

  // finds the two targets closest to the center
  private ArrayList<PhotonTrackedTarget> assignTargets() {
    ArrayList<PhotonTrackedTarget> groupTargets = new ArrayList<>();
    ArrayList<PhotonTrackedTarget> copy = new ArrayList<>(targetList);
    int minIndex = 0;
    double firstMin = copy.get(0).getYaw();
    double secondMin;
    for (int i = 0; i < copy.size(); i++) {
      if (Math.abs(copy.get(i).getYaw()) < firstMin) {
        firstMin = copy.get(i).getYaw();
        minIndex = i;
      }
    }
    groupTargets.add(copy.get(minIndex));
    if (targetList.size() < 2) {
      return groupTargets;
    }
    copy.remove(minIndex);
    secondMin = copy.get(0).getYaw();
    minIndex = 0;
    for (int i = 0; i < copy.size(); i++) {
      if (Math.abs(copy.get(i).getYaw()) < secondMin) {
        secondMin = copy.get(i).getYaw();
        minIndex = i;
      }
    }
    if (Math.abs(firstMin - secondMin) < 10) {
      groupTargets.add(copy.get(minIndex));
    }
    return groupTargets;
  }

  // assigns the mid Target
  public void assignMid() {
    ArrayList<PhotonTrackedTarget> groupTargets = assignTargets();
    target = groupTargets.get(0);
    if (groupTargets.size() == 2) {
      if (groupTargets.get(0).getPitch() > groupTargets.get(1).getPitch()) {
        target = groupTargets.get(1);
      } else {
        target = groupTargets.get(0);
      }
    }
  }

  // assign high target
  public void assignHigh() {
    ArrayList<PhotonTrackedTarget> groupTargets = assignTargets();
    target = groupTargets.get(0);
    if (groupTargets.size() == 2) {
      if (groupTargets.get(0).getPitch() > groupTargets.get(1).getPitch()) {
        target = groupTargets.get(0);
      } else {
        target = groupTargets.get(1);
      }
    }

  }

  public double getYaw() {
    if (target != null) {
      return target.getYaw();
    }
    return Double.MIN_VALUE;
  }

  public double getPitch() {
    if (target != null) {
      return target.getPitch();
    }
    return Double.MIN_VALUE;
  }

  public EstimatedRobotPose getEstimatedGlobalPose(Pose2d prevEstimatedRobotPose) {
    if (photonPoseEstimator == null) {
      // The field layout failed to load, so we cannot estimate poses.
      return null; // If fail to get position, null
    }
    photonPoseEstimator.setReferencePose(prevEstimatedRobotPose);
    return photonPoseEstimator.update().get();
  }

  public void aprilTagsTest() {
    // EstimatedRobotPose pose = getEstimatedGlobalPose(null)
    if (checkTargets()) {
      target = targetList.get(0);
    }
  }

  public int getSize() {
    if (target != null) {
      return targetList.size();
    }
    return Integer.MIN_VALUE;
  }

}
