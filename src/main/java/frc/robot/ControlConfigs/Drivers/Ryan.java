package frc.robot.ControlConfigs.Drivers;

import frc.robot.Robot;
import frc.robot.ControlConfigs.PlayerConfigs;

public class Ryan extends PlayerConfigs {
    
    public static void getDriverConfig() {
        //Constants
        PlayerConfigs.turnSpeed = 0.3;
        PlayerConfigs.driveSpeed = 0.5;

        //Driving and rotation
        PlayerConfigs.xMovement = Robot.controller0.getLeftX();
        PlayerConfigs.yMovement = Robot.controller0.getLeftY();
        PlayerConfigs.turnMovement = Robot.controller0.getRightX();
        PlayerConfigs.modeSwitch = Robot.controller0.getCircleButton();

        //Signal object
        PlayerConfigs.signalCone = Robot.controller0.getTriangleButton();
        PlayerConfigs.signalCube = Robot.controller0.getSquareButton();
        PlayerConfigs.toggleLeds = Robot.controller0.getTouchpad();
    }

    public static void getCoDriverConfig() {
        //Constants turn speed drive speed
        PlayerConfigs.fineTurnSpeed = 0.175;
        PlayerConfigs.fineDriveSpeed = 0.2;

        //Fine movement
        PlayerConfigs.fineControlX = Robot.controller1.getLeftX();
        PlayerConfigs.fineControlY = Robot.controller1.getLeftY();
        PlayerConfigs.fineTurn = Robot.controller1.getRightX();

        //Scoring and grabbing objects
        PlayerConfigs.groundGrab = Robot.controller1.getPOV() == 180;
        PlayerConfigs.highGoal = Robot.controller1.getPOV() == 0;
        PlayerConfigs.lowGoal = Robot.controller1.getPOV() == 270;
        PlayerConfigs.openClaw = Robot.controller1.getPOV() == 90;

        //Claw
        PlayerConfigs.openClaw = Robot.controller1.getStartButton();

        //Limelight Switch
        PlayerConfigs.switchPipeline = Robot.controller1.getBackButton();
    }
}
