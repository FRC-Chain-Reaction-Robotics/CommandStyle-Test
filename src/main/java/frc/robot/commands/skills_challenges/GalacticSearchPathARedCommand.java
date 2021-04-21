// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.skills_challenges;

import frc.robot.commands.drive.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.*;

//  name is kinda long but whatever lmao
//  you can copy this file to use as a template for others
public class GalacticSearchPathARedCommand extends SequentialCommandGroup
{
	public GalacticSearchPathARedCommand(Mecanum dt, Intake intake)
	{
		addCommands
		(
            new RunCommand(intake::on, intake),   //  you only need the intake business on the galactic search challenges
            new DriveToDistanceCommand(1.524, dt),
            new TurnToAngleCommand(26.565, dt),
            new DriveToDistanceCommand(1.704, dt),
            new TurnToAngleCommand(-98.13, dt),
            new DriveToDistanceCommand(2.410, dt),
            new TurnToAngleCommand(71.65, dt),
            new DriveToDistanceCommand(3.81, dt),
            new RunCommand(intake::off, intake)   //  just make sure to end with the intake off 
            //  (technically once the robot is disabled it will turn off automatically BUT good practice)
        );
        
        // Copied from Skillz.java for reference:
        /*
            auton = new Object[][]
            {
                {drive, 1.524},
                {turn, 26.565},
                {drive, 1.704},
                {turn, -98.13},
                {drive, 2.410},
                {turn, 71.65},
                {drive, 3.81}
            };
        */
    }
}
