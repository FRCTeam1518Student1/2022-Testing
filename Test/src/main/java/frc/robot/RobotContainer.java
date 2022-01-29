package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.DriveStraight;
import frc.robot.commands.FirePiston;
import frc.robot.subsystems.AutoSubsystem;
import frc.robot.subsystems.SolenoidSubsystem;
import frc.robot.util.BallShooter;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class RobotContainer {
  private final AutoSubsystem m_exampleSubsystem = new AutoSubsystem();
  private final SolenoidSubsystem solenoidSubsystem = new SolenoidSubsystem(2, 3);

  private final DriveStraight m_autoCommand = new DriveStraight(m_exampleSubsystem);

  public final Joystick joystick = new Joystick(0);
  public JoystickButton shootButton;
  public JoystickButton switchPressure;
  private final BallShooter ballshooter = new BallShooter();

  public RobotContainer() {
    configureButtonBindings();
  }

  private void configureButtonBindings() {
    switchPressure = new JoystickButton(joystick, 7);
    switchPressure.whenPressed(new FirePiston(solenoidSubsystem));
    shootButton  = new JoystickButton(joystick, 1); // trigger on stick
    shootButton.whenPressed(() -> ballshooter.shoot()).whenReleased(() -> ballshooter.stopShooting());
  }

  public Command getAutonomousCommand() {
    return m_autoCommand;
  }
}
