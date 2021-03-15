package frc.robot.commands.skills_challenges;

import frc.robot.commands.drive.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.*;

public class BouncePathCommand extends SequentialCommandGroup
{
    public BouncePathCommand(Mecanum dt)
	{
		addCommands
		(
            new DriveToDistanceCommand(1.704, dt),
            new TurnToAngleCommand(-63.0, dt),
            new DriveToDistanceCommand(2.41, dt),
            new TurnToAngleCommand(136.0, dt),
            new DriveToDistanceCommand(1.078, dt),
            new TurnToAngleCommand(-32, dt),
            new DriveToDistanceCommand(2.41, dt),
            new TurnToAngleCommand(-117, dt),
            new DriveToDistanceCommand(2.41, dt),
            new TurnToAngleCommand(152, dt),
            new DriveToDistanceCommand(0.762, dt),
            new TurnToAngleCommand(-76, dt),
            new DriveToDistanceCommand(1.704, dt),
            new TurnToAngleCommand(136, dt),
            //  (technically once the robot is disabled it will turn off automatically BUT good practice)
        );
    
/**
 *          {drive, 1.704},
            {turn, -63.0},
            {drive, 2.41},
            {turn, 136.0},
            {drive, 1.078},
            {turn, -32.0},
            {drive, 2.41},
            {turn, -117.0},
            {drive, 2.41},
            {turn, 152.0},
            {drive, 0.762},
            {turn, -76.0},
            {drive, 2.41},
            {turn, -76.0},
            {drive, 1.704},
            {turn, 136.0}
 */
}