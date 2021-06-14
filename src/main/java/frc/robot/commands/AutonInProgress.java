// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.commands.drive.*;
import frc.robot.commands.shoot.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.*;


public class AutonInProgress extends SequentialCommandGroup
{
	/**
	 * Infinite Recharge auton for Texas Cup :)
	 * @param dt
	 * @param intake
	 * @param feeder
	 * @param shooter
	 */
	public AutonInProgress(Mecanum dt, Intake intake, Feeder feeder, Shooter shooter, Limelight ll)
	{
		addRequirements(dt, intake, feeder, shooter);
		
		shooter.setDefaultCommand(new StopShooterCommand(shooter).perpetually());
		feeder.setDefaultCommand(new RunCommand(feeder::off, feeder));
		intake.setDefaultCommand(new RunCommand(intake::off, intake));

		var dropIntake = new SequentialCommandGroup(
			//new InstantCommand(intake::reverse, intake),
			new JerkCommand(0.5, dt),
			new JerkCommand(-0.5, dt));
		
		var shootBalls = new ParallelCommandGroup(
			new AimCommand(dt, ll),
			new StartShooterCommand(Shooter.RPM_10FTLINE, shooter));

		addCommands
		(
			new InstantCommand(() -> dt.setMaxOutput(Mecanum.AUTON_SPEED), dt),
			dropIntake.andThen(shootBalls).andThen(new RunCommand(feeder::on, feeder).withTimeout(1))
			// new TurnToAngleCommand(130.0, dt),
			// new DriveToDistanceCommand(1.652, dt),
			// new TurnToAngleCommand(50, dt),
			// new DriveToDistanceCommand(3.799, dt),
			// new RunCommand(intake::on, intake).withTimeout(4)
			// .alongWith(new RunCommand(feeder::on, feeder).withTimeout(2)
			// 	.andThen(new InstantCommand(feeder::off, feeder))),
			// //	Drive back to goal
			// new TurnToAngleCommand(140.0, dt),
			// //	Start winding up shooter early
			// new DriveToDistanceCommand(2.277, dt).alongWith(new StartShooterCommand(() -> Shooter.RPM_18FT, shooter)),
			// new TurnToAngleCommand(40.0, dt),
			// //	Shoot the balls we've picked up
			// new AimCommand(dt, ll),
			// new RunCommand(feeder::on, feeder).withTimeout(4),
            // new InstantCommand(feeder::off, feeder),
            // new StopShooterCommand(shooter)
		);
	}
}


		//	Convert this to command based pls
		//	And insert RunCommand(intake or feeder business) and ShootCommands wherever applicable

		// public void selectInfRechPath()
		// {
		// 	auton = new Object[][]
		// 	{
			// shoot
		// 		{turn, 130.0},
		// 		{drive, 1.652},
		// 		{turn, 50.0},
		// 		{drive, 3.799},
		//// intake
		// 		{turn, 140.0},
		// 		{drive, 2.277},
		// 		{turn, 40.0}
		//// shoot
		// 	};
		//
	
