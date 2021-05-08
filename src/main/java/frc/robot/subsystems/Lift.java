package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Lift extends SubsystemBase
{
    CANSparkMax leftLift = new CANSparkMax(Constants.LEFT_LIFT, MotorType.kBrushless);

    public void on()
    {
        leftLift.set(0.3);
    }

    public void off()
    {
        leftLift.set(0);
    }    
}
