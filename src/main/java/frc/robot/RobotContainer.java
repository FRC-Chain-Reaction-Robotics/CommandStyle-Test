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
import frc.robot.commands.drive.*;
import frc.robot.commands.shoot.*;
import frc.robot.subsystems.*;
import frc.robot.subsystems.Lights.*;
import frc.robot.subsystems.trajectorytesting.*;

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
	MecanumTraj dt = new MecanumTraj();
	Shooter shooter = new Shooter();
	Intake intake = new Intake();
	Feeder feeder = new Feeder();
	PDP pdp = new PDP();
	Lightz2Controller lightzController = new Lightz2Controller(shooter, feeder, dt, ll);
	
	XboxController driverController = new XboxController(0);
	XboxController operatorController = new XboxController(1);
	Saitek flightStick = new Saitek(2);
	
	/** The container for the robot. Contains subsystems, OI devices, and commands. */
	public RobotContainer()
	{
		dt.register();
		//#region some short commands
		var rumbleOnCommand = new RunCommand(() -> operatorController.setRumble(RumbleType.kLeftRumble, 1.0))
			.alongWith(new RunCommand(() -> operatorController.setRumble(RumbleType.kRightRumble, 1.0)))
			.alongWith(new RunCommand(() -> driverController.setRumble(RumbleType.kLeftRumble, 1.0)))
			.alongWith(new RunCommand(() -> driverController.setRumble(RumbleType.kRightRumble, 1.0)));
			
		var rumbleOffCommand = new RunCommand(() -> operatorController.setRumble(RumbleType.kLeftRumble, 0.0))
			.alongWith(new RunCommand(() -> operatorController.setRumble(RumbleType.kRightRumble, 0.0)))
			.alongWith(new RunCommand(() -> driverController.setRumble(RumbleType.kLeftRumble, 0.0)))
			.alongWith(new RunCommand(() -> driverController.setRumble(RumbleType.kRightRumble, 0.0)));

		var driveCommand = new RunCommand(() -> dt.drive(	//	use either controller ;)
				driverController.getX(kLeft) + flightStick.getX(),
				-driverController.getY(kLeft) - flightStick.getY(),
				driverController.getX(kRight) + flightStick.getZRotation(),
				Math.min(Math.abs(flightStick.getZ()), Mecanum.TELEOP_SPEED)),	//	TODO: testmaxoutput?!!
			dt);
		//#endregion

		//#region controls
		var feederUpButton = new POVButton(operatorController, 0).or(new POVButton(flightStick, 0));
		var feederDownButton = new POVButton(operatorController, 180).or(new POVButton(flightStick, 180));	// flight stick or driverController?

		var intakeButton = new JoystickButton(operatorController, kBumperRight.value);
		var intakeReverseButton = new JoystickButton(operatorController, kBumperLeft.value);

		var shootButton = new JoystickButton(operatorController, kX.value);
		
		var slowModeButton = new JoystickButton(driverController, kBumperLeft.value).or(new JoystickButton(flightStick, Saitek.SButtons.kUR.ordinal()));
		var aimButton = new JoystickButton(driverController, kBumperRight.value).or(new JoystickButton(flightStick, Saitek.SButtons.kUL.ordinal()));
		//#endregion


		//#region bindings
		feederUpButton.whileActiveContinuous(new RunCommand(feeder::on, feeder))
			.or(feederDownButton.whileActiveContinuous(new RunCommand(feeder::reverse, feeder))
			.whenInactive(new RunCommand(feeder::off, feeder)));

		intakeButton.whileHeld(new RunCommand(intake::on, intake))
			.or(intakeReverseButton.whileHeld(new RunCommand(intake::reverse, intake)))
			.whenInactive(new RunCommand(intake::off, intake));

		shootButton.whileHeld(new ShootCommand(() -> shooter.calcRPM(ll.getTy()), shooter, feeder).alongWith(rumbleOnCommand))
					.whenInactive(new StopShooterCommand(shooter).alongWith(rumbleOffCommand));

		slowModeButton.whenActive(new RunCommand(() -> dt.setMaxOutput(Mecanum.SLOW_MODE_SPEED), dt).withTimeout(0.02))
					.whenInactive(new RunCommand(() -> dt.setMaxOutput(Mecanum.TELEOP_SPEED), dt).withTimeout(0.02));

		aimButton.whileActiveContinuous(new AimCommand(dt, ll))
					.whenInactive(driveCommand);
		//#endregion
		
		
		//#region Configure default commands
		dt.setDefaultCommand(driveCommand);

		shooter.setDefaultCommand(new StopShooterCommand(shooter).perpetually());
		feeder.setDefaultCommand(new RunCommand(feeder::off, feeder));
		intake.setDefaultCommand(new RunCommand(intake::off, intake));
		
		lightzController.setDefaultCommand(new RunCommand(
			() -> lightzController.updateMode(aimButton.get(), shootButton.get()), lightzController));
		//#endregion
	}

	/**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
	public Command getAutonomousCommand()
	{
		// return new InfRechAutoCommand(dt, intake, feeder, shooter, ll);
		return new SetWheelSpeedsTest(dt);
		// return new FollowTrajCommand(dt);
	}

	public void disabledInit()
	{
		dt.resetEncoders();
		dt.resetGyro();
		dt.resetPose();
		lightzController.disabled();
	}
}
