package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PDP extends SubsystemBase
{
    PowerDistributionPanel pdp = new PowerDistributionPanel();

    public PDP()
    {
        var powerTab = Shuffleboard.getTab("PDP");

        powerTab.add("PDP", pdp);
        powerTab.addNumber("Temperature (C)", pdp::getTemperature);
    }
}
