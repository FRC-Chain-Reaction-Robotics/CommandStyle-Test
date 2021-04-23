// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;

import static edu.wpi.first.wpilibj.GenericHID.Hand.*;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj2.command.button.*;
import static edu.wpi.first.wpilibj.XboxController.Button.*;

import frc.robot.commands.*;
import frc.robot.commands.drive.AimCommand;
import frc.robot.commands.shoot.*;
import frc.robot.subsystems.*;
import frc.robot.subsystems.Lights.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer
{
  	// The robot's subsystems and commands are defined here...
	Limelight ll = new Limelight();	
	Mecanum dt = new Mecanum();
	Shooter shooter = new Shooter();
	Intake intake = new Intake();
	Feeder feeder = new Feeder();
	PDP pdp = new PDP();
	Lightz2Controller lightzController = new Lightz2Controller(shooter, feeder, dt, ll);
	Lightz lightz = new Lightz();
	
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
		
		lightzController.setDefaultCommand(new RunCommand(
			() -> lightzController.updateMode(
					operatorController.getXButton(), 
					driverController.getBumper(kRight)), lightzController));
	}

	/**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
	private void configureButtonBindings()
	{
		//#region rumble commands
		var rumbleOnCommand = new RunCommand(() -> operatorController.setRumble(RumbleType.kLeftRumble, 1.0))
			.alongWith(new RunCommand(() -> operatorController.setRumble(RumbleType.kRightRumble, 1.0)))
			.alongWith(new RunCommand(() -> driverController.setRumble(RumbleType.kLeftRumble, 1.0)))
			.alongWith(new RunCommand(() -> driverController.setRumble(RumbleType.kRightRumble, 1.0)));
			
		var rumbleOffCommand = new RunCommand(() -> operatorController.setRumble(RumbleType.kLeftRumble, 0.0))
			.alongWith(new RunCommand(() -> operatorController.setRumble(RumbleType.kRightRumble, 0.0)))
			.alongWith(new RunCommand(() -> driverController.setRumble(RumbleType.kLeftRumble, 0.0)))
			.alongWith(new RunCommand(() -> driverController.setRumble(RumbleType.kRightRumble, 0.0)));
		//#endregion

		//#region controls
		var feederUpButton = new POVButton(operatorController, 0).or(new POVButton(driverController, 0));
		var feederDownButton = new POVButton(operatorController, 180).or(new POVButton(driverController, 180));

		var intakeButton = new JoystickButton(operatorController, kBumperRight.value);
		var intakeReverseButton = new JoystickButton(operatorController, kBumperLeft.value);

		var shootButton = new JoystickButton(operatorController, kX.value);

		var slowModeButton = new JoystickButton(driverController, kBumperLeft.value);
		var aimButton = new JoystickButton(driverController, kBumperRight.value);
		//#endregion

		//#region bindings
		feederUpButton.whileActiveContinuous(new RunCommand(feeder::on, feeder))
			.or(feederDownButton.whileActiveContinuous(new RunCommand(feeder::reverse, feeder))
			.whenInactive(new RunCommand(feeder::off, feeder)));

		intakeButton.whileHeld(new RunCommand(intake::on, intake))
			.or(intakeReverseButton.whileHeld(new RunCommand(intake::reverse, intake)))
			.whenInactive(new RunCommand(intake::off, intake));

		shootButton.whileHeld(new StartShooterCommand(Shooter.RPM_10FTLINE, shooter).alongWith(rumbleOnCommand))
					.whenInactive(new StopShooterCommand(shooter).alongWith(rumbleOffCommand));

		slowModeButton.whenActive(new RunCommand(() -> dt.setMaxOutput(Mecanum.SLOW_MODE_SPEED), dt))
					.whenInactive(new RunCommand(() -> dt.setMaxOutput(Mecanum.TELEOP_SPEED), dt));

		aimButton.whileHeld(new AimCommand(dt, ll))
					.whenInactive(new RunCommand(
						() -> dt.drive(
							driverController.getX(kLeft),
							-driverController.getY(kLeft),
							driverController.getX(kRight)),
						dt));
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
		// return new AimCommand(dt, ll).andThen(new ShootCommand(Shooter.RPM_10FTLINE, shooter, feeder));
	}
}
