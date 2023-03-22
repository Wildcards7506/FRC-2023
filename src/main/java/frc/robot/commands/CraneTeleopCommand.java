package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.ControlConfigs.PlayerConfigs;

public class CraneTeleopCommand extends CommandBase {
    double extenderSetpoint;

    public CraneTeleopCommand() {
        addRequirements(Robot.crane);
    }

    @Override
    public void execute() {
        //End Effector
        if (PlayerConfigs.intake) {
            if(Robot.limelight.getPipeline() == 0){
                Robot.crane.setStinger(-8);
            } else if(Robot.limelight.getPipeline() == 1){
                Robot.crane.setStinger(8);
            }
        } else if (PlayerConfigs.release) {
            if(Robot.limelight.getPipeline() == 0){
                Robot.crane.setStinger(8);
            } else if(Robot.limelight.getPipeline() == 1){
                Robot.crane.setStinger(-8);
            }
        } else {
            if(Robot.limelight.getPipeline() == 0){
                Robot.crane.setStinger(-1);
            } else if(Robot.limelight.getPipeline() == 1){
                Robot.crane.setStinger(1);
            }
        }

        //Craneworks
        if (PlayerConfigs.groundGrab) {
            Robot.crane.setRotator(Constants.kRotatorGround);
            extenderSetpoint = Constants.kExtenderGround;
            Robot.crane.setWrist(Constants.kWristGround + Constants.cubeOffset * Robot.limelight.getPipeline());
        } else if (PlayerConfigs.collectPos){
            Robot.crane.setRotator(Constants.kRotatorCollect + (Constants.kRotatorCubeOffset * Robot.limelight.getPipeline()));
            extenderSetpoint = Constants.kExtenderCollect;
            Robot.crane.setWrist(Constants.kWristCollect + Constants.cubeOffset * Robot.limelight.getPipeline());
        } else if (PlayerConfigs.lowGoal) {
            Robot.crane.setRotator(Constants.kRotatorMid);
            extenderSetpoint = Constants.kExtenderLo;
            Robot.crane.setWrist(Constants.kWristMid);
        } else if (PlayerConfigs.highGoal) {
            Robot.crane.setRotator(Constants.kRotatorHi);
            extenderSetpoint = Constants.kExtenderHi;
            Robot.crane.setWrist(Constants.kWristHi);
        } else if (PlayerConfigs.craneControl || PlayerConfigs.redundantCraneControl){
            if (Math.abs(PlayerConfigs.cranePos) > 0.2){
                Robot.crane.setRotator(Robot.crane.getRotatorLEncoder() + (30 * PlayerConfigs.cranePos));
            } else {
                Robot.crane.setRotator(Robot.crane.getRotatorLEncoder());
            }
        } else if (PlayerConfigs.cubeHold) {
            Robot.crane.setRotator(Constants.kRotatorCubeHold);
            Robot.crane.setWrist(Constants.kWristCubeHold);
        } else {
            Robot.crane.setRotator(Constants.kRotatorClosed);
            extenderSetpoint = Constants.kExtenderClosed;
            Robot.crane.setWrist(Constants.kWristClosed);
        }

        //Extender
        if(Robot.crane.getExtenderEncoder() > extenderSetpoint & Robot.crane.getExtenderEncoder() <= -1.0 & Math.abs(PlayerConfigs.extendPos) > 0.2){
            Robot.crane.setExtender(12*PlayerConfigs.extendPos);
        } else if (Robot.crane.getExtenderEncoder() > -1 & PlayerConfigs.extendPos < -0.2){
            Robot.crane.setExtender(12*PlayerConfigs.extendPos);
        } else if (Robot.crane.getExtenderEncoder() < extenderSetpoint & PlayerConfigs.extendPos > 0.2){
            Robot.crane.setExtender(12*PlayerConfigs.extendPos);
        } else if (Robot.crane.getExtenderEncoder() < -1 & Math.abs(PlayerConfigs.extendPos) < 0.2){
            Robot.crane.setExtender(12);
        } else {
            Robot.crane.setExtender(0);
        }

        Robot.crane.updateEncoderValues();
    }
}