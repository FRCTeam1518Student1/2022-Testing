package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class OI extends SubsystemBase {

    public final Joystick joystick = new Joystick(0);
    public static double liveX;
    public static double liveZ;
    public static double livePow;


    public OI() {

    }


    public void get() {
        liveX = joystick.getX();
        liveZ = joystick.getZ();
        livePow = joystick.getThrottle();
    }
    
}
