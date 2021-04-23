package frc.robot.subsystems.trajectorytesting;

import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj.kinematics.MecanumDriveWheelSpeeds;

public class MecanumTraj extends MecanumOdom
{    
    public static final double MAX_WHEEL_VELOCITY_METERS_PER_SECOND = 15;

	protected CANPIDController lfPid = lb.getPIDController();
    protected CANPIDController rfPid = rf.getPIDController();
    protected CANPIDController rbPid = rb.getPIDController();
    protected CANPIDController lbPid = lb.getPIDController();

    public MecanumTraj()
    {
        super();
        //  TODO: set pid coeffs
    }
    public void setWheelSpeeds(MecanumDriveWheelSpeeds wheelSpeeds)
    {
        lfPid.setReference(wheelSpeeds.frontLeftMetersPerSecond, ControlType.kVelocity);
        rfPid.setReference(wheelSpeeds.frontRightMetersPerSecond, ControlType.kVelocity);
        rbPid.setReference(wheelSpeeds.rearRightMetersPerSecond, ControlType.kVelocity);
        lbPid.setReference(wheelSpeeds.rearLeftMetersPerSecond, ControlType.kVelocity);
    }
}
