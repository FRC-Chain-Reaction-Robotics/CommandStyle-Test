// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.commands.shoot.*;
import frc.robot.commands.drive.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AutoCommand extends SequentialCommandGroup
{
	public AutoCommand(Mecanum dt, Intake intake, Feeder feeder, Shooter shooter)
	{
		addCommands
		(
			new ShootCommand(shooter, feeder)
		);
	}
}
