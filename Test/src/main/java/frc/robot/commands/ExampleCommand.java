// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.ExampleSubsystem;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class ExampleCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final ExampleSubsystem m_subsystem;
  private double startPos;
  public static final TalonFX motorSensor = new TalonFX(2);


  final int kCountsPerRev = 2048;
  final double kSensorGearRatio = 10.71;
  final double kWheelRadiusInches = 3;
  final int k100msPerSecond = 10;
  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public ExampleCommand(ExampleSubsystem subsystem) {
    m_subsystem = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    startPos = motorSensor.getSelectedSensorPosition();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(isFinished() == true) {
      return;
    }
    double drift = readGyro() / 100;
	  drift = Math.min(drift, 0.1);
    Robot.driveTrain.autonomousDrive(-0.5d, -drift);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

  }
  // ~5000 units = 1 foot 

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (Math.abs(motorSensor.getSelectedSensorPosition()-startPos) > 45812);
  }

  protected double readGyro() {
		double angle = DriveTrain.rioGyro.getAngle();
		return angle;
	}

  private int distanceToNativeUnits(double positionMeters) {
    double wheelRotations = positionMeters/(2 * Math.PI * Units.inchesToMeters(kWheelRadiusInches));
    double motorRotations = wheelRotations * kSensorGearRatio;
    int sensorCounts = (int)(motorRotations * kCountsPerRev);
    return sensorCounts;
  }


  private double nativeUnitsToDistanceMeters(double sensorCounts) {
    double motorRotations = (double)sensorCounts / kCountsPerRev;
    double wheelRotations = motorRotations / kSensorGearRatio;
    double positionMeters = wheelRotations * (2 * Math.PI * Units.inchesToMeters(kWheelRadiusInches));
    return positionMeters;
  }
}