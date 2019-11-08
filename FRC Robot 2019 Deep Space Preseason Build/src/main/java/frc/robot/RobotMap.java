/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  // For example to map the left and right motors, you could define the
  // following variables to use with your drivetrain subsystem.
  // public static int leftMotor = 1;
  // public static int rightMotor = 2;

  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;


  public static final int LEFT_MOTOR_FRONT = 3;
  public static final int LEFT_MOTOR_REAR = 2;
  public static final int RIGHT_MOTOR_FRONT = 1;
  public static final int RIGHT_MOTOR_REAR = 0;
  public static final int CONTROLLER_STICK_PORT_RIGHT = 0;
  public static final int CONTROLLER_STICK_PORT_LEFT = 1;
    
  public static final int CONTROLLER_XBOX_PORT_DRIVER = 1;
  public static final int CONTROLLER_XBOX_PORT_SUBSYSTEM = 0;

  public static final int PLATE_PISTON_DEPLOY = 5;
  public static final int PLATE_PISTON_RETRACT = 4;
  public static final int CLAW_PISTON_DEPLOY = 3;
  public static final int CLAW_PISTON_RETRACT = 2;

  public static final int CARGO_SPARK = 8;
  
  public static final int RANGEFINDER = 1;
  
  public static final int LEFT_ENCODER_A = 2;
  public static final int LEFT_ENCODER_B = 3;
  public static final int RIGHT_ENCODER_A = 4;
  public static final int RIGHT_ENCODER_B = 5;

}
