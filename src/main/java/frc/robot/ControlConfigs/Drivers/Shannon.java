package frc.robot.ControlConfigs.Drivers;

import frc.robot.ControlConfigs.PlayerConfigs;
import frc.robot.Robot;

public class Shannon extends PlayerConfigs {
    
    public void getDriverConfig() {
        turnSpeed = 0.3;
        driveSpeed = 0.5;

        PlayerConfigs.xMovement = Robot.controller0.getLeftX();
        PlayerConfigs.yMovement = Robot.controller0.getLeftY();
        PlayerConfigs.turnMovement = Robot.controller0.getRightX();

        modeSwitch = Robot.controller0.getL2Button();
        // snapZero = Robot.controller0.getL3Button();
        // snap180 = Robot.controller0.getR3Button();
        PlayerConfigs.snapZero = Robot.controller0.getPOV() == 0;
        PlayerConfigs.snap90 = Robot.controller0.getPOV() == 90;
        PlayerConfigs.snap180 = Robot.controller0.getPOV() == 180;
        PlayerConfigs.snap270 = Robot.controller0.getPOV() == 270;

        PlayerConfigs.signalCone = Robot.controller0.getTriangleButton();
        PlayerConfigs.signalCube = Robot.controller0.getSquareButton();
        PlayerConfigs.toggleLeds = Robot.controller0.getCircleButton();
    }

    public void getCoDriverConfig() {
        PlayerConfigs.fineTurnSpeed = 0.15;
        PlayerConfigs.fineDriveSpeed = 0.25;

        PlayerConfigs.fineControlX = Robot.controller1.getLeftX();
        PlayerConfigs.fineControlY = Robot.controller1.getLeftY();
        PlayerConfigs.fineTurnMovement = Robot.controller1.getRightX();
        PlayerConfigs.fineControlToggle = Robot.controller1.getRightTriggerAxis() > .2;

        PlayerConfigs.collectPos = Robot.controller1.getLeftBumper();
        PlayerConfigs.groundGrab = Robot.controller1.getLeftTriggerAxis() >= 0.15;
        PlayerConfigs.highGoal = Robot.controller1.getRightBumper();
        PlayerConfigs.lowGoal = Robot.controller1.getRightTriggerAxis() >= 0.15;
        PlayerConfigs.openClaw = Robot.controller1.getAButton();

        PlayerConfigs.switchPipeline = Robot.controller1.getLeftStickButton();
    }
}
