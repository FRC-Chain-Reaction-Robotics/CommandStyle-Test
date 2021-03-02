package frc.robot.subsystems;

import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
//its all good

public class Hopper extends SubsystemBase {

  private static TalonSRX hopperMotor = new TalonSRX(Constants.HOPPER_MOTOR_ID);
  
  public void HopperOn() 
  {
    hopperMotor.set(ControlMode.PercentOutput, 1);
  }

  public void HopperOff(){
    hopperMotor.set(ControlMode.PercentOutput,0);
  }

  

  @Override
  public void periodic() // This method will be called once per scheduler run
  {
    
  }
  

}
