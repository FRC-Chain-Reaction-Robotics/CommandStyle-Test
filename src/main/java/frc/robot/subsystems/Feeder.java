// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Feeder extends SubsystemBase
{
    TalonSRX feedMotor = new TalonSRX(Constants.FEEDER_MOTOR_ID);
    
    public Feeder()
    {
        setDefaultCommand(new RunCommand(this::off, this));
        register();
    }

    public void on() 
    {
        feedMotor.set(ControlMode.PercentOutput, 0.3);
    }
    
    public void reverse() 
    {
        feedMotor.set(ControlMode.PercentOutput, -0.3);
    }

    public void off()
    {
        feedMotor.set(ControlMode.PercentOutput, 0);
	}
}
