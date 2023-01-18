package frc.robot.ControlConfigs;

public class PlayerConfigs {

    //buttons used: left joystick, right joystick, circle, entire d pad, L1, L2, R1, R2
    
    //drivetrain
    public static double xMovement;
    public static double yMovement;
    public static double turnMovement;
    public static double turnSpeed;
    public static double driveSpeed;
    public static boolean modeSwitch;
    public static double fineControlX;
    public static double fineControlY;
    public static double fineTurn;
    public static double fineTurnSpeed;
    public static double fineDriveSpeed;

    //crane
    public static boolean collectPos;
    public static boolean groundGrab;
    public static boolean highGoal;
    public static boolean lowGoal;
    public static boolean openClaw;

    //limelight
    public static boolean switchPipeline;

    //LEDs
    public static boolean signalCone;
    public static boolean signalCube;
    public static boolean toggleLeds;

    public static void getDriverConfig(){
        
    }

    /*Need to add Co-Driver controls
     * Arm Positioning (Ground, Middle Score, High Score, Shelf Pickup)
     * Arm Extension (Ground, Middle Score, High Score, Shelf Pickup)
     * Claw/Pincher Release
     * Fine-Detail Driving control (Scoring and Endgame)
    */

    public static void getCoDriverConfig(){  
        
    }
}