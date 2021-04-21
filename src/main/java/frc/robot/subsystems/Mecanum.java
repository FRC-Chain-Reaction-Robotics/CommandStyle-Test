// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static com.revrobotics.CANSparkMax.IdleMode.*;
import static com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless;
import static edu.wpi.first.wpilibj.SPI.Port.kOnboardCS0;

// import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.*;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.shuffleboard.*;
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
	// AHRS gyro = new AHRS();

	MecanumDrive md = new MecanumDrive(lf, lb, rf, rb);

	public static final double SLOW_MODE_SPEED = 0.15;
	public static final double AUTON_SPEED = 0.5;
	public static final double TELEOP_SPEED = 0.5;

	/** Creates a new ExampleSubsystem. */
	public Mecanum()
	{
		md.setMaxOutput(TELEOP_SPEED); // spitfire

        gyro.reset();
        gyro.calibrate();
        
        m_rightFrontEncoder.setPositionConversionFactor(0.0454);
        m_rightBackEncoder.setPositionConversionFactor(0.0454);
        m_leftFrontEncoder.setPositionConversionFactor(0.0454);
        m_leftBackEncoder.setPositionConversionFactor(0.0454);

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
		var drivetrainTab = Shuffleboard.getTab("Drivetrain");

		// display gyro
		drivetrainTab.add((Sendable) gyro);
		
		// display encoders
		var encoderGrid = drivetrainTab.getLayout("Encoders", BuiltInLayouts.kGrid)
			.withSize(2, 4);

		encoderGrid.addNumber("lf position", m_leftFrontEncoder::getPosition);
		encoderGrid.addNumber("rf position", m_rightFrontEncoder::getPosition);
		encoderGrid.addNumber("lb position", m_leftBackEncoder::getPosition);
		encoderGrid.addNumber("rb position", m_rightBackEncoder::getPosition);

		encoderGrid.addNumber("rb Velocity", m_rightBackEncoder::getVelocity);
		encoderGrid.addNumber("lb Velocity", m_leftBackEncoder::getVelocity);
		encoderGrid.addNumber("lf Velocity", m_leftFrontEncoder::getVelocity);
		encoderGrid.addNumber("rf Velocity", m_rightFrontEncoder::getVelocity);
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
   * @param zRotation The robot's rotation rate around the Z axis [-1.0..1.0]. Clockwise is
   *     positive.
   */
	public void drive(double ySpeed, double xSpeed, double zRotation)
	{
		md.driveCartesian(ySpeed, xSpeed, zRotation);
	}

	public void resetSensors()
    {
        gyro.reset();
        m_leftBackEncoder.setPosition(0);
        m_rightBackEncoder.setPosition(0);
        m_leftFrontEncoder.setPosition(0);
        m_rightFrontEncoder.setPosition(0);
	}

	public double getDistance()
	{
		return m_leftFrontEncoder.getPosition();
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