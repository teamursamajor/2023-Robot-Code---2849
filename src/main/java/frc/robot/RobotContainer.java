// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static frc.robot.Constants.*;

import frc.robot.commands.AutoBalanceCommand;
import frc.robot.commands.ManualArmCommand;
import frc.robot.commands.ManualClawCommand;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

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
  // private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  // private final GyroSubsystem GYRO_SUBSYSTEM = new GyroSubsystem();
  public final DriveSubsystem m_robotDrive = new DriveSubsystem();
  private final ArmSubsystem m_ArmSubsystem = new ArmSubsystem();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the trigger bindings

    /*m_robotDrive.setDefaultCommand(
        // A split-stick arcade command, with forward/backward controlled by the left
        // hand, and turning controlled by the right.
        new RunCommand(
            () -> m_robotDrive.drive(
                -XBOX_CONTROLLER.getLeftY(),
                -XBOX_CONTROLLER.getLeftX(),
                -XBOX_CONTROLLER.getRightX()),
            m_robotDrive));
        /*
        new RunCommand(new Runnable() {
          public void run() {
            System.out.println(XBOX_CONTROLLER.getRightY());
            System.out.println(XBOX_CONTROLLER.getRightX());
            System.out.println(Math.atan2(XBOX_CONTROLLER.getRightY(), XBOX_CONTROLLER.getRightX()) * 180 / Math.PI);
          }
        }, m_robotDrive));
        */

    configureBindings();
  }

  
  private void configureBindings() {
    //future bingdings:
    //sticks --> moving robot
    //y --> auto balance
    //b --> auto align with high pole
    //a --> auto align with low polw
    //right bumper --> toggle claw
    //right trigger --> toggle arm
    

    XBOX_CONTROLLER.a().onTrue(new AutoBalanceCommand(m_robotDrive));
    XBOX_CONTROLLER.povDown().whileTrue(new ManualArmCommand(m_ArmSubsystem, false));
    XBOX_CONTROLLER.povUp().whileTrue(new ManualArmCommand(m_ArmSubsystem, true));
    XBOX_CONTROLLER.povRight().onTrue(new ManualClawCommand(m_ArmSubsystem, false));
    XBOX_CONTROLLER.povLeft().onTrue(new ManualClawCommand(m_ArmSubsystem, true));
    // new JoystickButton(XBOX_CONTROLLER, Button.kA.value).whileTrue
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return null;
  }
}
