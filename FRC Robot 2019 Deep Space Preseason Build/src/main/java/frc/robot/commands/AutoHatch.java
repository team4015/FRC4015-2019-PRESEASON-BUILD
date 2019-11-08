/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.NumberConstants;
import frc.robot.Robot;
import frc.robot.RobotMap;

/**
 * An example command. You can replace me with your own command.
 */
public class AutoHatch extends Command {
    String hatch;
    boolean deploy;
    public AutoHatch(String hatch, boolean deploy) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.hatchSubsystem);
        setTimeout(0.3);
        this.hatch = hatch;
        this.deploy = deploy;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (hatch.equals("plate")) {
            if (deploy) {
                Robot.hatchSubsystem.extendPlate();
            } else {
                Robot.hatchSubsystem.retractPlate();
            }
        } else if (hatch.equals("claw")) {
            if (deploy) {
                Robot.hatchSubsystem.extendClaw();
            } else {
                Robot.hatchSubsystem.retractClaw();
            }
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return isTimedOut();
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
