// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;


public class DriveTrain extends SubsystemBase {
  
  private final MotorController rightFront = new WPI_TalonSRX(10);
  private final MotorController rightRear = new WPI_TalonSRX(6);
  private final MotorControllerGroup rightMotorGroup = new MotorControllerGroup(rightFront, rightRear);

  private final MotorController leftFront = new WPI_TalonSRX(9);
  private final MotorController leftRear = new WPI_TalonSRX(7);
  private final MotorControllerGroup leftMotorGroup = new MotorControllerGroup(leftFront, leftRear);

  private final DifferentialDrive m_drive;


  /** Creates a new ExampleSubsystem. */
  public DriveTrain() {
    m_drive = new DifferentialDrive(leftMotorGroup, rightMotorGroup);
    leftMotorGroup.setInverted(true);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }


  public void driveByStick(final double liveX, final double liveZ) {
    m_drive.arcadeDrive(liveX, liveZ);
  }
}
