package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.*;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.MecanumControllerCommand;
import frc.robot.subsystems.trajectorytesting.MecanumTraj;

public class FollowTrajCommand extends MecanumControllerCommand
{
    MecanumTraj dt;
    
    static PIDController xController = null;
    static PIDController yController = null;
    static ProfiledPIDController thetaController = null;

    public FollowTrajCommand(Trajectory trajectory, MecanumTraj dt)
    {
        super(trajectory, dt::getPose, dt.kinematics, 
                xController, yController, thetaController,
                MecanumTraj.MAX_WHEEL_VELOCITY_METERS_PER_SECOND,
                dt::setWheelSpeeds, dt);
    }
}