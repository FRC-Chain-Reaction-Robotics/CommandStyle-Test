package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Saitek ST290 Pro flight stick
 */
public class Saitek extends Joystick
{
    public enum SButtons { __unused__, kTrigger, kM, kL, kR, kUL, kUR };

    public Saitek(int port)
    {
        super(port);
    }

    public double getZRotation()
    {
        return getRawAxis(3);
    }
}
