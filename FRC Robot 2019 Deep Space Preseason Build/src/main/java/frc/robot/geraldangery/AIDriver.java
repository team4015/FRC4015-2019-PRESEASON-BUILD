/*
package frc.robot.geraldangery;

import frc.robot.Robot;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.ArrayList;
import java.util.LinkedList;

import frc.robot.geraldangery.*;

public final class AIDriver {

    public static final GripPipeline pipeline = new GripPipeline();

    private static final double AGRESSION = 0.1;
    private static final double TARGET_WIDTH_CLOSE_RANGE = 100;
    private static final double MAXIMUM_SQUARE_DIFFERENCE = 10;

    private static CvSink source = CameraServer.getInstance().getVideo();


    private AIDriver() {}

    final static class Instructions {

        private final double SPEED, TURN;

        private Instructions(double SPEED, double TURN) {
            this.SPEED = SPEED;
            this.TURN = TURN;
        }

    }

    public static final void test() {
        Mat carpet = new Mat();
        source.grabFrame(carpet);
        pipeline.process(carpet);
        Robot.outputStream.putFrame(pipeline.hslThresholdOutput());
        ArrayList<MatOfPoint> points = pipeline.filterContoursOutput();
        LinkedList<Integer> cvData = new LinkedList<>();
        for (MatOfPoint toTest : points) {
            cvData.add(getWidth(toTest));
        }
        if (cvData.toArray() instanceof Double[]) {
            SmartDashboard.putNumberArray("CV_Data", (Double[]) cvData.toArray());
        }
    }

    private static final int getWidth(MatOfPoint data) {
        return Imgproc.boundingRect(data).width;
    }
 
    public static final double[] attemptAutomaticDriving() {
        
        Mat carpet = new Mat();
        source.grabFrame(carpet);
        pipeline.process(carpet);
        Instructions instructions = null;
        Mat data = pipeline.hslThresholdOutput();

        if (instructions == null) {
            double[] output = {0,0};
            return output;
        }
        else {
            double[] output = {instructions.SPEED, instructions.TURN};
            return output;
        }
    }

    private static final Instructions calculateCloseRangeMotion(MatOfPoint reflectiveTape) {
        return new Instructions(0, Math.tanh((reflectiveTape.width() - TARGET_WIDTH_CLOSE_RANGE))/Math.pow(AGRESSION, -1));
    }

    public static final boolean readyToAutomaticallyDrive() {
        GripPipeline pipeline = new GripPipeline();
        switch(pipeline.filterContoursOutput().size()) {
            default:
            return false;
            case 1:
            case 2:
            case 3:
            return true;
        }
    }

}*/
