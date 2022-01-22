// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.OI;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  private final TalonFX rightFront = new TalonFX(2);

  private long startTime;

  private RobotContainer m_robotContainer;

  public static DriveTrain driveTrain = new DriveTrain();
  private OI oi = new OI();

  @Override
  public void robotInit() {
    m_robotContainer = new RobotContainer();
    startTime = System.currentTimeMillis();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {
    /*if(System.currentTimeMillis()-startTime < 500L) {
      driveTrain.autonomousDrive(0.75d, 0.75d);
    }*/
  }

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
    driveTrain.driveByStick(oi.joystick.getY()*0.5d, -(oi.joystick.getZ()*0.5d));
    //if (OI.joystick.getRawButton(1)) {
      /*System.out.println("Sensor Vel:" + rightFront.getSelectedSensorVelocity());
      System.out.println("Sensor Pos:" + rightFront.getSelectedSensorPosition());
      System.out.println("Out %" + rightFront.getMotorOutputPercent());*/
    //}
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}
}
