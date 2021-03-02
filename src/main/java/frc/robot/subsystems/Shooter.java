// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase
{
    CANSparkMax rightShooter;
    CANSparkMax leftShooter;
    CANPIDController shooterPID; 

    double kP, kI, kD;
    final double RPM_10FTLINE = 1350;

  /** Creates a new Shooter. */
  public Shooter() 
  {
      rightShooter = new CANSparkMax(Constants.RIGHT_SHOOTER_MOTOR_ID, MotorType.kBrushless);
      leftShooter = new CANSparkMax(Constants.LEFT_SHOOTER_MOTOR_ID, MotorType.kBrushless);  //  left and right
      shooterPID = leftShooter.getPIDController();

      leftShooter.restoreFactoryDefaults();		
      leftShooter.setInverted(true);
      leftShooter.setSmartCurrentLimit(40);

      shooterPID.setP(0.001);
      shooterPID.setI(0);
      shooterPID.setD(0.01);
      leftShooter.burnFlash();
  }

  @Override
  public void periodic() 
  {
    // This method will be called once per scheduler run
    //shoot
    //make more methods?
    //something controller
  } 

  public void shoot()
  {
      shooterPID.setReference(RPM_10FTLINE+200, ControlType.kVelocity); // steady state err is 200, but I terms make it VIOLENT
  }

  public void stop()
  {
      shooterPID.setReference(0,ControlType.kVelocity);
  }
}
