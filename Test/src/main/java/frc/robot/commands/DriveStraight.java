package frc.robot.commands;

import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.AutoSubsystem;
import frc.robot.subsystems.DriveTrain.Motor;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveStraight extends CommandBase {
  private final AutoSubsystem m_subsystem;
  private double startPos;

  public DriveStraight(AutoSubsystem subsystem) {
    m_subsystem = subsystem;
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
    startPos = m_subsystem.getStartPos();
  }

  @Override
  public void execute() {
    if(isFinished() == true) {
      return;
    }
    //double drift = -(DriveTrain.getMotor(Motor.LEFT_FRONT).getSelectedSensorPosition()) - DriveTrain.getMotor(Motor.RIGHT_FRONT).getSelectedSensorPosition();
    double drift = Math.min((DriveTrain.rioGyro.getAngle() / 100d), 0.1);
    System.out.println("Drift: " + drift);
    //Robot.driveTrain.autonomousDrive(-0.3d, -drift);
  }

  @Override
  public void end(boolean interrupted) {}
  // ~45812 units = 1 meter 

  @Override
  public boolean isFinished() {
    return (Math.abs(DriveTrain.getMotor(Motor.RIGHT_FRONT).getSelectedSensorPosition()-startPos) > 91624);
  }
}