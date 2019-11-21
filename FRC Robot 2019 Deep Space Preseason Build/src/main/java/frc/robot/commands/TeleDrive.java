/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.concurrent.atomic.AtomicBoolean;

import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.NumberConstants;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class TeleDrive extends Command {

  private DriveMode driveMode = DriveMode.FAST;

  enum DriveMode {
    FAST,
    MEDIUM,
    SLOW,
    SUPERSLOW
  }

  public TeleDrive() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.driveTrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    SmartDashboard.putNumber("Battery Voltage:", RobotController.getBatteryVoltage());
    
    if (OI.XBoxControllerDriver.getXButton()) {
      
    }
    else {
    if(OI.XBoxControllerDriver.getAButtonPressed()) { // A Slows OI.JoyStickRight.getRawButtonPressed(3) || 
      switch(driveMode) {
      case MEDIUM:
        driveMode = DriveMode.FAST;
        break;
      case SLOW:
        driveMode = DriveMode.MEDIUM;
        break;
      case SUPERSLOW:
        driveMode = DriveMode.SLOW;
        break;
    default:
      break;
      }
    }
    if(OI.XBoxControllerDriver.getBButtonPressed()) { // B speeds OI.JoyStickRight.getRawButtonPressed(4) || 
      switch(driveMode) {
      case SLOW:
        driveMode = DriveMode.SUPERSLOW;
        break;
      case MEDIUM:
        driveMode = DriveMode.SLOW;
        break;
      case FAST:
        driveMode = DriveMode.MEDIUM;
        break;
    default:
      break;
      }
    }
    double slowMod;
    	switch(driveMode) {
    		case FAST:
          slowMod = 1;
          SmartDashboard.putString("Mode:", "FAST");
    			break;
    		case MEDIUM:
          slowMod = Math.sqrt(NumberConstants.SLOWING_CONSTANT);
          SmartDashboard.putString("Mode:", "MEDIUM");
    			break;
    		case SLOW:
          slowMod = NumberConstants.SLOWING_CONSTANT;
          SmartDashboard.putString("Mode:", "SLOW");
          break;
        case SUPERSLOW:
          slowMod = NumberConstants.SLOWING_CONSTANT*1.5;
          SmartDashboard.putString("Mode:", "SUPERSLOW");
    			break;
    		default:
          slowMod = 1;
          SmartDashboard.putString("Mode:", "FAST");
      }
      
    	double speed = (OI.XBoxControllerDriver.getTriggerAxis(Hand.kRight) - OI.XBoxControllerDriver.getTriggerAxis(Hand.kLeft)) / slowMod;
    	double turn = OI.XBoxControllerDriver.getX(Hand.kLeft) / slowMod;
      Robot.driveTrain.drive(speed, turn, true);
      SmartDashboard.putNumber("Speed %", Math.abs(speed)*100);

    	//SmartDashboard.putNumber("Left Talon Front", leftTalonFront.get());
    	//SmartDashboard.putNumber("Left Talon Rear", leftTalonRear.get());
    	//SmartDashboard.putNumber("Right Talon Front", rightTalonFront.get());
      //SmartDashboard.putNumber("Right Talon Rear", rightTalonRear.get());
      //SmartDashboard.putNumber("Speed", speed);
    }
  }

  // private double clamp(double target, double last) {
  //   double magnitude = (target - last) / Math.abs(target - last);
  //   if (Math.abs(target - last) > ACCELERATION_MAX) {
  //     return last + magnitude * ACCELERATION_MAX;
  //   }
  //   return target;
  // }

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
