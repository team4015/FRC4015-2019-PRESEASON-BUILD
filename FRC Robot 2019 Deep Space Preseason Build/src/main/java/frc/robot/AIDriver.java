
/*package frc.robot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;

import org.opencv.core.Point;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.TapeSensorMKII.Line;

public class AIDriver {

    private final static double TOLERANCE = 15;
    private final static double AGGRESSION = 0.05;

    private static Object lineUpdateLock = new Object();
    private static LineExtender lineOne = null;
    private static double angleOne = 0;
    private static LineExtender lineTwo = null;
    private static double angleTwo = 0;
    private static BoundingBox bounds = null;

    static class BoundingBox {

        Point leftLow, rightHigh;

        BoundingBox(Point leftLow, Point rightHigh) {
            this.leftLow = new Point(Double.MAX_VALUE, Double.MAX_VALUE);
            this.rightHigh = new Point(Double.MIN_VALUE, Double.MIN_VALUE);
            update(leftLow, rightHigh);
        }

        public void update(Point ... points) {
            for (Point point : points) {
                leftLow.x = (point.x < leftLow.x) ? point.x : leftLow.x;
                leftLow.y = (point.y < leftLow.y) ? point.y : leftLow.y;
                rightHigh.x = (point.x > rightHigh.x) ? point.x : rightHigh.x;
                rightHigh.y = (point.y > rightHigh.y) ? point.y : rightHigh.y;
            }
        }

        public Point getCenter() {
            return new Point((leftLow.x + rightHigh.x) / 2, (leftLow.y + rightHigh.y) / 2);
        }

    }

    public static void processLines(ArrayList<TapeSensorMKII.Line> lines) {
        SmartDashboard.putNumber("Lines", lines.size());
        ArrayList<LineExtender> grouper = new ArrayList<>();
        ArrayList<ArrayList<Double>> angleGroupings = new ArrayList<>();
        ArrayList<BoundingBox> boundGroupings = new ArrayList<>();
        for (Line line : lines) {
            boolean isNewLine = true;
            Point pointA = new Point(line.x1, line.y1);
            Point pointB = new Point(line.x2, line.y2);
            int lineIndex = 0;
            for (LineExtender knownLine : Collections.unmodifiableList(grouper)) {
                double averageDistance = (knownLine.GetDistanceFromPoint(pointA) + knownLine.GetDistanceFromPoint(pointB) / 2);
                if (averageDistance < TOLERANCE) {
                    isNewLine = false;
                    angleGroupings.get(lineIndex).add(line.angle());
                    boundGroupings.get(lineIndex).update(pointA, pointB);
                }
                lineIndex++;
            }
            if (isNewLine) {
                grouper.add(new LineExtender(line));
                angleGroupings.add(new ArrayList<>());
                angleGroupings.get(angleGroupings.size() - 1).add(line.angle());
                boundGroupings.add(new BoundingBox(pointA, pointB));
            }
        }
        if (grouper.size() >= 2) {
            try {
                synchronized (lineUpdateLock) {
                    int bestOne = getMostSupportedIndex(grouper, angleGroupings);
                    lineOne = grouper.get(bestOne);
                    double sum = 0;
                    for (double angle : angleGroupings.get(bestOne)) {
                        sum += angle;
                    }
                    angleOne = sum / angleGroupings.get(bestOne).size();
                    bounds = new BoundingBox(boundGroupings.get(bestOne).leftLow, boundGroupings.get(bestOne).rightHigh);
                    grouper.remove(bestOne);
                    angleGroupings.remove(bestOne);
                    boundGroupings.remove(bestOne);
                    bestOne = getMostSupportedIndex(grouper, angleGroupings);
                    lineTwo = grouper.get(bestOne);
                    sum = 0;
                    for (double angle : angleGroupings.get(bestOne)) {
                        sum += angle;
                    }
                    angleTwo = sum / angleGroupings.get(bestOne).size();
                    bounds.update(boundGroupings.get(bestOne).leftLow, boundGroupings.get(bestOne).rightHigh);
                    SmartDashboard.putBoolean("AIDriver Ready", true);
                    SmartDashboard.putNumber("Line One Angle", angleOne);
                    SmartDashboard.putNumber("Line Two Angle", angleTwo);
                }
            } catch(Exception exception) {
                SmartDashboard.putString("Fat Error", exception.getMessage());
            } 
        }
        else {
            SmartDashboard.putBoolean("AIDriver Ready", false);
        }
    }

    private static int getMostSupportedIndex(ArrayList<LineExtender> grouper, ArrayList<ArrayList<Double>> angleGroupings) {
        int bestIndex = -1;
        int bestScore = 0;
        for (int index = 0; index < grouper.size(); index++) {
            int score = angleGroupings.get(index).size();
            bestIndex = (score > bestScore) ? index : bestIndex;
            bestScore = (score > bestScore) ? score : bestScore;
        }
        return bestIndex;
    }

    public static void drive() {
        if (lineOne != null && lineTwo != null && bounds != null) {
            //Robot.autoDrive.autoDrive(0,0);
            synchronized (lineUpdateLock) {
                
            }
        }
        else {
            //Robot.autoDrive.autoDrive(0,0);
        }
    }

}*/
