
package frc.robot.commands.skills_challenges;

import frc.robot.commands.drive.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.*;

//  name is kinda long but whatever lmao 
//  you can copy this file to use as a template for others
public class GalacticSearchPathBBlueCommand extends SequentialCommandGroup
{
	public GalacticSearchPathBBlueCommand(Mecanum dt, Intake intake)
	{
		addCommands
		(
            new RunCommand(intake::intakeOn, intake),   //  you only need the intake business on the galactic search challenges
            new DriveToDistanceCommand(3.381, dt),
            new TurnToAngleCommand(-45, dt),
            new DriveToDistanceCommand(2.155, dt),
            new TurnToAngleCommand(90, dt),
            new DriveToDistanceCommand(2.155, dt),
            new TurnToAngleCommand(-45, dt),
            new DriveToDistanceCommand(0.762, dt),
            new RunCommand(intake::intakeOff, intake)   //  just make sure to end with the intake off 
            //  (technically once the robot is disabled it will turn off automatically BUT good practice)
        );
        
        // Copied from Skillz.java for reference:
        /*
            auton = new Object[][]
            {
            {drive, 3.381},
            {turn, -45.0},
            {drive, 2.155},
            {turn, 90.0},
            {drive, 2.155},
            {turn, -45.0},
            {drive, 0.762}
            };
        */
    }
}    

