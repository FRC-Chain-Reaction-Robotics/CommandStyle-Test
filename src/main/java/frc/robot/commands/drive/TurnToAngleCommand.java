package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.Mecanum;

public class TurnToAngleCommand extends PIDCommand
{
    Mecanum dt;

    public TurnToAngleCommand(double targetAngleDegrees, Mecanum dt)
    {
        super(new PIDController(0.05, 0, 0),
            dt::getAngle,
            targetAngleDegrees,
            output -> dt.drive(0, 0, output),
            dt);

        this.dt = dt;
        
        getController().setTolerance(1);

        addRequirements(dt);
    }

    @Override
    public void initialize()
    {
        dt.resetGyro();
    }

    @Override
    public boolean isFinished()
    {
        return getController().atSetpoint();
    }
}

    // 1.0/15 is for carpet
    // 1.0/20 is for sully room (for now) HMMM
    /// update: never drive in sully's room, it's too slippery

    // PIDController turnPID = new PIDController(0.066667, 0, 0);

    // from frcpdr
    // PIDController turnPID = new PIDController(0.12, 0.49, 0.0073);
    // from wpiman
    // PIDController turnPID = new PIDController(0.12, 0.8223, 0.01216);
    // mean of two
    // PIDController turnPID = new PIDController(0.12, 0.65, 0.01);
    // softer??
    // PIDController turnPID = new PIDController(0.1, 0.6, 0.008);
    // SOFTER II
    // PIDController turnPID = new PIDController(0.025, 0.5, 0.0075);

    // from frcpdr; Ziegler-Nichols PI only loop. slow but works
    // PIDController turnPID = new PIDController(0.09, 0.22, 0);

    // public boolean turnToAngle(double angle)
    // {
    //     md.driveCartesian(0, 0, turnPID.calculate(gyro.getAngle(), angle));

    //     return turnPID.atSetpoint();
    // }
