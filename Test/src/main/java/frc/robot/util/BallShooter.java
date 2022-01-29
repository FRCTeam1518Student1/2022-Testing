package frc.robot.util;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class BallShooter extends SubsystemBase {
  private RobotContainer container;
    private CANSparkMax shooterMotor = new CANSparkMax(15, MotorType.kBrushless);
    private RelativeEncoder shooterMotorEncoder = shooterMotor.getEncoder();
  
    public BallShooter() {
      shooterMotorEncoder.setPosition(0);
      shooterMotor.setIdleMode(IdleMode.kCoast);
    }

    public void shoot() {
      // double shooterThrottle = container.joystick.getThrottle();
      shooterMotor.set(0.5);
      
    }

    public void stopShooting() {
      shooterMotor.set(0);
    }

}
