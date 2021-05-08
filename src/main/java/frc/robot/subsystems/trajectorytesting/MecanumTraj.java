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
        double kP = 0.0/16, kI = 0.0/16, kD = 0.0/16;
        double kFF = 3.0/32;

        setAllPID(kP, kI, kD, kFF);
    }

    private void setAllPID(double P, double I, double D, double F)
    {
        setPID(lf, P, I, D, F);
        setPID(rf, P, I, D, F);
        setPID(rb, P, I, D, F);
        setPID(lb, P, I, D, F);
    }
    private void setPID(CANSparkMax motorController, double P, double I, double D, double F)
    {
        motorController.getPIDController().setP(P);
        motorController.getPIDController().setI(I);
        motorController.getPIDController().setD(D);
        motorController.getPIDController().setFF(F);
        
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
