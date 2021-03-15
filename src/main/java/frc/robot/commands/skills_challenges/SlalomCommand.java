// Copyright (cdt) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.skills_challenges;

import frc.robot.commands.drive.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.*;

//docs in ExamplePath, removed from here because can't see anything
public class SlalomCommand extends SequentialCommandGroup
{
	public SlalomCommand(Mecanum dt)
	{
		addCommands
		( //dunno if this works
                  new DriveToDistanceCommand(0.9, dt),
                  new TurnToAngleCommand(-45.0, dt),
                  new DriveToDistanceCommand(2.155, dt),
                  new TurnToAngleCommand(45.0, dt),
                  new DriveToDistanceCommand(3.302, dt),
                  new TurnToAngleCommand(45.0, dt),
                  new DriveToDistanceCommand(2.067, dt),
                  new TurnToAngleCommand(-95.0, dt),
                  new DriveToDistanceCommand(1.27, dt),
                  new TurnToAngleCommand(-80.0, dt),
                  new DriveToDistanceCommand(1.27, dt),
                  new TurnToAngleCommand(-100.0, dt),
                  new DriveToDistanceCommand(2.342, dt),
                  new TurnToAngleCommand(50.0, dt),
                  new DriveToDistanceCommand(3.048, dt),
                  new TurnToAngleCommand(42.0, dt),
                  new DriveToDistanceCommand(2.155, dt)
            );
	}
}
