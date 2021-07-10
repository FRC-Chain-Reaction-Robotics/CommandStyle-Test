// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.commands.drive.*;
import frc.robot.commands.shoot.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.*;


public class SystemTestCommand extends SequentialCommandGroup
{
	/**
	 * Infinite Recharge auton for Texas Cup :)
	 * @param dt
	 * @param intake
	 * @param feeder
	 * @param shooter
	 */
	public SystemTestCommand (Mecanum dt, Intake intake, Feeder feeder, Shooter shooter)
	{
		addRequirements(dt, intake, feeder, shooter);
		
		shooter.setDefaultCommand(new StopShooterCommand(shooter).perpetually());
		feeder.setDefaultCommand(new RunCommand(feeder::off, feeder));
		intake.setDefaultCommand(new RunCommand(intake::off, intake));

		addCommands
		(
            new PrintCommand("Commands have started"),
            // new RunCommand(intake::on, intake).withTimeout(5),
            // new RunCommand(intake::off, intake),
            new RunCommand(feeder::on, feeder).withTimeout(5),
            new RunCommand(feeder::off, feeder),
            new StartShooterCommand(300, shooter),
            new WaitCommand(3),
            new StopShooterCommand(shooter),
            new DriveToDistanceCommand(2, dt),
            new PrintCommand("Finished!")
		);
	}
}
