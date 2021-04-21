// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase
{	
	TalonSRX intakeMotor = new TalonSRX(Constants.INTAKE_MOTOR_ID);
	TalonSRX leftHopperMotor = new TalonSRX(Constants.LEFT_HOPPER_MOTOR_ID);
	TalonSRX rightHopperMotor = new TalonSRX(Constants.RIGHT_HOPPER_MOTOR_ID);
	
	public Intake()
	{
        rightHopperMotor.follow(leftHopperMotor);
		intakeMotor.setInverted(true);
		
		setDefaultCommand(new RunCommand(this::off, this));
		register();
	}

	public void on() {
		intakeMotor.set(ControlMode.PercentOutput, 0.5);
	}
	
	public void reverse() {
		intakeMotor.set(ControlMode.PercentOutput, -0.5);
	}

	public void off() {
		intakeMotor.set(ControlMode.PercentOutput, 0);
	}
}
