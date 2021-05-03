// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shoot;

import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ShootCommand extends SequentialCommandGroup
{
    Shooter shooter;
    Feeder feeder;

    public ShootCommand(double rpm, Shooter shooter, Feeder feeder)
    {
        this.shooter = shooter;
        this.feeder = feeder;
        addRequirements(shooter, feeder);

        addCommands
        (
			new StartShooterCommand(rpm, shooter),
            new RunCommand(feeder::on, feeder).withTimeout(4),
            new RunCommand(feeder::off, feeder).withTimeout(0.02),
            new StopShooterCommand(shooter)
        );
    }
}