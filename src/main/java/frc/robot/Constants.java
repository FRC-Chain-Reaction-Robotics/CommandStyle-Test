// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants
{
	public final static int LF_MOTOR_ID = 1;
    public final static int LB_MOTOR_ID = 3;
    public final static int RF_MOTOR_ID = 4;
    public final static int RB_MOTOR_ID = 10;
	
	public final static int RIGHT_SHOOTER_MOTOR_ID = 8;
    public final static int LEFT_SHOOTER_MOTOR_ID = 7;
	
    public final static int FEEDER_MOTOR_ID = 12;
	
    public final static int LEFT_HOPPER_MOTOR_ID = 16;
	public final static int RIGHT_HOPPER_MOTOR_ID = 19;
	
    public final static int INTAKE_MOTOR_ID = 6;
    
    public static final int LED_PWM_PORT = 0;
	public static final int LEFT_LIFT = 1337;
}