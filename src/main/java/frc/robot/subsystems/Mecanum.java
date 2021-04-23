// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static com.revrobotics.CANSparkMax.IdleMode.*;
import static com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless;
import static edu.wpi.first.wpilibj.SPI.Port.kOnboardCS0;

// import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.*;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
public class Mecanum extends SubsystemBase
{
	protected CANSparkMax lf = new CANSparkMax(Constants.LF_MOTOR_ID, kBrushless);
	protected CANSparkMax lb = new CANSparkMax(Constants.LB_MOTOR_ID, kBrushless);
	protected CANSparkMax rf = new CANSparkMax(Constants.RF_MOTOR_ID, kBrushless);
	protected CANSparkMax rb = new CANSparkMax(Constants.RB_MOTOR_ID, kBrushless);
	
    protected CANEncoder lfEncoder = lf.getEncoder();
    protected CANEncoder lbEncoder = lb.getEncoder();
    protected CANEncoder rfEncoder = rf.getEncoder();
    protected CANEncoder rbEncoder = rb.getEncoder();
    
	protected Gyro gyro = new ADXRS450_Gyro(kOnboardCS0);
	// AHRS gyro = new AHRS();

	MecanumDrive md = new MecanumDrive(lf, lb, rf, rb);

	public static final double SLOW_MODE_SPEED = 0.25;
	public static final double AUTON_SPEED = 0.3;
	public static final double TELEOP_SPEED = 0.5;

	/** Creates a new ExampleSubsystem. */
	public Mecanum()
	{
		md.setMaxOutput(TELEOP_SPEED); // spitfire

        gyro.reset();
        gyro.calibrate();
        
        rfEncoder.setPositionConversionFactor(0.0454);
        rbEncoder.setPositionConversionFactor(0.0454);
        lfEncoder.setPositionConversionFactor(0.0454);
		lbEncoder.setPositionConversionFactor(0.0454);
		
        rf.setInverted(true);

		lb.setIdleMode(kCoast);
		rf.setIdleMode(kCoast);
		rb.setIdleMode(kCoast);
		lf.setIdleMode(kCoast);

		lf.burnFlash();
		lb.burnFlash();
		rf.burnFlash();
		rb.burnFlash();

		//#region shuffleboard
		SmartDashboard.putData((Sendable) gyro);

		SmartDashboard.putNumber("lf position", lfEncoder.getPosition());
		SmartDashboard.putNumber("rf position", rfEncoder.getPosition());
		SmartDashboard.putNumber("lb position", lbEncoder.getPosition());
		SmartDashboard.putNumber("rb position", rbEncoder.getPosition());

		SmartDashboard.putNumber("rb Velocity", rbEncoder.getVelocity());
		SmartDashboard.putNumber("lb Velocity", lbEncoder.getVelocity());
		SmartDashboard.putNumber("lf Velocity", lfEncoder.getVelocity());
		SmartDashboard.putNumber("rf Velocity", rfEncoder.getVelocity());
		//#endregion
	}

	/**
   * Drive method for Mecanum platform.
   *
   * <p>Angles are measured clockwise from the positive X axis. The robot's speed is independent
   * from its angle or rotation rate.
   *
   * @param ySpeed The robot's speed along the Y axis [-1.0..1.0]. Right is positive.
   * @param xSpeed The robot's speed along the X axis [-1.0..1.0]. Forward is positive.
   * @param zRotation The robot's rotation rate around the Z axis [-1.0..1.0]. Clockwise is positive.
   */
	public void drive(double ySpeed, double xSpeed, double zRotation)
	{
		md.driveCartesian(ySpeed, xSpeed, zRotation);
	}

	public void resetSensors()
    {
        gyro.reset();
        lbEncoder.setPosition(0);
        rbEncoder.setPosition(0);
        lfEncoder.setPosition(0);
        rfEncoder.setPosition(0);
	}

	public double getDistance()
	{
		return lfEncoder.getPosition();
	}

	public double getAngle()
	{
		return gyro.getAngle();
	}

	public void setMaxOutput(double maxOutput)
	{
		md.setMaxOutput(maxOutput);
	}
}