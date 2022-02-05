package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.util.Constants;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class BallShooter extends SubsystemBase {
  private CANSparkMax shooterMotor = new CANSparkMax(Constants.ShooterMotorID, MotorType.kBrushless);
  private RelativeEncoder shooterMotorEncoder = shooterMotor.getEncoder();

  public BallShooter() {
    shooterMotorEncoder.setPosition(0);
    setShooterIdleMode(IdleMode.kBrake);

  }

  public void enableShooterMotor() {
    double joyStickThrottle = RobotContainer.INSTANCE.joystick.getThrottle();
    double shooterThrottle = (-0.5*joyStickThrottle)+0.5;
    shooterMotor.set(shooterThrottle);
    //SmartDashboard.putNumber("ShooterRPM", shooterMotorEncoder.getVelocity());
    
  }

  public void disableShooterMotor() {
    shooterMotor.set(0);
  }

  public void setShooterIdleMode(IdleMode idleMode) {
    shooterMotor.setIdleMode(idleMode);
  }

}
