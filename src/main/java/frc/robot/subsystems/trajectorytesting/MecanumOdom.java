package frc.robot.subsystems.trajectorytesting;

import edu.wpi.first.wpilibj.geometry.*;
import edu.wpi.first.wpilibj.kinematics.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.Mecanum;

public class MecanumOdom extends Mecanum
{
    public MecanumDriveKinematics kinematics = new MecanumDriveKinematics
    (
        new Translation2d(-12, 9.125), new Translation2d(12, 9.125),
        new Translation2d(-12, -9.125), new Translation2d(12, -9.125)
    );
    
    MecanumDriveOdometry odom;
    
    public MecanumOdom()
    {
        super();
        
        rfEncoder.setVelocityConversionFactor(1/1);  //  RPM to m/s
        rbEncoder.setVelocityConversionFactor(1/1);
        lfEncoder.setVelocityConversionFactor(1/1);
		lbEncoder.setVelocityConversionFactor(1/1);   //  1330);  //  TODO: tune
        
        rf.burnFlash();
        rb.burnFlash();
        lf.burnFlash();
        lb.burnFlash();

        odom = new MecanumDriveOdometry(kinematics, getGyroAsRotation2d());
    }

    public void periodic()
    {
        super.periodic();
        
        var wheelSpeeds = new MecanumDriveWheelSpeeds
        (
            lfEncoder.getVelocity(), -rfEncoder.getVelocity(), 
            lbEncoder.getVelocity(), -rbEncoder.getVelocity()
        );
        
        odom.update(getGyroAsRotation2d(), wheelSpeeds);

        printOdom();
    }

    private Rotation2d getGyroAsRotation2d()
    {
        return new Rotation2d(Math.toRadians(-gyro.getAngle()));
    }

    public Pose2d getPose()
    {
        return odom.getPoseMeters();
    }

    public void resetPose()
    {
        odom.resetPosition(new Pose2d(), new Rotation2d(0));
    }

    public void printOdom()
	{
		var pose = getPose();

		SmartDashboard.putNumber("x", pose.getX());
		SmartDashboard.putNumber("y", pose.getY());
		SmartDashboard.putNumber("theta", pose.getRotation().getDegrees());
	}
}
