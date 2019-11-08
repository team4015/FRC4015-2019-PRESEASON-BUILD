/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

/**
 * An example command.  You can replace me with your own command.
 */
public class HatchManager extends Command {
  public HatchManager() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.hatchSubsystem);
    // Robot.hatchSubsystem.initForStart();
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    if (OI.XBoxControllerSubsystem.getBButtonPressed()) {
      Robot.hatchSubsystem.togglePlate();
    }
    else if (OI.XBoxControllerSubsystem.getAButtonPressed()) {
      Robot.hatchSubsystem.toggleClaw();
    }
    // if (OI.XBoxControllerSubsystem.getYButtonPressed()) {
    //   Robot.hatchSubsystem.extendClaw();
    // } else if (OI.XBoxControllerSubsystem.getXButtonPressed()) {
    //   Robot.hatchSubsystem.retractClaw();
    // }
    // if (OI.XBoxControllerSubsystem.getBButtonPressed()) {
    //   Robot.hatchSubsystem.extendPlate();
    // } else if (OI.XBoxControllerSubsystem.getAButtonPressed()) {
    //   Robot.hatchSubsystem.retractPlate();
    // }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
