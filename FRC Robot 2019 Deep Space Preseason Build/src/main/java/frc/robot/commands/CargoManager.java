/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

/**
 * An example command.  You can replace me with your own command.
 */
public class CargoManager extends Command {

  public CargoManager() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.cargoSubsystem);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.cargoSubsystem.idle();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    //button 7 is left, button 8 is right
    if (OI.XBoxControllerSubsystem.getBumper(Hand.kLeft)) {
      Robot.cargoSubsystem.inTakeRocket();
    }
    else if (OI.XBoxControllerSubsystem.getBumper(Hand.kRight)) {
      Robot.cargoSubsystem.outTakeRocket();
    }
    else if (OI.XBoxControllerSubsystem.getRawButton(7)) {
      Robot.cargoSubsystem.inTakeCargo();
    } else if (OI.XBoxControllerSubsystem.getRawButton(8)) {
      Robot.cargoSubsystem.outTakeCargo();
    }
    else {
      Robot.cargoSubsystem.idle();
    }
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
