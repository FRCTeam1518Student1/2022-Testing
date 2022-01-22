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
  public static final TalonFX motorSensor = new TalonFX(0);

  long startTime;
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
    startTime = System.currentTimeMillis();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(isFinished() == true) {
      return;
    }
    /*if(!hasPassed(500)) {
      System.out.println("Debug: " + Double.valueOf(-(Double.valueOf((System.currentTimeMillis()-startTime)/1000))));
      Robot.driveTrain.autonomousDrive(Double.valueOf(-(Double.valueOf((System.currentTimeMillis()-startTime)/1000))), 0.0d);
      return;
    }*/
    double drift = getGyroAngle() / 100;
	  drift = Math.min(drift, 0.1);
    //System.out.println("Drift: " + drift);
    //Robot.driveTrain.autonomousDrive(-0.3d, -drift);
    // last value: 1125
    Robot.driveTrain.autonomousDrive(-0.3d, 0.1115-drift);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

  }
  // ~45812 units = 1 meter 

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (Math.abs(motorSensor.getSelectedSensorPosition()-startPos) > 91624);
  }

  private boolean hasPassed(long ms) {
      return (System.currentTimeMillis()-startTime > ms);
  }

  protected double getGyroAngle() {
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