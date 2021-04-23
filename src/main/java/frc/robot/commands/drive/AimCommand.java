package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.*;

public class AimCommand extends PIDCommand
{
    Mecanum dt;

    public AimCommand(Mecanum dt, Limelight ll)
    {
        super(new PIDController(0.05, 0, 0),
            () -> -ll.getTx(),
            0,
            output -> dt.drive(0, 0, output),
            dt);

        this.dt = dt;
        
        getController().setTolerance(1);

        addRequirements(dt);
    }

    @Override
    public void initialize()
    {
        dt.resetSensors();
    }

    @Override
    public boolean isFinished()
    {
        return getController().atSetpoint();
    }
}
