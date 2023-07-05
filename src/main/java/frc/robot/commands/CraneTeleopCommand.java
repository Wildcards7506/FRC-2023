package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.ControlConfigs.PlayerConfigs;

public class CraneTeleopCommand extends CommandBase {
    double rotatorSetpoint;
    double extenderSetpoint;
    double wristSetpoint;
    private int prev_CraneState = -1;
    private boolean latch = false;
    private boolean release = false;

    public CraneTeleopCommand() {
        addRequirements(Robot.crane);
    }

    @Override
    public void execute() {
        //State Selection
        if(Robot.controller1.getPOV() != prev_CraneState && !release){
            latch = Robot.controller1.getPOV() == -1 ? true : false;
            prev_CraneState = Robot.controller1.getPOV() != -1 ? Robot.controller1.getPOV() : prev_CraneState;
        } else if (latch && Robot.controller1.getPOV() == prev_CraneState){
            release = true;
        } else if (latch && release){
            prev_CraneState = -1;
            release = false;
        }

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
        if (prev_CraneState == 180) {
            rotatorSetpoint = Constants.kRotatorGround;
            extenderSetpoint = Constants.kExtenderGround;
            wristSetpoint = Constants.kWristGround + Constants.cubeOffset * (Robot.limelight.getPipeline() - 1);
        } else if (prev_CraneState == 270){
            rotatorSetpoint = Constants.kRotatorDoubleSub + (Constants.kRotatorCubeOffset * Robot.limelight.getPipeline());
            extenderSetpoint = Constants.kExtenderCollect;
            wristSetpoint = Constants.kRotatorDoubleSub + Constants.cubeOffset * Robot.limelight.getPipeline();
        } else if (prev_CraneState == 90) {
            rotatorSetpoint = Constants.kRotatorMid;
            extenderSetpoint = Constants.kExtenderLo;
            wristSetpoint = Constants.kWristMid;
        } else if (prev_CraneState == 0) {
            rotatorSetpoint = Constants.kRotatorHi;
            extenderSetpoint = Constants.kExtenderHi;
            wristSetpoint = Constants.kWristHi;
        } else if (PlayerConfigs.craneControl){
            if (Math.abs(PlayerConfigs.cranePos) > 0.2){
                rotatorSetpoint = Robot.crane.getRotatorLEncoder() + (30 * PlayerConfigs.cranePos);
            } else {
                rotatorSetpoint = Robot.crane.getRotatorLEncoder();
            }
        } else if (PlayerConfigs.singleSub) {
            rotatorSetpoint = Constants.kRotatorSingleSub;
            extenderSetpoint = Constants.kExtenderClosed;
            Robot.crane.setWrist(Constants.kWristSingleSub);
        } else if(prev_CraneState == -1) {
            rotatorSetpoint = Constants.kRotatorClosed;
            extenderSetpoint = Constants.kExtenderClosed;
            wristSetpoint = Constants.kWristClosed;
        }

        Robot.crane.setRotator(rotatorSetpoint);
        Robot.crane.setWrist(wristSetpoint);
        SmartDashboard.putNumber("Rotator Setpoint", rotatorSetpoint);
        SmartDashboard.putNumber("Wrist Setpoint", wristSetpoint);

        //Extender
        if(PlayerConfigs.fineExtender){
            SmartDashboard.putString("Crane State", "Fine Control");
            if (Math.abs(PlayerConfigs.extendPos) > 0.2) {
                Robot.crane.setExtender(Robot.crane.getExtenderEncoder() + (3 * PlayerConfigs.extendPos));
                SmartDashboard.putNumber("Extender Setpoint", Robot.crane.getExtenderEncoder() + (3 * PlayerConfigs.extendPos));
            } else {
                Robot.crane.setExtender(Robot.crane.getExtenderEncoder());
            }
        } else if (prev_CraneState == 0 || prev_CraneState == 90){
            SmartDashboard.putString("Crane State", "Scoring");
            if (Robot.crane.getRotatorLEncoder() > Constants.kRotatorVertical) {
                Robot.crane.setExtender(extenderSetpoint);
                SmartDashboard.putNumber("Extender Setpoint", extenderSetpoint);
            } else {
                Robot.crane.setExtender(-3);
                SmartDashboard.putNumber("Extender Setpoint", -3);
            }
        } else if (prev_CraneState == 180 || prev_CraneState == 270){
            SmartDashboard.putString("Crane State", "Collecting");
            if (Robot.crane.getExtenderEncoder() > extenderSetpoint) {
                Robot.crane.setExtender(Robot.crane.getExtenderEncoder() + (12 * PlayerConfigs.extendPos));
                SmartDashboard.putNumber("Extender Setpoint", Robot.crane.getExtenderEncoder() + (12 * PlayerConfigs.extendPos));
            } else {
                Robot.crane.setExtender(Robot.crane.getExtenderEncoder());
                SmartDashboard.putNumber("Extender Setpoint", Robot.crane.getExtenderEncoder());
            }
        } else if (prev_CraneState == -1){
            SmartDashboard.putString("Crane State", "Neutral");
            Robot.crane.setExtender(Constants.kExtenderClosed);
            SmartDashboard.putNumber("Extender Setpoint", Constants.kExtenderClosed);
        }

        SmartDashboard.putNumber("POV", prev_CraneState);

        Robot.crane.updateEncoderValues();
    }
}
