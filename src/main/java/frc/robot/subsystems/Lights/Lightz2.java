package frc.robot.subsystems.Lights;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Lightz2 extends SubsystemBase
{
	// PWM port 0
	// Must be a PWM header, not MXP or DIO
	AddressableLED led = new AddressableLED(Constants.LED_PWM_PORT);
	AddressableLEDBuffer ledBuffer;

	public enum Mode { kRed, kBlue, kGreen, kRainbow, kPlano };
	Mode mode = Mode.kPlano;

	public Lightz2()
	{
		// Reuse buffer
		// Default to a length of 60, start empty output
		// Length is expensive to set, so only set it once, then just update data
		ledBuffer = new AddressableLEDBuffer(60);
		led.setLength(ledBuffer.getLength());

		// Set the data
		led.setData(ledBuffer);
		led.start();

		register();
	}

	@Override
	public void periodic()
	{		
		// Fill the buffer
		switch (mode)
		{
			case kRed:
				red();
				break;
			case kBlue:
				blue();
				break;
			case kGreen:
				green();
				break;
			case kRainbow:
				rainbow();
				break;
			default:
			case kPlano:
				plano();
				break;
		}
		
		// Set the LEDs
		led.setData(ledBuffer);
	}

	
	// Store what the last hue of the first pixel is
	private int rainbowFirstPixelHue;
	private void rainbow()
	{
		// For every pixel
		for (var i = 0; i < ledBuffer.getLength(); i++) 
		{
			// Calculate the hue - hue is easier for rainbows because the color
			// shape is a circle so only one value needs to precess
			final var hue = (rainbowFirstPixelHue + (i * 180 / ledBuffer.getLength())) % 180;
			// Set the value
			ledBuffer.setHSV(i, hue, 255, 128);
		}
		// Increase by to make the rainbow "move"
		rainbowFirstPixelHue += 3;
		// Check bounds
		rainbowFirstPixelHue %= 180;
	}

	/**
	 * maroon and white
	 */
	int lastWhiteIndex = 0;
	private void plano()
	{
		for (int i = 0; i < ledBuffer.getLength(); i++)
		{
			if (i % 8 != lastWhiteIndex)
			{
				ledBuffer.setLED(i, new Color8Bit(Color.kMaroon));
			}
			else
				ledBuffer.setLED(i, new Color8Bit(Color.kWhite));
		}
		lastWhiteIndex++;
		lastWhiteIndex %= ledBuffer.getLength();
	}

	private void red()
	{
		for (int i = 0; i < ledBuffer.getLength(); i++)
			ledBuffer.setLED(i, new Color8Bit(Color.kRed));
	}

	private void blue()
	{
		for (int i = 0; i < ledBuffer.getLength(); i++)
			ledBuffer.setLED(i, new Color8Bit(Color.kBlue));
	}

	private void green()
	{
		for (int i = 0; i < ledBuffer.getLength(); i++)
			ledBuffer.setLED(i, new Color8Bit(Color.kGreen));
	}
}
