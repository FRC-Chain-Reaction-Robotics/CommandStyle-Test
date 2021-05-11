// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shoot;

import frc.robot.subsystems.*;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.*;

public class ShootCommand extends SequentialCommandGroup
{
    Shooter shooter;
    Feeder feeder;

    public ShootCommand(DoubleSupplier rpmGetter, Shooter shooter, Feeder feeder)
    {
        this.shooter = shooter;
        this.feeder = feeder;
        addRequirements(shooter, feeder);

        addCommands
        (
			new StartShooterCommand(rpmGetter, shooter),
            new RunCommand(feeder::on, feeder).withTimeout(10),
            new InstantCommand(feeder::off, feeder),
            new StopShooterCommand(shooter)
        );
    }
}