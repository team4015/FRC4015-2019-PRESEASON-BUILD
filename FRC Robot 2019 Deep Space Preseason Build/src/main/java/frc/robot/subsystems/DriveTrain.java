/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.NumberConstants;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private PWMTalonSRX leftTalonFront = new PWMTalonSRX(RobotMap.LEFT_MOTOR_FRONT);
  private PWMTalonSRX leftTalonRear = new PWMTalonSRX(RobotMap.LEFT_MOTOR_REAR);
  private PWMTalonSRX rightTalonFront = new PWMTalonSRX(RobotMap.RIGHT_MOTOR_FRONT);
  private PWMTalonSRX rightTalonRear = new PWMTalonSRX(RobotMap.RIGHT_MOTOR_REAR);
  private SpeedControllerGroup leftSide = new SpeedControllerGroup(leftTalonFront, leftTalonRear);
  private SpeedControllerGroup rightSide = new SpeedControllerGroup(rightTalonFront, rightTalonRear);
  private DifferentialDrive robotDrive = new DifferentialDrive(leftSide, rightSide);
  private double prevSpeed = 0;
  // public Encoder leftEncoder;
  // public Encoder rightEncoder;
  // public DriveTrain() {
  //   leftEncoder = new Encoder(RobotMap.LEFT_ENCODER_A, RobotMap.LEFT_ENCODER_B, false, Encoder.EncodingType.k4X);
  //   rightEncoder = new Encoder(RobotMap.RIGHT_ENCODER_A, RobotMap.RIGHT_ENCODER_B, false, Encoder.EncodingType.k4X);
  //   leftEncoder.reset();
  //   rightEncoder.reset();
  //   leftEncoder.setDistancePerPulse(NumberConstants.DISTANCE_PER_PULSE);
  //   rightEncoder.setDistancePerPulse(NumberConstants.DISTANCE_PER_PULSE);
  // }
  //Disables the automatic motor safety check
  public void disableSafety() {
    leftTalonFront.setSafetyEnabled(false);
    leftTalonRear.setSafetyEnabled(false);
    rightTalonFront.setSafetyEnabled(false);
    rightTalonRear.setSafetyEnabled(false);
    robotDrive.setSafetyEnabled(false);
  }
  //Enables the automatic motor safety check
  public void enableSafety() {
    leftTalonFront.setSafetyEnabled(true);
    leftTalonRear.setSafetyEnabled(true);
    rightTalonFront.setSafetyEnabled(true);
    rightTalonRear.setSafetyEnabled(true);
    robotDrive.setSafetyEnabled(true);
  }
  private double clamp(double target, double last) {
    double newSpeed = last;
    // double acceleration;
    // if (last == 0) {
    //   acceleration = target;
    // } else {
    //   acceleration = (target - last)/last;
    // }
    // System.out.println("ACCEL"+acceleration);
    // if (Math.abs(acceleration)>NumberConstants.ACCELERATION_MAX) {
    //   if (acceleration>0) {
    //       newSpeed*=(1+NumberConstants.ACCELERATION_MAX);
    //   } else if (acceleration<0) {
    //     newSpeed*=(1-NumberConstants.ACCELERATION_MAX);
    //   }
    // } else {
    //   newSpeed = target;
    // }
    newSpeed = target;
    return newSpeed;
  }
  public void stop() {
    drive(0,0,true);    
  }
  public void setLeftSpeed(double leftSpeed) {
      leftSide.set(leftSpeed);
  }

  public void setRightSpeed(double rightSpeed) {
      rightSide.set(rightSpeed);
  }
  public void drive(double speed, double turn, boolean quickTurn) {
    prevSpeed = clamp(speed,prevSpeed);
    robotDrive.curvatureDrive(speed, turn/NumberConstants.TURNING_CONSTANT,quickTurn);
    // //Adjustment for going forward
    if (speed>0.5) {
      // System.out.println("++++++++++++++FORWARD++++++++++++++");
      if (leftSide.get()*1.6>1) {
        rightSide.set(rightSide.get()/1.6);
      } else {
        leftSide.set(leftSide.get()*1.6);//good for going forward, not good for going backwards, increasre right side when going back or decrease left side
      }
    } 
    else if (speed>0.3 && speed<=0.5) {
      leftSide.set(leftSide.get()*1.7);
    }
    else if (speed>0 && speed<=0.3) {
      leftSide.set(leftSide.get()*1.9);
    }
    //Adjustment for going backwards
    else if (speed<0 && speed >=-0.5) {
      // System.out.println("-------------BACKWARDS-------------");
      if (leftSide.get()*1.15<-1) {
        rightSide.set(rightSide.get()/1.15);
      } else {
        leftSide.set(leftSide.get()*1.15);//good for going forward, not good for going backwards, increasre right side when going back or decrease left side
      }
    }
    else if (speed <-0.5) {
      // System.out.println("-------------BACKWARDS-------------");
      if (leftSide.get()*1.25<-1) {
        rightSide.set(rightSide.get()/1.25);
      } else {
        leftSide.set(leftSide.get()*1.25);//good for going forward, not good for going backwards, increasre right side when going back or decrease left side
      }
    }
    // Timer.delay(0.05);  // motor update time
  }
  //Auto Methods
  // public void driveStraight(double distance) {
  //   leftEncoder.reset();
  //   rightEncoder.reset();
  //   leftSide.set(NumberConstants.AUTO_SPEED);
  //   rightSide.set(NumberConstants.AUTO_SPEED);
  //   while(leftEncoder.getDistance()<distance)  {
  //     double error = leftEncoder.getDistance()-rightEncoder.getDistance();
  //     double correctedSpeed = rightSide.get()+error/NumberConstants.ENCODER_ERROR_CONSTANT;
  //     if (correctedSpeed>1.0) {
  //       correctedSpeed = 1.0;
  //     } else if (correctedSpeed<-1.0) {
  //       correctedSpeed = -1.0;
  //     }
  //     rightSide.set(correctedSpeed);
  //     Timer.delay(0.1);
  //   }
  //   leftSide.set(0);
  //   rightSide.set(0);
  // }

  // public void turnLeft() {
  //   leftEncoder.reset();
  //   rightEncoder.reset();
  //   leftSide.set(-NumberConstants.AUTO_SPEED);
  //   rightSide.set(NumberConstants.AUTO_SPEED);
  //   while(rightEncoder.getDistance()<NumberConstants.TURN_DISTANCE)  {
  //     double error = -leftEncoder.getDistance()-rightEncoder.getDistance();
  //     double correctedSpeed = rightSide.get()+error/NumberConstants.ENCODER_ERROR_CONSTANT;
  //     if (correctedSpeed>1.0) {
  //       correctedSpeed = 1.0;
  //     } else if (correctedSpeed<-1.0) {
  //       correctedSpeed = -1.0;
  //     }
  //     rightSide.set(correctedSpeed);
  //     Timer.delay(0.1);
  //   }
  //   leftSide.set(0);
  //   rightSide.set(0);
  // }

  // public void turnRight() {
  //   leftEncoder.reset();
  //   rightEncoder.reset();
  //   leftSide.set(NumberConstants.AUTO_SPEED);
  //   rightSide.set(-NumberConstants.AUTO_SPEED);
  //   while(leftEncoder.getDistance()<NumberConstants.TURN_DISTANCE)  {
  //     double error = leftEncoder.getDistance()+rightEncoder.getDistance();
  //     double correctedSpeed = rightSide.get()+error/NumberConstants.ENCODER_ERROR_CONSTANT;
  //     if (correctedSpeed>1.0) {
  //       correctedSpeed = 1.0;
  //     } else if (correctedSpeed<-1.0) {
  //       correctedSpeed = -1.0;
  //     }
  //     rightSide.set(correctedSpeed);
  //     Timer.delay(0.1);
  //   }
  //   leftSide.set(0);
  //   rightSide.set(0);
  // }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}