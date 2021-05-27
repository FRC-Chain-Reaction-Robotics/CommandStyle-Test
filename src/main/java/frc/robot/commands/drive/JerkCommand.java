package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.Mecanum;

public class JerkCommand extends PIDCommand
{
    Mecanum dt;

    public JerkCommand(double targetDistanceMeters, Mecanum dt)
    {
        super(new PIDController(7, 0, 0),
            dt::getDistance,
            targetDistanceMeters,
            output -> dt.drive(0, output, 0), 
            dt);

        this.dt = dt;

        getController().setTolerance(0.01);

        addRequirements(dt);
    }

    @Override
    public void initialize()
    {
        dt.resetEncoders();
    }

    @Override
    public boolean isFinished()
    {
        return getController().atSetpoint();
    }
    
}

// PIDController distPID = new PIDController(2.5, 0, 0);

//     public boolean driveToDistance(double distance)
//     {
//         double distanceOutput = distPID.calculate(m_leftFrontEncoder.getPosition(), distance);
//         // double turnOutput = turnPID.calculate(gyro.getAngle(), 0);
//         double turnOutput = 0;
        
//         md.driveCartesian(0, distanceOutput, turnOutput);

//         return distPID.atSetpoint();
// 	}