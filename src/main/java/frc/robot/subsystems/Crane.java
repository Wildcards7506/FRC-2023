package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.CraneTOCom;

public class Crane extends SubsystemBase {
    private CANSparkMax endEffector;
    private CANSparkMax rotatorLeader;
    private CANSparkMax rotatorFollower;
    private CANSparkMax extender;
    private CANSparkMax wrist;
    
    private RelativeEncoder clawEncoder;
    private RelativeEncoder rotatorEncoder;
    private RelativeEncoder extenderEncoder;
    private RelativeEncoder wristEncoder;

    public SparkMaxPIDController rotatorPID;
    public SparkMaxPIDController clawPID;
    public SparkMaxPIDController wristPID;

    public boolean rollerInUse;

    // rollerOrShell = true means roller, false means shell
    public Crane(int rotator_lead, int rotator_follow, int craneExtender, int craneEndEffector, int craneWrist, boolean usingRoller) {
        rollerInUse = usingRoller;

        endEffector = new CANSparkMax(craneEndEffector, MotorType.kBrushless);
        rotatorLeader = new CANSparkMax(rotator_lead, MotorType.kBrushless);
        rotatorFollower = new CANSparkMax(rotator_follow, MotorType.kBrushless);
        extender = new CANSparkMax(craneExtender, MotorType.kBrushless);
        wrist = new CANSparkMax(craneWrist, MotorType.kBrushless);

        rotatorLeader.enableSoftLimit(SoftLimitDirection.kForward, true);
        rotatorLeader.enableSoftLimit(SoftLimitDirection.kReverse, true);
        rotatorFollower.enableSoftLimit(SoftLimitDirection.kForward, true);
        rotatorFollower.enableSoftLimit(SoftLimitDirection.kReverse, true);
        extender.enableSoftLimit(SoftLimitDirection.kForward, true);
        extender.enableSoftLimit(SoftLimitDirection.kReverse, true);

        rotatorLeader.setSmartCurrentLimit(Constants.kRotateCurrentLimit);
        rotatorFollower.setSmartCurrentLimit(Constants.kRotateCurrentLimit);
        extender.setSmartCurrentLimit(Constants.kExtenderCurrentLimit);
        endEffector.setSmartCurrentLimit(Constants.kClawCurrentLimit);
        wrist.setSmartCurrentLimit(Constants.kWristCurrentLimit);

        if (!usingRoller) {
            endEffector.enableSoftLimit(SoftLimitDirection.kForward, true);
            endEffector.enableSoftLimit(SoftLimitDirection.kReverse, true);

            endEffector.setSoftLimit(SoftLimitDirection.kForward, 0);
            endEffector.setSoftLimit(SoftLimitDirection.kReverse, 85);
        } else {
            endEffector.enableSoftLimit(SoftLimitDirection.kForward, false);
            endEffector.enableSoftLimit(SoftLimitDirection.kReverse, false);
        }

        rotatorFollower.follow(rotatorLeader, true);

        rotatorLeader.setSoftLimit(SoftLimitDirection.kForward, 0);
        rotatorLeader.setSoftLimit(SoftLimitDirection.kReverse, 330);
        rotatorFollower.setSoftLimit(SoftLimitDirection.kForward, 0);
        rotatorFollower.setSoftLimit(SoftLimitDirection.kReverse, 330);
        extender.setSoftLimit(SoftLimitDirection.kForward, 0);
        extender.setSoftLimit(SoftLimitDirection.kReverse, 28);

        rotatorEncoder = rotatorLeader.getEncoder();
        extenderEncoder = extender.getEncoder();
        clawEncoder = endEffector.getEncoder();
        wristEncoder = wrist.getEncoder();

        rotatorPID = rotatorLeader.getPIDController();
        clawPID = endEffector.getPIDController();
        wristPID = wrist.getPIDController();

        rotatorPID.setP(Constants.kRotatorKP);
        clawPID.setP(Constants.kClawKP);
        wristPID.setP(Constants.kWristKP);

        rotatorPID.setOutputRange(0, Constants.kRotatorMid);
        wristPID.setOutputRange(0, Constants.kRotatorMid);
        if (!usingRoller) clawPID.setOutputRange(0, Constants.kClawOpen);

        rotatorLeader.burnFlash();
        rotatorFollower.burnFlash();
        extender.burnFlash();
        endEffector.burnFlash();
        wrist.burnFlash();
    }

    @Override
    public void periodic() {
        setDefaultCommand(new CraneTOCom());
    }

    public void updateEncoderValues() {
        SmartDashboard.putNumber("Rotator 1 Position ", getRotatorEncoder());
        SmartDashboard.putNumber("Claw Position ", getClawEncoder());
        SmartDashboard.putNumber("Extender Position ", getExtenderEncoder());
        SmartDashboard.putNumber("Wrist Position ", getWristEncoder());
    }

    public double getRotatorEncoder() {
        return rotatorEncoder.getPosition();
    }

    public double getClawEncoder() {
        return clawEncoder.getPosition();
    }

    public double getExtenderEncoder() {
        return extenderEncoder.getPosition();
    }

    public double getWristEncoder() {
        return wristEncoder.getPosition();
    }

    public void setRotator(double setPoint) {
        rotatorPID.setReference(setPoint, ControlType.kPosition);
    }

    public void setClaw(double setPoint) {
        clawPID.setReference(setPoint, ControlType.kPosition);
    }

    public void setExtender(double setPoint) {
        if (Math.abs(setPoint - getExtenderEncoder()) > 0.5) {
            double voltage = 12 * (setPoint -getExtenderEncoder() ) / Math.abs(setPoint - getExtenderEncoder());
            extender.setVoltage(voltage);
        } else {
            extender.setVoltage(0);
        }
    }

    public void setWrist(double setPoint) {
        wristPID.setReference(setPoint, ControlType.kPosition);
    }

    public void setRoller (double setPoint) {
        endEffector.setVoltage(setPoint);
    }
}