package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.DriveTrain.Motor;

public class AutoSubsystem extends SubsystemBase {
  public AutoSubsystem() {
    setNeutralMode(NeutralMode.Coast);
    resetMotorPositions();
    DriveTrain.rioGyro.reset();
  }

  @Override
  public void periodic() {

  }

  @Override
  public void simulationPeriodic() {

  }

  public void resetMotorPositions() {
    DriveTrain.getMotor(Motor.RIGHT_FRONT).setSelectedSensorPosition(0.0d);
    DriveTrain.getMotor(Motor.LEFT_FRONT).setSelectedSensorPosition(0.0d);
  }

  public void setNeutralMode(NeutralMode mode) {
    DriveTrain.getMotor(Motor.RIGHT_FRONT).setNeutralMode(mode);
    DriveTrain.getMotor(Motor.RIGHT_REAR).setNeutralMode(mode);
    DriveTrain.getMotor(Motor.LEFT_FRONT).setNeutralMode(mode);
    DriveTrain.getMotor(Motor.LEFT_REAR).setNeutralMode(mode);
  }

  public double getStartPos() {
    return DriveTrain.getMotor(Motor.RIGHT_FRONT).getSelectedSensorPosition();
  }
}
