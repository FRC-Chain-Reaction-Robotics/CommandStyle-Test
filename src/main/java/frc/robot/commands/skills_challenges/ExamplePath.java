// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.skills_challenges;

import frc.robot.commands.drive.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ExamplePath extends SequentialCommandGroup
{
	public ExamplePath(Mecanum dt)
	{
		addCommands
		(
			new DriveToDistanceCommand(1, dt),
			new TurnToAngleCommand(90, dt)
		);
	}
}
