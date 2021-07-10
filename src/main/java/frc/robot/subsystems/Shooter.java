// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants;
import frc.robot.commands.shoot.StopShooterCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase
{
	CANSparkMax rightShooter;
	CANSparkMax leftShooter;
	CANPIDController shooterPID;
	CANEncoder leftEncoder;


	double kP, kI, kD;
	public static final double RPM_10FTLINE = 1330; 
	public static final double RPM_18FT = 1650;

	public static final double ERROR_TOLERANCE = 50;
	
	double setpoint;

  	/** Creates a new Shooter. */
	public Shooter() 
	{	
		rightShooter = new CANSparkMax(Constants.RIGHT_SHOOTER_MOTOR_ID, MotorType.kBrushless);
		leftShooter = new CANSparkMax(Constants.LEFT_SHOOTER_MOTOR_ID, MotorType.kBrushless);  //  left and right
		shooterPID = leftShooter.getPIDController();
		leftEncoder = leftShooter.getEncoder();

		rightShooter.follow(leftShooter);
		leftShooter.restoreFactoryDefaults();		
		leftShooter.setInverted(true);
		
		leftShooter.setSmartCurrentLimit(40);
		
		shooterPID.setP(0.001);
		shooterPID.setI(0.0);
		shooterPID.setD(0);
		shooterPID.setFF(0.00015);
		// shooterPID.setFF(																																											0);
		shooterPID.setIZone(200);

		leftShooter.burnFlash();
		
		setDefaultCommand(new StopShooterCommand(this).perpetually());
		register();
	}

	@Override
	public void periodic() 
	{	
		SmartDashboard.putNumber("Shooter Velocity", leftEncoder.getVelocity());
	} 
	
	public void shoot(double setpoint)
	{
		this.setpoint = setpoint;
		shooterPID.setReference(setpoint/*+200*/, ControlType.kVelocity); // steady state err is 200, but I terms make it VIOLENT
		
		// math goes like:
		// ff = setpoint * ff_gain
		// output = pid + ff
	}
	public void reverse()
    {
        shoot(-1500);
    }
	public void stop()
	{
		this.setpoint = 0;
		shooterPID.setReference(0, ControlType.kVelocity);
	}

	public boolean atSetpoint()
	{
		return Math.abs(leftEncoder.getVelocity() - setpoint) <= ERROR_TOLERANCE;
	}
	
	public boolean atSetpoint(double setpointToCheck)
	{
		return Math.abs(leftEncoder.getVelocity() - setpointToCheck) <= ERROR_TOLERANCE;
	}

	public double calcRPM(double tY) 
    {
		return 1940.435 - 30.356*tY;
    }
}
