package frc.robot.subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.Constants;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class DriveTrain extends SubsystemBase {
  private final static WPI_TalonFX rightFront = new WPI_TalonFX(Constants.RightFrontID);
  private final static WPI_TalonFX rightRear = new WPI_TalonFX(Constants.RightRearID);
  private final MotorControllerGroup rightMotorGroup = new MotorControllerGroup(rightFront, rightRear);

  private final static WPI_TalonFX leftFront = new WPI_TalonFX(Constants.LeftFrontID);
  private final static WPI_TalonFX leftRear = new WPI_TalonFX(Constants.LeftRearID);
  private final MotorControllerGroup leftMotorGroup = new MotorControllerGroup(leftFront, leftRear);
  public static ADXRS450_Gyro rioGyro;
  private final DifferentialDrive m_drive;
  private final double deadband = 0.1d;
 

  public DriveTrain() {
    rioGyro = new ADXRS450_Gyro();
    rioGyro.reset();
    m_drive = new DifferentialDrive(leftMotorGroup, rightMotorGroup);
    leftMotorGroup.setInverted(true);
  }

  @Override
  public void periodic() {
  }

  @Override
  public void simulationPeriodic() {
  }


  public void driveByStick(final double liveX, final double liveZ) {
    double fixedLiveZ = Math.abs(liveZ) < deadband ? 0.0d : liveZ;
    double drift = getGyroAngleAsFraction();
	  drift = Math.min(drift, 0.1);
    m_drive.arcadeDrive(liveX, fixedLiveZ-drift);
  }

  protected double getGyroAngleAsFraction() {
		double angle = rioGyro.getAngle();
    // need to account for angle values > 360
    angle = (double)angle % 360;
    if (angle <= 90) {
      return -angle/90.0;
    } else if (angle >= 270) {
      return (360 - 270) / 90;
    }
		return 0;
	}

  public static WPI_TalonFX getMotor(Motor m) {
    return m.getTalonFx();
  }

  public enum Motor {
    RIGHT_FRONT(rightFront),
    RIGHT_REAR(rightRear),
    LEFT_FRONT(leftFront),
    LEFT_REAR(leftRear);

    private final WPI_TalonFX wFx;
    private Motor(WPI_TalonFX wFx) {
      this.wFx = wFx;
    }

    public WPI_TalonFX getTalonFx() {
     return this.wFx;
    }
  }
}
