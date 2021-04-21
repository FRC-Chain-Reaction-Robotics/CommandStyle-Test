// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shoot;

import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class StopShooterCommand extends CommandBase
{
    Shooter shooter;

    public StopShooterCommand(Shooter shooter)
    {
        this.shooter = shooter;
		addRequirements(shooter);
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
    public void execute()
    {
        shooter.stop();
    }

	@Override
    public boolean isFinished()
    {
        return shooter.atSetpoint();
	}
}
