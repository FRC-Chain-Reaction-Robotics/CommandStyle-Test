package frc.robot.subsystems.trajectorytesting;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj.kinematics.MecanumDriveWheelSpeeds;

public class MecanumTraj extends MecanumOdom
{    
    public static final double MAX_WHEEL_VELOCITY_METERS_PER_SECOND = 15;

	protected CANPIDController lfPid = lf.getPIDController();
    protected CANPIDController rfPid = rf.getPIDController();
    protected CANPIDController rbPid = rb.getPIDController();
    protected CANPIDController lbPid = lb.getPIDController();

    public MecanumTraj()
    {
        super();
        
        //  TODO: tune
        setPID(lf, 0.35, 0, 0);
        setPID(rf, 0.35, 0, 0);
        setPID(rb, 0.35, 0, 0);
        setPID(lb, 0.35, 0, 0);
    }

    private void setPID(CANSparkMax motorController, double P, double I, double D)
    {
        motorController.getPIDController().setP(P);
        motorController.getPIDController().setI(I);
        motorController.getPIDController().setD(D);
        
        motorController.burnFlash();
    }

    public void setWheelSpeeds(MecanumDriveWheelSpeeds wheelSpeeds)
    {
        lfPid.setReference(wheelSpeeds.frontLeftMetersPerSecond, ControlType.kVelocity);
        lbPid.setReference(wheelSpeeds.rearLeftMetersPerSecond, ControlType.kVelocity);
        rfPid.setReference(-wheelSpeeds.frontRightMetersPerSecond, ControlType.kVelocity);
        rbPid.setReference(-wheelSpeeds.rearRightMetersPerSecond, ControlType.kVelocity);;
    }
}
