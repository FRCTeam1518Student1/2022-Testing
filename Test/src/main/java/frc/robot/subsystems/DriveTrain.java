package frc.robot.subsystems;

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
  private final DifferentialDrive m_drive;
  private final double deadband = 0.1d;
 

  public DriveTrain() {
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
    m_drive.arcadeDrive(liveX, fixedLiveZ);
  }

  public void autonomousDrive(final double liveX, final double liveZ) {
    m_drive.arcadeDrive(liveX, liveZ);
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
