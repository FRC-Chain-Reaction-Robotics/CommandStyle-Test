// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Lightz extends SubsystemBase
{
    Spark blinkin;

	/** Creates a new Lightz. */
    public Lightz()
    {
        blinkin = new Spark(0);
        //start color method here
        rainbow();
    }

    public void set(double val)
    {
        blinkin.set(val);
    }

    public void rainbow()
    {
        set(-0.99);
    }

    public void solid_orange()
    {
        set(0.65);
    }

    public void allianceColor()
    {
        boolean isRed = NetworkTableInstance.getDefault().getTable("FMSInfo").getEntry("IsRedAlliance").getBoolean(true);
        if (isRed)
        {
            set(-0.01);
        }
        else
        {
            set(0.19);
        }
    }
}
