// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.*;
import static edu.wpi.first.wpilibj.GenericHID.Hand.*;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj2.command.button.*;
import static edu.wpi.first.wpilibj.XboxController.Button.*;

import frc.robot.commands.*;
import frc.robot.commands.shoot.*;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer
{
  	// The robot's subsystems and commands are defined here...
	private final Limelight ll = new Limelight();	
	private final Mecanum dt = new Mecanum();
	private final Shooter shooter = new Shooter();
	private final Intake intake = new Intake();
	private final Feeder feeder = new Feeder();
	
	XboxController driverController = new XboxController(0);
	XboxController operatorController = new XboxController(1);
	
	/** The container for the robot. Contains subsystems, OI devices, and commands. */
	public RobotContainer()
	{
		// Configure the button bindings
		configureButtonBindings();
		
		// Configure default commands
		
		dt.setDefaultCommand(
			new RunCommand(
			() -> dt.drive(
				driverController.getX(kLeft),
				-driverController.getY(kLeft),
				driverController.getX(kRight)),
			dt));

		shooter.setDefaultCommand(new StopShooterCommand(shooter).perpetually());
		feeder.setDefaultCommand(new RunCommand(feeder::off, feeder));
		intake.setDefaultCommand(new RunCommand(intake::off, intake));
	}

	/**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
	private void configureButtonBindings()
	{
		//#region controls
		var feederUpButton = new POVButton(driverController, 0);
		var feederDownButton = new POVButton(driverController, 180);

		var intakeButton = new JoystickButton(driverController, kBumperRight.value);
		var intakeReverseButton = new JoystickButton(driverController, kBumperLeft.value);

		var shootButton = new JoystickButton(driverController, kX.value);

		var slowModeButton = new Trigger(() -> driverController.getTriggerAxis(kLeft) > 0);
		//#endregion

		//#region bindings
		feederUpButton.whileHeld(feeder::on, feeder)
			.or(feederDownButton.whileHeld(feeder::reverse, feeder))
			.whenInactive(feeder::off, feeder);

		intakeButton.whileHeld(intake::on, intake)
			.or(intakeReverseButton.whileHeld(intake::reverse, intake))
			.whenInactive(intake::off, intake);

		shootButton.whenActive(new StartShooterCommand(Shooter.RPM_10FTLINE, shooter))
					.whenInactive(new StopShooterCommand(shooter));
		
		slowModeButton.whenActive(() -> dt.setMaxOutput(Mecanum.SLOW_MODE_SPEED), dt).
					whenInactive(() -> dt.setMaxOutput(Mecanum.TELEOP_SPEED), dt);
		//#endregion
	}

	/**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
	public Command getAutonomousCommand()
	{
		return new InfRechAutoCommand(dt, intake, feeder, shooter, ll);
		// return new AimCommand(dt).andThen(new ShootCommand(Shooter.RPM_10FTLINE, shooter, feeder));
	}
}
