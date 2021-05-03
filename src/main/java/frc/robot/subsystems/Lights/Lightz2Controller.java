package frc.robot.subsystems.Lights;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.subsystems.*;

public class Lightz2Controller extends SubsystemBase
{
	Lightz2 lightz = new Lightz2();

	Shooter shooter; Feeder feeder; Mecanum dt; Limelight ll;

	public Lightz2Controller(Shooter shooter, Feeder feeder, Mecanum dt, Limelight ll)
	{
		this.shooter = shooter;
		this.feeder = feeder;
		this.dt = dt;
		this.ll = ll;

		register();
	}

	/**
	 * Updates robot status to control LEDs.
	 * @param tryingToShoot the shooter button
	 * @param tryingToAim the aiming button
	 */
	public void updateMode(boolean tryingToAim, boolean tryingToShoot)
	{		
		boolean isAimed =  Math.abs(ll.getTx()) < 1;
		boolean isReadyToShoot = shooter.atSetpoint(Shooter.RPM_10FTLINE);

		if (tryingToAim && isAimed && tryingToShoot && isReadyToShoot)
			lightz.mode = Lightz2.Mode.kGreen;
		else if ((tryingToAim && !isAimed) || (tryingToShoot && !isReadyToShoot))
			lightz.mode = Lightz2.Mode.kRed;
		else
			lightz.mode = Lightz2.Mode.kBlue;

	}
}
