/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.NumberConstants;
import frc.robot.RobotMap;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class CargoSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  // private SpeedControllerGroup cargoSpeedController = new SpeedControllerGroup(
  //   new Spark(RobotMap.CARGO_INTAKE), new Spark(RobotMap.CARGO_INTAKE_SEONDARY));
  Spark cargoSpark = new Spark(RobotMap.CARGO_SPARK); //Y-cable
  // Spark cargoSparkSecondary = new Spark(6);
  // SpeedControllerGroup cargoSpeedController = new SpeedControllerGroup(cargoSpark, cargoSparkSecondary);

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void outTakeRocket() {
    cargoSpark.set(-NumberConstants.ROCKET_POWER);
  }

  public void inTakeRocket() {
    cargoSpark.set(NumberConstants.ROCKET_POWER);
  }
  
  public void outTakeCargo() {
    cargoSpark.set(-NumberConstants.CARGO_POWER);
  }

  public void inTakeCargo() {
    cargoSpark.set(NumberConstants.CARGO_POWER);
  }

  public void idle() {
    cargoSpark.stopMotor();
  }

}
