// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.ExampleCommand;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class DriveTrain extends SubsystemBase {
  

  private final double startPos;
  private final MotorController rightFront = new WPI_TalonFX(2);
  private final MotorController rightRear = new WPI_TalonFX(1);
  private final MotorControllerGroup rightMotorGroup = new MotorControllerGroup(rightFront, rightRear);

  private final MotorController leftFront = new WPI_TalonFX(0);
  private final MotorController leftRear = new WPI_TalonFX(3);
  private final MotorControllerGroup leftMotorGroup = new MotorControllerGroup(leftFront, leftRear);
  public static Gyro rioGyro;
  private final DifferentialDrive m_drive;


  /** Creates a new ExampleSubsystem. */
  public DriveTrain() {
    rioGyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
    m_drive = new DifferentialDrive(leftMotorGroup, rightMotorGroup);
    leftMotorGroup.setInverted(true);
    startPos = ExampleCommand.motorSensor.getSelectedSensorPosition();
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
    System.out.println("Units traveled: " + Math.abs(ExampleCommand.motorSensor.getSelectedSensorPosition()-startPos));
    System.out.println(distanceToNativeUnits((1)));
    double drift = readGyro() / 100;
	  drift = Math.min(drift, 0.1);
    System.out.println("Drift: " + drift);
    System.out.println("Rot Deg:" + rioGyro.getRotation2d().getDegrees());
    //System.out.println("X:" + liveX);
    //System.out.println("Z:" + liveZ);
  }

  protected double readGyro() {
		double angle = rioGyro.getAngle();
		return angle;
	}

  public void autonomousDrive(final double liveX, final double liveZ) {
    m_drive.arcadeDrive(liveX, liveZ);
  }

  private int distanceToNativeUnits(double positionMeters) {
    double wheelRotations = positionMeters/(2 * Math.PI * Units.inchesToMeters(3));
    double motorRotations = wheelRotations * 10.71;
    int sensorCounts = (int)(motorRotations * 2048);
    return sensorCounts;
  }
}
