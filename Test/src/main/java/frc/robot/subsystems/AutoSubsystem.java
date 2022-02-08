package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.DriveTrain.Motor;
import edu.wpi.first.wpilibj.SerialPort;

public class AutoSubsystem extends SubsystemBase {

  public AHRS gyro;

  public AutoSubsystem() {
    setNeutralMode(NeutralMode.Coast);
    resetMotorPositions();
    setupGyro();
    gyro.reset();
  }

  @Override
  public void periodic() {

  }

  @Override
  public void simulationPeriodic() {

  }

  public void setupGyro() {
    gyro = new AHRS(SerialPort.Port.kUSB);
  }

  public double getZ(double angleToRotateTo) {
    if(Math.abs(gyro.getAngle() - angleToRotateTo) > 5.0d) {
      if(gyro.getAngle() - angleToRotateTo < 0) {
        return 0.2d;
      } else {
        return 0.2d;
      }
    }
    return 0.0d;
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
