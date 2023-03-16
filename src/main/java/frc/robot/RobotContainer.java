// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static frc.robot.Constants.*;

import frc.robot.commands.ClawCommand;
import frc.robot.commands.ArmCommand;
import frc.robot.commands.AutoBalanceCommand;
import frc.robot.commands.AutoConeDropCommand;
import frc.robot.commands.AutoLeaveBackwards;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LimeLightSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  public final DriveSubsystem m_robotDrive = new DriveSubsystem();
  private final ArmSubsystem m_ArmSubsystem = new ArmSubsystem();
  private final LimeLightSubsystem m_LimeLightSubsystem = new LimeLightSubsystem();
  private AutoBalanceCommand autoBalanceCommand = new AutoBalanceCommand(m_robotDrive);
  private AutoLeaveBackwards autoLeaveBackwards = new AutoLeaveBackwards(m_robotDrive);

  // Replace with CommandPS4Controller or CommandJoystick if needed

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the trigger bindings

    /*
     * m_robotDrive.setDefaultCommand(
     * // A split-stick arcade command, with forward/backward controlled by the left
     * // hand, and turning controlled by the right.
     * new RunCommand(
     * () -> m_robotDrive.drive(
     * -XBOX_CONTROLLER.getLeftY(),
     * -XBOX_CONTROLLER.getLeftX(),
     * -XBOX_CONTROLLER.getRightX()),
     * m_robotDrive));
     * /*
     * new RunCommand(new Runnable() {
     * public void run() {
     * System.out.println(XBOX_CONTROLLER.getRightY());
     * System.out.println(XBOX_CONTROLLER.getRightX());
     * System.out.println(Math.atan2(XBOX_CONTROLLER.getRightY(),
     * XBOX_CONTROLLER.getRightX()) * 180 / Math.PI);
     * }
     * }, m_robotDrive));
     */
    debugTab.add("AutoBalance", autoBalanceCommand);
    configureBindings();
  }

  private void configureBindings() {
    // sticks --> moving robot
    // x --> auto balance
    // pov up --> auto align with high pole
    // pov down --> auto align with low polw
    // right bumper --> toggle claw
    // left bumper --> toggle arm

    // XBOX_CONTROLLER.x().onTrue(new AutoBalanceCommand(m_robotDrive));
    // XBOX_CONTROLLER.povDown().whileTrue(new ManualArmCommand(m_ArmSubsystem,
    // false));
    // XBOX_CONTROLLER.povUp().whileTrue(new ManualArmCommand(m_ArmSubsystem,
    // true));

    // XBOX_CONTROLLER.b().onTrue(new AutoPistonClawCommand(m_ArmSubsystem));
    // XBOX_CONTROLLER.a().onTrue(new AutoArmPistonCommand(m_ArmSubsystem));
    //XBOX_CONTROLLER.b().whileTrue(new ReflectiveTapeTestCommand(m_LimeLightSubsystem, false));
    //XBOX_CONTROLLER.a().whileTrue(new ReflectiveTapeTestCommand(m_LimeLightSubsystem, true));
    
    XBOX_CONTROLLER.a()
        .onTrue(new AutoConeDropCommand(m_robotDrive, m_LimeLightSubsystem, m_ArmSubsystem, false));
    XBOX_CONTROLLER.y().onTrue(new AutoConeDropCommand(m_robotDrive, m_LimeLightSubsystem, m_ArmSubsystem, true));
    XBOX_CONTROLLER.x().toggleOnTrue(autoBalanceCommand);
    XBOX_CONTROLLER.rightBumper().onTrue(new ArmCommand(m_ArmSubsystem));
    XBOX_CONTROLLER.leftBumper().onTrue(new ClawCommand(m_ArmSubsystem));

    // new JoystickButton(XBOX_CONTROLLER, Button.kA.value).whileTrue
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous

    // auto leave backwards followed by balance
    return autoLeaveBackwards.andThen(autoBalanceCommand);
  }
}
