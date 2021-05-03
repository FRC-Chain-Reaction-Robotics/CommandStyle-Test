package frc.robot.commands;

import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.trajectorytesting.MecanumTraj;

public class SetWheelSpeedsTest extends CommandBase
{
    MecanumTraj dt;

    public SetWheelSpeedsTest(MecanumTraj dt)
    {
        this.dt = dt;
    }
    
    @Override
    public void execute()
    {
        var chassisSpeeds = new ChassisSpeeds(2, 0, 0);
        var wheelSpeeds = dt.kinematics.toWheelSpeeds(chassisSpeeds);
        dt.setWheelSpeeds(wheelSpeeds);
    }
}