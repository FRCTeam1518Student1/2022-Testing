package frc.robot;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoMode.PixelFormat;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.DriveTrain;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  private RobotContainer container;
 
  public static UsbCamera usbCamera;

  private final I2C.Port i2cPort = I2C.Port.kOnboard;
  private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);

  private final ColorMatch matcher = new ColorMatch();
  
  public static DriveTrain driveTrain = new DriveTrain();

  @Override
  public void robotInit() {
    container = new RobotContainer();
    usbCamera = CameraServer.startAutomaticCapture();
    usbCamera.setVideoMode(PixelFormat.kMJPEG, 160, 120, 15);
    matcher.addColorMatch(Color.kFirstBlue);
    matcher.addColorMatch(Color.kFirstRed);
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
    Color detectedColor = m_colorSensor.getColor();

    double IR = m_colorSensor.getIR();
    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);
    SmartDashboard.putNumber("IR", IR);
    int proximity = m_colorSensor.getProximity();
    SmartDashboard.putNumber("Proximity", proximity);
    ColorMatchResult res = matcher.matchClosestColor(detectedColor);

    //System.out.println(res.color.red > 0.1d ? "Ball red." : "Ball blue.");
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void autonomousInit() {
    m_autonomousCommand = container.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
    driveTrain.driveByStick(container.joystick.getY()*0.5d, -(container.joystick.getZ()*0.5d));

  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}
}
