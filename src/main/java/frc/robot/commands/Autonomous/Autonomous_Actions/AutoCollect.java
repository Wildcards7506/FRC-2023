package frc.robot.commands.Autonomous.Autonomous_Actions;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.commands.Autonomous.Subsystem_Commands.AutoClawPosition;
import frc.robot.commands.Autonomous.Subsystem_Commands.AutoExtenderPosition;
import frc.robot.commands.Autonomous.Subsystem_Commands.AutoRotatorPosition;

public class AutoCollect extends SequentialCommandGroup {
  double action;
  int pipeline;
  
  public AutoCollect(int offset){
    if (Robot.crane.rollerInUse){
      action = 4;
    } else {
      action = Constants.kClawOpen;
    }

    addCommands(
      new ParallelCommandGroup(
        new AutoRotatorPosition(Constants.kRotatorGround, Constants.kRotatorGround + offset),
        new AutoExtenderPosition(Constants.kExtenderGround),
        new AutoClawPosition(action)),
      new AutoClawPosition(0),
      new AutoExtenderPosition(Constants.kExtenderClosed)
    );
  }
} 