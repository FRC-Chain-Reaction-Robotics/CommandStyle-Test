package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;

public class AimCommand extends CommandBase
{
    Mecanum dt; Limelight ll;
    PIDController txController = new PIDController(0.05, 0, 0);
    PIDController tyController = new PIDController(0.2, 0, 0);

    public static final double ty10ft = 17;

    public AimCommand(Mecanum dt, Limelight ll)
    {
        this.dt = dt;
        this.ll = ll;
        txController.setTolerance(1);
        tyController.setTolerance(1);
        addRequirements(dt);
    }

    @Override
    public void initialize()
    {
        dt.resetEncoders();
    }

    @Override
    public void execute()
    {
        dt.drive(0, tyController.calculate(ll.getTy(), 20), txController.calculate(-ll.getTx(), 0));
    }

    @Override
    public boolean isFinished()
    {
        return txController.atSetpoint();
    }
}
