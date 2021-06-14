// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.skills_challenges;

import frc.robot.commands.drive.*;
import frc.robot.commands.shoot.StartShooterCommand;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.*;

// I apologize for the super long comment explanations ahead of time 😅 - Josh


//	By inheriting from SequentialCommandGroup, all the Commands you put in the constructor will be executed in sequence by default.
//	ParallelCommandGroup, for example, will execute them all simultaneously. (There are more than these two group types)

//	HOWEVER, Command Groups are "recursively composable", meaning that you can have groups inside groups.
//	This is what makes Command-Based code so powerful, because you can quickly write very complex autons.
public class ExamplePath extends SequentialCommandGroup
{
	//	Every Command or Command Group needs to take in the subsystems it uses as arguments.
	//	For example, this command uses the drivetrain, so we pass in a Mecanum argument.
	//	This is called "dependency injection" in technical terms.
	public ExamplePath(Mecanum dt)
	{
		//	The addCommands method will add commands to the given Command Group.
		//	You can pass in as many commands as you want to as arguments.
		//	I think it's neat to separate it into multiple lines, rather than having the whole group in one line.
		addCommands
		(
			new DriveToDistanceCommand(1, dt),
			new TurnToAngleCommand(90, dt)
			// This will drive 1 meter forward and turn 90 degrees to the right
		);

		//	The constructor doesn't even need to do anything else! Just addCommands() and you're good
	}

	//	You should turn on the intake as demonstrated here in the Galactic Search paths to collect the balls along the way.
	public ExamplePath(Mecanum dt, Intake intake)
	{
		addCommands
		(
			//	https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html#runcommand
			//	RunCommand takes a method *reference* as a parameter. A method reference looks like objectName::methodName.
			//	In the link up there, they used a lambda instead of a method reference. You can look up what that is, but it's not important here.
			//	A RunCommand would be used for a simple command that doesn't need its own file.

			new RunCommand(intake::on, intake),	//	turns the intake on for the entire duration of this command group
			new DriveToDistanceCommand(99, dt),
			new TurnToAngleCommand(99, dt),
			new TurnToAngleCommand(99, dt),	//	pretend some random galactic search path happens here
			new TurnToAngleCommand(99, dt),
			new RunCommand(intake::off, intake)	//	turn it off at the end for safety
		);
	}

	//	Here's an example of one of those "recursively composed" command groups.
	//	You guys shouldn't need this, but I'm just showing you because it's cool.
	//	It's also the only good reason to use Command-Based ;)
	public ExamplePath(Mecanum dt, Intake intake, Shooter shooter)
	{
		addCommands
		(
			new RunCommand(intake::on, intake),
			new DriveToDistanceCommand(1, dt),
			new TurnToAngleCommand(1, dt).alongWith(new StartShooterCommand(Shooter.RPM_10FTLINE, shooter)),
			//	.alongWith(Command) returns a parallel group composed of the turn and shoot commands here, so they will run simultaneously
			
			//	this could be useful if, say, you wanted to aim and begin ramping up the slow ass shooter wheel simultaneously
			//	ie, new AimCommand(dt).alongWith(new StartShooter(Shooter.RPM_10FTLINE, shooter))
			//	You can also use parallel() instead, slightly shorter looking code
			parallel
			(
				new TurnToAngleCommand(1, dt), new StartShooterCommand(Shooter.RPM_10FTLINE, shooter)
			)
			
			//	there are more methods than .alongWith(), you can look at that page in line 46 and find out about 
			//	.raceWith(), .withTimeout(), etc
		);
	}
}
