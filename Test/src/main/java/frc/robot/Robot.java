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
import frc.robot.subsystems.BallShooter;
import frc.robot.subsystems.DriveTrain;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  private RobotContainer container;
 
  public static UsbCamera usbCamera;

  private final I2C.Port i2cPort = I2C.Port.kOnboard;
  private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
  
  private static String currentBall = ""; 

  public static DriveTrain driveTrain = new DriveTrain();

  private double lastPos1, lastPos2, posDifference;

  @Override
  public void robotInit() {
    container = new RobotContainer();
    usbCamera = CameraServer.startAutomaticCapture();
    usbCamera.setVideoMode(PixelFormat.kMJPEG, 160, 120, 15);
    //matcher.addColorMatch(new Color(0.523, 0.350, 0.127)); // red
    //matcher.addColorMatch(new Color(0.154, 0.393, 0.454)); // blue
    //matcher.addColorMatch(new Color(0.301, 0.487, 0.211)); // nothing
    posDifference = 0;
    lastPos1 = lastPos2 = -1;
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
    //ColorMatchResult res = matcher.matchClosestColor(detectedColor);
    doShooterRPM();
    currentBall = getBallColorName(detectedColor);
    SmartDashboard.putString("Ball Color", getBallColorName(detectedColor));
  }

  public String getBallColorName(Color c) {
    double r = c.red;
    if(r > 0.4) {
      return "Red";
    } else if (r < 0.2) {
      return "Blue";
    }
    return "None";
  }

  public static String getCurrentColorBall() {
    return currentBall;
  }

  public void doShooterRPM() {
    // counts per revolution = 42
    // 60 secs in a min
    // 42*60=2520
    if(lastPos1 == -1 && lastPos2 == -1) {
      lastPos1 = BallShooter.shooterMotorEncoder.getPosition();
    } else if(lastPos1 != -1 && lastPos2 == -1) {
      lastPos2 = BallShooter.shooterMotorEncoder.getPosition();
    } else if(lastPos1 != -1 && lastPos2 != -1) {
      posDifference = Math.abs(lastPos2-lastPos1)*2520;
      lastPos1 = lastPos2 = -1;
    }
    SmartDashboard.putNumber("ShooterRPM", posDifference);
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
