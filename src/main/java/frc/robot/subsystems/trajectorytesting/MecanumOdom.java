package frc.robot.subsystems.trajectorytesting;

import edu.wpi.first.wpilibj.geometry.*;
import edu.wpi.first.wpilibj.kinematics.*;
import frc.robot.subsystems.Mecanum;

public class MecanumOdom extends Mecanum
{
    public MecanumDriveKinematics kinematics = new MecanumDriveKinematics
    (
        new Translation2d(-12, 9.125),
        new Translation2d(12, 9.125),
        new Translation2d(-12, -9.125),
        new Translation2d(12, -9.125)
    );
    
    MecanumDriveOdometry odom;
    
    public MecanumOdom()
    {
        super();
        
        rfEncoder.setVelocityConversionFactor(0.0454);
        rbEncoder.setVelocityConversionFactor(0.0454);
        lfEncoder.setVelocityConversionFactor(0.0454);
		lbEncoder.setVelocityConversionFactor(0.0454);
		
        odom = new MecanumDriveOdometry(kinematics, new Rotation2d(-gyro.getAngle()));
    }

    public void periodic()
    {
        super.periodic();
        
        var wheelSpeeds = new MecanumDriveWheelSpeeds
        (
            lfEncoder.getVelocity(), rfEncoder.getVelocity(), 
            lbEncoder.getVelocity(), rbEncoder.getVelocity()
        );
        
        odom.update(new Rotation2d(-gyro.getAngle()), wheelSpeeds);
    }

    public Pose2d getPose()
    {
        return odom.getPoseMeters();
    }
}
