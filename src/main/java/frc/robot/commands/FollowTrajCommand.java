package frc.robot.commands;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.controller.*;
import edu.wpi.first.wpilibj.geometry.*;
import edu.wpi.first.wpilibj.trajectory.*;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.MecanumControllerCommand;
import frc.robot.subsystems.trajectorytesting.MecanumTraj;

public class FollowTrajCommand extends MecanumControllerCommand
{
    MecanumTraj dt;

    static PIDController xController = new PIDController(2.75, 0, 0);
    static PIDController yController = new PIDController(2.75, 0, 0);
    static ProfiledPIDController thetaController = new ProfiledPIDController(0.05, 0, 0, new TrapezoidProfile.Constraints(Units.feetToMeters(12), Units.feetToMeters(12)));

    public FollowTrajCommand(MecanumTraj dt)
    {
        super(getPathweaver(), dt::getPose, dt.kinematics, 
                xController, yController, thetaController,
                MecanumTraj.MAX_WHEEL_VELOCITY_METERS_PER_SECOND,
                dt::setWheelSpeeds, dt);
    }

    private static Trajectory generateTrajectory()
    {
        // // 2018 cross scale auto waypoints.
        // var sideStart = new Pose2d(Units.feetToMeters(1.54), Units.feetToMeters(23.23),
        //     Rotation2d.fromDegrees(-180));
        // var crossScale = new Pose2d(Units.feetToMeters(23.7), Units.feetToMeters(6.8),
        //     Rotation2d.fromDegrees(-160));

        // var interiorWaypoints = new ArrayList<Translation2d>();
        // interiorWaypoints.add(new Translation2d(Units.feetToMeters(14.54), Units.feetToMeters(23.23)));
        // interiorWaypoints.add(new Translation2d(Units.feetToMeters(21.04), Units.feetToMeters(18.23)));

        // TrajectoryConfig config = new TrajectoryConfig(Units.feetToMeters(12), Units.feetToMeters(12));
        // config.setReversed(true);

        // var trajectory = TrajectoryGenerator.generateTrajectory(
        //     sideStart,
        //     interiorWaypoints,
        //     crossScale,
        //     config);
        
        ArrayList<Pose2d> waypoints = new ArrayList<Pose2d>();
        waypoints.add(new Pose2d(0, 0, new Rotation2d()));
        waypoints.add(new Pose2d(1, 0, new Rotation2d()));

        TrajectoryConfig config = new TrajectoryConfig(Units.feetToMeters(12), Units.feetToMeters(12));
        config.setReversed(true);
        
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(waypoints, config);

        return trajectory;
    }

    private static Trajectory getPathweaver()
    {
        String trajectoryJSON = "paths/TestMovement.wpilib.json";
        Trajectory trajectory = new Trajectory();

        try {
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
            trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
        } catch (IOException ex) {
            DriverStation.reportError("Unable to open trajectory: " + trajectoryJSON, ex.getStackTrace());
        }
        
        return trajectory;
    }
}