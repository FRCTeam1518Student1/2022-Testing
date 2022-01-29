package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.DuoSolenoid;

public class SolenoidSubsystem extends SubsystemBase {
    public DuoSolenoid duoSolenoid;
    public SolenoidSubsystem(int channel1, int channel2) {
        duoSolenoid = new DuoSolenoid(PneumaticsModuleType.CTREPCM, channel1, channel2);
    }

    @Override
    public void periodic() {

    }

    @Override
    public void simulationPeriodic() {

    }
}
