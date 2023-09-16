package frc.robot;

import edu.wpi.first.math.util.Units;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;

public final class Constants {
    public static final class CANID {
        //Drivetrain
        public static final int LEFT_FRONT_DRIVE = 11;
        public static final int LEFT_REAR_DRIVE = 10;
        public static final int RIGHT_FRONT_DRIVE = 1;
        public static final int RIGHT_REAR_DRIVE = 20;
        public static final int LEFT_FRONT_TURN = 12;
        public static final int LEFT_REAR_TURN = 13;
        public static final int RIGHT_FRONT_TURN = 14;
        public static final int RIGHT_REAR_TURN = 15;
        
        //Crane
        public static final int CRANE_EXTENDER = 19;
        public static final int CRANE_ROTATION_LEAD = 2;
        public static final int CRANE_ROTATION_FOLLOW = 3;
        public static final int CRANE_STINGER = 18;
        public static final int CRANE_WRIST = 17;

        //Pinchers
        public static final int PINCH_LEFT = 21;
        public static final int PINCH_RIGHT = 22;

        //Limelight Rotator
        public static final int LIMELIGHT_ROTATOR = 23;
    }

    public static final class IOConstants {
        //Controller Assignments
        public static final int DRIVER_CONTROLLER_0 = 0;
        public static final int DRIVER_CONTROLLER_1 = 1;
        
        //Control Axes
        public static final int LEFT_STICK_X = 0;
        public static final int LEFT_STICK_Y = 1;
        public static final int RIGHT_STICK_X = 2;
        public static final int RIGHT_STICK_Y = 3;

        //Control D-Pad
        public static final int DPAD_X = 2;
        public static final int DPAD_Y = 3;

        //Control Buttons
        public static final int BUTTON_A = 2;
        public static final int BUTTON_B = 3;
        public static final int BUTTON_X = 1;
        public static final int BUTTON_Y = 4;
        public static final int LEFT_BUMPER = 5;
        public static final int RIGHT_BUMPER = 6;

        public static final int LEFT_TRIGGER = 7;
        public static final int RIGHT_TRIGGER = 8;

        public static final int BUTTON_BACK = 9;
        public static final int BUTTON_START = 10;
        public static final int LEFT_JOYSTICK_BUTTON = 11;
        public static final int RIGHT_JOYSTICK_BUTTON = 12;
    }

    public static final class DriveConstants {
        // Driving Parameters - Note that these are not the maximum capable speeds of
        // the robot, rather the allowed maximum speeds
        public static final double kMaxSpeedMetersPerSecond = 4.8;
        public static final double kMaxAccelerationMetersPerSecondSquared = 3.0;
        public static final double kMaxAngularSpeed = 2 * Math.PI; // radians per second
    
        public static final double kDirectionSlewRate = 1.2; // radians per second
        public static final double kMagnitudeSlewRate = 1.8; // percent per second (1 = 100%)
        public static final double kRotationalSlewRate = 2.0; // percent per second (1 = 100%)
    
        // Chassis configuration
        public static final double kTrackWidth = Units.inchesToMeters(20.176);
        // Distance between centers of right and left wheels on robot
        public static final double kWheelBase = Units.inchesToMeters(21.911);
        // Distance between front and back wheels on robot
        public static final SwerveDriveKinematics kDriveKinematics = new SwerveDriveKinematics(
            new Translation2d(kWheelBase / 2, kTrackWidth / 2),
            new Translation2d(kWheelBase / 2, -kTrackWidth / 2),
            new Translation2d(-kWheelBase / 2, kTrackWidth / 2),
            new Translation2d(-kWheelBase / 2, -kTrackWidth / 2));
    
        // Angular offsets of the modules relative to the chassis in radians
        public static final double kFrontLeftChassisAngularOffset = -Math.PI / 2;
        public static final double kFrontRightChassisAngularOffset = 0;
        public static final double kBackLeftChassisAngularOffset = Math.PI;
        public static final double kBackRightChassisAngularOffset = Math.PI / 2;
    
        public static final boolean kGyroReversed = false;
        
        public static final double kSnapRange = 1;
        public static final double kSnapSpeed = 0.08;
        public static final double kAlignKP = 0.3;
      }
    
      public static final class ModuleConstants {
        // The MAXSwerve module can be configured with one of three pinion gears: 12T, 13T, or 14T.
        // This changes the drive speed of the module (a pinion gear with more teeth will result in a
        // robot that drives faster).
        public static final int kDrivingMotorPinionTeeth = 14;
    
        // Invert the turning encoder, since the output shaft rotates in the opposite direction of
        // the steering motor in the MAXSwerve Module.
        public static final boolean kTurningEncoderInverted = true;
    
        // Calculations required for driving motor conversion factors and feed forward
        public static final double kDrivingMotorFreeSpeedRps = NeoMotorConstants.kFreeSpeedRpm / 60;
        public static final double kWheelDiameterMeters = 0.0762;
        public static final double kWheelCircumferenceMeters = kWheelDiameterMeters * Math.PI;
        // 45 teeth on the wheel's bevel gear, 22 teeth on the first-stage spur gear, 15 teeth on the bevel pinion
        public static final double kDrivingMotorReduction = (45.0 * 22) / (kDrivingMotorPinionTeeth * 15);
        public static final double kDriveWheelFreeSpeedRps = (kDrivingMotorFreeSpeedRps * kWheelCircumferenceMeters)
            / kDrivingMotorReduction;
    
        public static final double kDrivingEncoderPositionFactor = (kWheelDiameterMeters * Math.PI)
            / kDrivingMotorReduction; // meters
        public static final double kDrivingEncoderVelocityFactor = ((kWheelDiameterMeters * Math.PI)
            / kDrivingMotorReduction) / 60.0; // meters per second
    
        public static final double kTurningEncoderPositionFactor = (2 * Math.PI); // radians
        public static final double kTurningEncoderVelocityFactor = (2 * Math.PI) / 60.0; // radians per second
    
        public static final double kTurningEncoderPositionPIDMinInput = 0; // radians
        public static final double kTurningEncoderPositionPIDMaxInput = kTurningEncoderPositionFactor; // radians
    
        public static final double kDrivingP = 0.04;
        public static final double kDrivingI = 0;
        public static final double kDrivingD = 0;
        public static final double kDrivingFF = 1 / kDriveWheelFreeSpeedRps;
        public static final double kDrivingMinOutput = -1;
        public static final double kDrivingMaxOutput = 1;
    
        public static final double kTurningP = 1;
        public static final double kTurningI = 0;
        public static final double kTurningD = 0;
        public static final double kTurningFF = 0;
        public static final double kTurningMinOutput = -1;
        public static final double kTurningMaxOutput = 1;
    
        public static final IdleMode kDrivingMotorIdleMode = IdleMode.kBrake;
        public static final IdleMode kTurningMotorIdleMode = IdleMode.kBrake;
    
        public static final int kDrivingMotorCurrentLimit = 50; // amps
        public static final int kTurningMotorCurrentLimit = 20; // amps
      }
    
      public static final class AutoConstants {
        public static final double kMaxSpeedMetersPerSecond = 3;
        public static final double kMaxAccelerationMetersPerSecondSquared = 3;
        public static final double kMaxAngularSpeedRadiansPerSecond = Math.PI;
        public static final double kMaxAngularSpeedRadiansPerSecondSquared = Math.PI;
    
        public static final double kPXController = 1;
        public static final double kPYController = 1;
        public static final double kPThetaController = 1;
    
        // Constraint for the motion profiled robot angle controller
        public static final TrapezoidProfile.Constraints kThetaControllerConstraints = new TrapezoidProfile.Constraints(
            kMaxAngularSpeedRadiansPerSecond, kMaxAngularSpeedRadiansPerSecondSquared);
      }
    
      public static final class NeoMotorConstants {
        public static final double kFreeSpeedRpm = 5676;
      }


      public static final class CraneConstants {
        public static final int kRotateCurrentLimit = 30;
        public static final double kRotateEncoderDistancePerPulse = 1.0/125 * 12.0/15 * 360;
        public static final double kRotatorKP = 0.007;
        public static final double kRotatorSingleSub = 40;
        public static final double kRotatorGroundClear = 15.0;
        public static final double kRotatorGround = -5.0;
        public static final double kRotatorHi = 160.0;
        public static final double kRotatorMid = 165.0;
        public static final double kRotatorDoubleSub = 85.0;
        public static final double kRotatorDoubleCubeOffset = -30;
        public static final double kRotatorSingleCubeOffset = -20;
        public static final double kRotatorVertical = 115.0;
        public static final double kRotatorClosed = 0.0;
        public static final double rotatorHorizontalOffset = 60;

        public static final int kExtenderCurrentLimit = 40;
        public static final double kExtendEncoderDistancePerPulse = 2.256*Math.PI/15.0;
        public static final double kExtenderKP = 0.1;
        public static final double kExtenderGround = -23.0;
        public static final double kExtenderLo = -4.0;
        public static final double kExtenderHi = -22.0;
        public static final double kExtenderCollect = -12.0;
        public static final double kExtenderClosed = -1.0;
        
        public static final int kStingerCurrentLimit = 40;

        public static final int kWristCurrentLimit = 10;
        public static final double kWristEncoderDistancePerPulse = 1.0/25 * 360;
        public static final double kWristKP = 0.1;
        public static final double kWristHi = -200.0;
        public static final double kWristMid = -190.0;
        public static final double kWristDoubleSub = -35.0;
        public static final double kWristGround = 90.0;
        public static final double kWristSingleSub= 90;
        public static final double kWristClosed = -10.0;
        public static final double kWristCubeDoubleOffset = 130.0;
        public static final double kWristCubeSingleOffset = -205;
        public static final double kWristCubeGroundOffset = 40;
      }
      public static final class PincherConstants{
        public static final double kHookLeft = 90.0;
        public static final double kHookRight = -90.0;
        public static final double kPullLeft = 270;
        public static final double kPullRight = -270;
        public static final double kPincherEncoderDistancePerPulse = 1.0/64 * 360;
      }

    public static final class LLRConstants {
        public static final int kLookForward = -180;
        public static final int kLookCollect = -160;
        public static final int kLookGoal = 0;
        public static final int kLimeLightCurrentLimit = 10;
        public static final double kLimelightRotatorEncoderDistancePerPulse = 1.0 / 3 * 360;
        public static final double kLimeLightRotatorKP = 0.05;
    }
}
