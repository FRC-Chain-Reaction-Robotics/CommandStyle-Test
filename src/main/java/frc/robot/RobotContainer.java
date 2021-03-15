// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.*;
import frc.robot.commands.*;
import frc.robot.commands.drive.*;
import frc.robot.commands.shoot.*;
import frc.robot.commands.skills_challenges.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer
{
  	// The robot's subsystems and commands are defined here...

	// private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

	private final Limelight ll = new Limelight();	
	private final Mecanum dt = new Mecanum(ll);
	private final Shooter shooter = new Shooter();
	private final Intake intake = new Intake();
	private final Feeder feeder = new Feeder();
	
	XboxController driverController = new XboxController(0);


	AutoCommand autoCommand = new AutoCommand(dt, intake, feeder, shooter);

	
  	/** The container for the robot. Contains subsystems, OI devices, and commands. */
	public RobotContainer() {
	// Configure the button bindings
	configureButtonBindings();
	 // Configure default commands
	
	dt.setDefaultCommand(
		// A split-stick arcade command, with forward/backward controlled by the left
		// hand, and turning controlled by the right.
		new RunCommand(
		() -> dt.drive(
			driverController.getY(GenericHID.Hand.kLeft),
			driverController.getX(GenericHID.Hand.kLeft),
			driverController.getX(GenericHID.Hand.kRight)),
		dt));
	}

	/**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
	private void configureButtonBindings()
	{
	
	}

	/**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
	public Command getAutonomousCommand()
	{
		// var auto = new ShootCommand(Shooter.RPM_10FTLINE, shooter);
		Command auto = new ExamplePath(dt);
		return auto;
	}
}
