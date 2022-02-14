package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;

public class BallRejecter extends SubsystemBase {
    String teamColor;
    public BallRejecter() {
    }
  
    @Override
    public void periodic() {
        teamColor = DriverStation.getAlliance().toString();
        if(!Robot.getCurrentColorBall().equalsIgnoreCase(teamColor) && !Robot.getCurrentColorBall().equalsIgnoreCase("None")) {
            BallShooter.override = BallIndexer.override = true;
            BallShooter.shooterMotor.set(0.2d);
            BallIndexer.indexMotor.set(-1d);
        } else {
            if(BallShooter.override) {
                BallShooter.shooterMotor.set(0.0d);
                BallIndexer.indexMotor.set(0.0d);
                BallShooter.override = BallIndexer.override = false;
            }
        }
    }
    
  
    @Override
    public void simulationPeriodic() {
  
    }
}
