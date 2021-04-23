package frc.robot.subsystems.Lights;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.subsystems.*;

public class Lightz2Controller extends SubsystemBase
{
	Lightz2 lightz = new Lightz2();

	public enum RobotStatus { kRampingUpShooterOrAiming, kShooting, kIdle };
	RobotStatus status = RobotStatus.kIdle;

	Shooter shooter; Feeder feeder; Mecanum dt; Limelight ll;

	public Lightz2Controller(Shooter shooter, Feeder feeder, Mecanum dt, Limelight ll)
	{
		this.shooter = shooter;
		this.feeder = feeder;
		this.dt = dt;
		this.ll = ll;

		register();
	}

	@Override
	public void periodic()
	{
		switch (status)
		{
			case kRampingUpShooterOrAiming:
				lightz.mode = Lightz2.Mode.kRed;
				break;
			case kShooting:
				lightz.mode = Lightz2.Mode.kGreen;
				break;
			default:
			case kIdle:
				lightz.mode = Lightz2.Mode.kBlue;
				break;
		}
	}

	/**
	 * Updates robot status to control LEDs.
	 * @param tryingToShoot the shooter button
	 * @param tryingToAim the aiming button
	 */
	public void updateMode(boolean tryingToShoot, boolean tryingToAim)
	{
		boolean isRamping = !shooter.atSetpoint(Shooter.RPM_10FTLINE)
						&& !shooter.atSetpoint(Shooter.RPM_FAR);
		
		boolean isAiming =  ! (ll.getTv() && Math.abs(ll.getTx()) < 0.2);

		//	I know these statements are redundant and maybe wrong but it's meant to be read and debugged
		if ((tryingToShoot && isRamping) || (tryingToAim && isAiming))
			this.status = RobotStatus.kRampingUpShooterOrAiming;
		else if (tryingToShoot && !isRamping && !isAiming)
			this.status = RobotStatus.kShooting;    //  Lets drivers know they can feed
		else
			this.status = RobotStatus.kIdle;
	}
}
