// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;

import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj2.command.button.*;
import static edu.wpi.first.wpilibj.XboxController.Button.*;

import edu.wpi.first.cameraserver.CameraServer;
import frc.robot.commands.*;
import frc.robot.commands.drive.*;
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
	
	Saitek flightStick = new Saitek(0);
	XboxController operatorController = new XboxController(1);
	XboxController driverController = new XboxController(2);
	
	/** The container for the robot. Contains subsystems, OI devices, and commands. */
	public RobotContainer()
	{
		// CameraServer.getInstance().startAutomaticCapture();
		
		dt.register();
		//#region some short commands
		var rumbleOnCommand = new RunCommand(() -> operatorController.setRumble(RumbleType.kLeftRumble, 1.0))
			.alongWith(new RunCommand(() -> operatorController.setRumble(RumbleType.kRightRumble, 1.0)));
			
		var rumbleOffCommand = new RunCommand(() -> operatorController.setRumble(RumbleType.kLeftRumble, 0.0))
			.alongWith(new RunCommand(() -> operatorController.setRumble(RumbleType.kRightRumble, 0.0)));

		var driveCommand = new RunCommand(() -> dt.drive(
				flightStick.getX() + driverController.getX(Hand.kLeft),
				-flightStick.getY() - driverController.getY(Hand.kLeft),
				flightStick.getZRotation() + driverController.getX(Hand.kRight),
				Mecanum.TELEOP_SPEED),
			dt);
		//#endregion

		//#region controls
		var feederUpButton = new POVButton(operatorController, 0).or(new POVButton(flightStick, 0));
		var feederDownButton = new POVButton(operatorController, 180).or(new POVButton(flightStick, 180));
		var frickFeederDownButton = new JoystickButton(operatorController, kB.value);
		var frickFeederUpButton = new JoystickButton(operatorController, kA.value);

		var intakeButton = new JoystickButton(operatorController, kBumperRight.value);
		var intakeReverseButton = new JoystickButton(operatorController, kBumperLeft.value);

		var shootButton = new JoystickButton(operatorController, kX.value);
		var shootReverseButton = new JoystickButton(operatorController, kY.value);
		
		var slowModeButton = new JoystickButton(flightStick, Saitek.SButtons.kTrigger.ordinal());
		var aimButton = new JoystickButton(flightStick, Saitek.SButtons.kUL.ordinal()).or(new JoystickButton(driverController, kBumperLeft.value));

		//#endregion


		//#region bindings
		feederUpButton.whileActiveContinuous(new RunCommand(feeder::on, feeder))
			.or(feederDownButton.whileActiveContinuous(new RunCommand(feeder::reverse, feeder))
			.or(frickFeederDownButton.whileActiveContinuous(new RunCommand(feeder::frick, feeder))
			.or(frickFeederUpButton.whileActiveContinuous(new RunCommand(feeder::frickUp, feeder))))
			.whenInactive(new RunCommand(feeder::off, feeder)));

		intakeButton.whileHeld(new RunCommand(intake::on, intake))
			.or(intakeReverseButton.whileHeld(new RunCommand(intake::reverse, intake)))
			.whenInactive(new RunCommand(intake::off, intake));

		shootButton.whileHeld(new ShootCommand(() -> shooter.calcRPM(ll.getTy()), shooter).alongWith(rumbleOnCommand))
		.or(shootReverseButton.whenActive(new RunCommand(shooter::reverse, shooter)))
					.whenInactive(new StopShooterCommand(shooter).alongWith(rumbleOffCommand));

		slowModeButton.whenPressed(new InstantCommand(() -> dt.setMaxOutput(Mecanum.SLOW_MODE_SPEED), dt))
					.whenReleased(new InstantCommand(() -> dt.setMaxOutput(Mecanum.TELEOP_SPEED), dt));

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
		return new InfRechAutoCommand(dt, intake, feeder, shooter, ll);
		// return new SetWheelSpeedsTest(dt);
		// return new FollowTrajCommand(dt);
	}

	public void disabledInit()
	{
		dt.resetEncoders();
		dt.resetGyro();
		lightzController.disabled();
	}
}
