package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.DriveStraight;
import frc.robot.commands.FirePiston;
import frc.robot.subsystems.AutoSubsystem;
import frc.robot.subsystems.BallIndexer;
import frc.robot.subsystems.BallRejecter;
import frc.robot.subsystems.BallShooter;
import frc.robot.subsystems.Pivoter;
import frc.robot.subsystems.SolenoidSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class RobotContainer {
  private final AutoSubsystem m_exampleSubsystem = new AutoSubsystem();
  private final SolenoidSubsystem solenoidSubsystem = new SolenoidSubsystem(2, 3);

  private final DriveStraight m_autoCommand = new DriveStraight(m_exampleSubsystem);

  private final BallShooter ballShooter = new BallShooter();
  private final BallIndexer ballIndexer = new BallIndexer();
  private final BallRejecter ballRejecter = new BallRejecter();
  private final Pivoter limelightPivoter = new Pivoter();

  public final Joystick joystick = new Joystick(0);
  public JoystickButton shootButton;
  public JoystickButton indexButton;
  public JoystickButton switchPressure;

  public static RobotContainer INSTANCE;
  public RobotContainer() {
    mapButtons();
    mapButtonTasks();
    INSTANCE = this;
  }

  private void mapButtons() {
    switchPressure = new JoystickButton(joystick, 7);

    shootButton  = new JoystickButton(joystick, 2);

    indexButton = new JoystickButton(joystick, 1);
  }

  private void mapButtonTasks() {
    switchPressure.whenPressed(new FirePiston(solenoidSubsystem));

    shootButton.whileHeld(() -> ballShooter.enableShooterMotor()).whenReleased(() -> ballShooter.disableShooterMotor());

    indexButton.whileHeld(() -> ballIndexer.enableIndexer()).whenReleased(() -> ballIndexer.disableIndexer());
  }

  public Command getAutonomousCommand() {
    return m_autoCommand;
  }

  public Joystick getJoystick() {
    return this.joystick;
  }
}
