// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.skills_challenges;

import frc.robot.commands.drive.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.*;

//  name is kinda long but whatever lmao
//  you can copy this file to use as a template for others
public class GalacticSearchPathABlueCommand extends SequentialCommandGroup
{
	public GalacticSearchPathABlueCommand(Mecanum dt, Intake intake)
	{
		addCommands
		(
            new RunCommand(intake::intakeOn, intake),   //  you only need the intake business on the galactic search challenges
            new DriveToDistanceCommand(4.72, dt),
            new TurnToAngleCommand(-72.565, dt),
            new DriveToDistanceCommand(2.41, dt),
            new TurnToAngleCommand(81.87, dt),
            new DriveToDistanceCommand(1.7, dt),
            new TurnToAngleCommand(-9.31, dt),
            new DriveToDistanceCommand(1.524, dt),
            new RunCommand(intake::intakeOff, intake)   //  just make sure to end with the intake off 
            //  (technically once the robot is disabled it will turn off automatically BUT good practice)
        );
        
        // Copied from Skillz.java for reference:
        /*
            auton = new Object[][]
            {
                {drive, 4.72},
                {turn, -72.565},
                {drive, 2.41},
                {turn, 81.87},
                {drive, 1.7},
                {turn, -9.31},
                {drive, 1.524}
            };
        */
    }
}
