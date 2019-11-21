/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.vision.VisionThread;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.subsystems.GripPipeline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class AutoAlign extends Command {
    private static final int IMG_WIDTH = 320;
	private static final int IMG_HEIGHT = 240;
	private VisionThread visionThread;
	private double centerX = 0.0;
    private double x1 = 0.0;
    private double x2 = 0.0;
    private double height1 = 0.0;
    private double height2 = 0.0;
    private double targetHeight = 70;//****change targetHeight to height of target
    private double driveSpeed = 0.0;
    private double turnSpeed = 0.0;
    private final Object imgLock = new Object();
    CvSink cvSink = new CvSink("opencv_USB Camera 0");
    CvSource outputStream = CameraServer.getInstance().putVideo("Vision", IMG_WIDTH, IMG_HEIGHT);
    // NetworkTable visionTable;
    // NetworkTableEntry area;
    boolean run = false;
  public AutoAlign() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.driveTrain);
    cvSink.setSource(Robot.cameraA);
    /*
    NetworkTableInstance inst = NetworkTabl eInstance.getDefault();
    inst.startClient("4015");    
    visionTable = inst.getTable("GRIP/myContoursReport");
    area = visionTable.getEntry("area"); //if area dosent exist, it is automatically created
    //Or you can access area directly: area = inst.getEntry("GRIP/myContoursReport/area");
    area.setNumber(value);
    */
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    
    visionThread = new VisionThread(Robot.cameraA, new GripPipeline(), pipeline -> {
        Mat output = new Mat();
        cvSink.grabFrame(output);
        if (!pipeline.filterContoursOutput().isEmpty()) {
            ArrayList<Rect> targets = new ArrayList<Rect>();
            for (int i = 0;i<pipeline.filterContoursOutput().size();i++) {
                Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(i));
                targets.add(r);
                Imgproc.rectangle(output, new Point(r.x, r.y),new Point(r.x+r.width, r.y+r.height), new Scalar(0, 255, 0, 255), 2);
            }
        
            synchronized (imgLock) {
                Collections.sort(targets, new LeftToRight());
                SmartDashboard.putNumber("Targets Detected", targets.size());
                if (targets.size()==2) {
                    Rect r1 = targets.get(0);
                    Rect r2 = targets.get(1);
                    x1 = r1.x+r1.width/2;
                    x2 = r2.x+r2.width/2;
                    centerX = (x1+x2)/2;
                    height1 = r1.height;
                    height2 = r2.height;
                } else if (targets.size()==1) {
                    Rect r1 = targets.get(0);
                    centerX = r1.x+r1.width/2;
                    height1 = r1.height;
                }
                targets.clear();
            }
        }
        outputStream.putFrame(output);
    });
    visionThread.start();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
      //Use Height ratio to gage distance, and use height ratio of both pieces of tape to determine angle
    double x1,x2,centerX,height1;
    synchronized (imgLock) {
        x1 = this.x1;
        x2 = this.x2;
        centerX = this.centerX;
        height1 = this.height1;
        height2 = this.height2;
    }
    double turn = centerX-(IMG_WIDTH/2);
    double distance = targetHeight-height1;
    System.out.println("RECT1: "+x1+" RECT2: "+x2+" CENTER: "+centerX+" HEIGHT1: "+height1+" HEIGHT2: "+height2+" DISTANCE: " +distance+" Turn: "+turn);
    if (OI.XBoxControllerDriver.getYButtonPressed()) {
        run = true;
    } else if (OI.XBoxControllerDriver.getXButtonPressed()) {
        run = false;
    }
    if (run==true) {
        if (Math.abs(turn)>15) {
            turnSpeed = turn * 0.0015;
        } else {
            turnSpeed = 0;
        }
        if (Math.abs(distance)>=8) {
            if (distance>0) {
                driveSpeed = 0.2;
            } else {
                driveSpeed = -0.25;
            }
        } else {
            driveSpeed = 0;
        }
        if (driveSpeed==0 && turnSpeed==0) {
            System.out.println("****ALIGNED****");
        } else {
            System.out.println("****DRIVING****"+driveSpeed);
        }
        Robot.driveTrain.drive(driveSpeed, turnSpeed, true);
    } else {
        if (Math.abs(turn)<=15 && Math.abs(distance)<8) {
            System.out.println("****ALIGNED****");
        }
        Robot.driveTrain.stop();
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
class LeftToRight implements Comparator<Rect> 
{ 
    // Used for sorting in ascending order of 
    // roll number 
    public int compare(Rect a, Rect b) 
    { 
        return a.x-b.x; 
    } 
} 