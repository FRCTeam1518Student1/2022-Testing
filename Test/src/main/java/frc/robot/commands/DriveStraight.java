package frc.robot.commands;

import frc.robot.subsystems.DriveTrain;
import frc.robot.Robot;
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
    double z = m_subsystem.getZ(0);
    Robot.driveTrain.autonomousDrive(-0.3d, z);
  }

  @Override
  public void end(boolean interrupted) {}
  // ~45812 units = 1 meter 

  @Override
  public boolean isFinished() {
    return (Math.abs(DriveTrain.getMotor(Motor.RIGHT_FRONT).getSelectedSensorPosition()-startPos) > 45812);
  }
}