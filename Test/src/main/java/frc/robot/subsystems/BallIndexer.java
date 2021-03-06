package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.Constants;

public class BallIndexer extends SubsystemBase {
    public static CANSparkMax indexMotor = new CANSparkMax(Constants.BallIndexerID, MotorType.kBrushless);

    public static boolean override = false;

    public BallIndexer() {
        setIndexIdleMode(IdleMode.kBrake);
    }

    public void enableIndexer() {
        if(override) {
            return;
        }
        indexMotor.set(-0.5d);
    }

    public void disableIndexer() {
        if(override) {
            return;
        }
        indexMotor.set(0);
    }

    public void setIndexIdleMode(IdleMode idleMode) {
        indexMotor.setIdleMode(idleMode);
    }
}
