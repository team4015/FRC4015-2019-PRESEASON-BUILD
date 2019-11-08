/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.subsystems.Piston;
/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class HatchSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private Piston clawPiston;
  private Piston platePiston;

  public HatchSubsystem() {
    clawPiston = new Piston(new Solenoid(RobotMap.CLAW_PISTON_DEPLOY), new Solenoid(RobotMap.CLAW_PISTON_RETRACT), "Claw");
    platePiston = new Piston(new Solenoid(RobotMap.PLATE_PISTON_DEPLOY), new Solenoid(RobotMap.PLATE_PISTON_RETRACT), "Plate");
  }
  public boolean getClawExtended() {
    return clawPiston.extended;
  }
  public boolean getPlateExtended() {
    return platePiston.extended;
  }
  public void extendClaw() {
    clawPiston.extend();
  }
  public void retractClaw() {
    clawPiston.retract();
  }
  public void extendPlate() {
    platePiston.extend();
  }
  public void retractPlate() {
    platePiston.retract();
  }
  public void toggleClaw() {
      clawPiston.toggle();
  }

  public void togglePlate() {
      platePiston.toggle();
  }

  public void initForStart() {
    platePiston.retract();
    clawPiston.extend();
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

}
