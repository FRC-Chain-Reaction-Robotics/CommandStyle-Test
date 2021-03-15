// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static com.revrobotics.CANSparkMax.IdleMode.kCoast;
import static com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless;
import static edu.wpi.first.wpilibj.SPI.Port.kOnboardCS0;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
public class Mecanum extends SubsystemBase
{
	CANSparkMax lf = new CANSparkMax(Constants.LF_MOTOR_ID, kBrushless);
	CANSparkMax lb = new CANSparkMax(Constants.LB_MOTOR_ID, kBrushless);
	CANSparkMax rf = new CANSparkMax(Constants.RF_MOTOR_ID, kBrushless);
	CANSparkMax rb = new CANSparkMax(Constants.RB_MOTOR_ID, kBrushless);
	
    CANEncoder m_leftFrontEncoder = lf.getEncoder();
    CANEncoder m_leftBackEncoder = lb.getEncoder();
    CANEncoder m_rightFrontEncoder = rf.getEncoder();
    CANEncoder m_rightBackEncoder = rb.getEncoder();
    
    Gyro gyro = new ADXRS450_Gyro(kOnboardCS0);

    PIDController aimPID = new PIDController(0.01, 0, 0);

    Limelight ll;

	MecanumDrive md = new MecanumDrive(lf, lb, rf, rb);

	/** Creates a new ExampleSubsystem. */
	public Mecanum(Limelight ll)
	{
		this.ll = ll;

		md.setMaxOutput(.44); // spitfire

        // md.setMaxOutput(.35); minispit

        gyro.reset();
        gyro.calibrate();
        
        m_rightFrontEncoder.setPositionConversionFactor(0.0454);
        m_rightBackEncoder.setPositionConversionFactor(0.0454);
        m_leftFrontEncoder.setPositionConversionFactor(0.0454);
        m_leftBackEncoder.setPositionConversionFactor(0.0454);

        /* For spitfire, not for new robot
        lf.setInverted(true);
		lb.setInverted(true);
		rb.setInverted(true);
        */

        // Minispit
        rf.setInverted(true);

		lb.setIdleMode(kCoast);
		rf.setIdleMode(kCoast);
		rb.setIdleMode(kCoast);
		lf.setIdleMode(kCoast);

		lf.burnFlash();
		lb.burnFlash();
		rf.burnFlash();
        rb.burnFlash();
	}

	public void drive(double xSpeed, double ySpeed, double zRotation)
	{
		md.driveCartesian(xSpeed, ySpeed, zRotation);
	}

	public void resetSensors()
    {
        gyro.reset();
        m_leftBackEncoder.setPosition(0);
        m_rightBackEncoder.setPosition(0);
        m_leftFrontEncoder.setPosition(0);
        m_rightFrontEncoder.setPosition(0);
	}
	
	@Override
	public void periodic()
	{
		// display gyro
		SmartDashboard.putData((Sendable) gyro);
		
		// display encoders
		SmartDashboard.putNumber("lf position", m_leftFrontEncoder.getPosition());
		SmartDashboard.putNumber("rf position", m_rightFrontEncoder.getPosition());
		SmartDashboard.putNumber("lb position", m_leftBackEncoder.getPosition());
		SmartDashboard.putNumber("rb position", m_rightBackEncoder.getPosition());
		
		SmartDashboard.putNumber("rb Velocity", m_rightBackEncoder.getVelocity());
		SmartDashboard.putNumber("elbeee Velocity", m_leftBackEncoder.getVelocity());
		SmartDashboard.putNumber("lf Velocity", m_leftFrontEncoder.getVelocity());
		SmartDashboard.putNumber("rf Velocity", m_rightFrontEncoder.getVelocity());
		
	}
		
	public void aimLL()
    {
        md.driveCartesian(0, 0, aimPID.calculate(ll.getTx(), 0));
	}
	

	public double getDistance()
	{
		return m_leftFrontEncoder.getPosition();
	}

	public double getAngle()
	{
		return gyro.getAngle();
	}
}