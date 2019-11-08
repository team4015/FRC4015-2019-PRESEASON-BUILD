
package frc.robot;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.Point;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GeraldLiterallyLosesHisMind {

    private GeraldLiterallyLosesHisMind() {}

    public static double getLineAngles(Mat data) {
        int width, height;
        width = data.width() - 1;
        height = data.height() - 1;

        ArrayList<Point> topFlags = new ArrayList<>();
        ArrayList<Point> bottomFlags = new ArrayList<>();
        ArrayList<Point> leftFlags = new ArrayList<>();
        ArrayList<Point> rightFlags = new ArrayList<>();
        topFlags = horizontalScan(data, 0, width);
        bottomFlags = horizontalScan(data, height, width);
        leftFlags = verticalScan(data, height, 0);
        rightFlags = verticalScan(data, height, width);
        ArrayList<Point> points = new ArrayList<>();
        points.addAll(topFlags);
        points.addAll(bottomFlags);
        points.addAll(leftFlags);
        points.addAll(rightFlags);
        if (points.size() == 4) {
            ArrayList<Point> pairA = new ArrayList<>();
            pairA.add(points.get(0));
            points.remove(0);
            int bestIndex = -1;
            int currentIndex = 0;
            double shortestDistance = Integer.MAX_VALUE;
            for (Point p : points) {
                if (distance(p, pairA.get(0), height, width) < shortestDistance) {
                    bestIndex = currentIndex;
                    shortestDistance = distance(p, pairA.get(0), height, width);
                }
                currentIndex++;
            }
            pairA.add(points.get(bestIndex));
            points.remove(bestIndex);
            Point itBeginsA = new Point((pairA.get(0).x + pairA.get(1).x) / 2,
            (pairA.get(0).y + pairA.get(1).y) / 2);
            Point itBeginsB = new Point((points.get(0).x + points.get(1).x) / 2,
            (points.get(0).y + points.get(1).y) / 2);

            SmartDashboard.putString("Point A", itBeginsA.x + " : " + itBeginsA.y);
            SmartDashboard.putString("Point B", itBeginsB.x + " : " + itBeginsB.y);

            if (itBeginsA.y > itBeginsB.y) {
                SmartDashboard.putBoolean("Autopilot Ready", true);
                return getAngleBetween(itBeginsA, itBeginsB);
            }
            else if (itBeginsA.y < itBeginsB.y) {
                SmartDashboard.putBoolean("Autopilot Ready", true);
                return getAngleBetween(itBeginsB, itBeginsA);
            }
            SmartDashboard.putBoolean("Autopilot Ready", false);
            return -1;
        }
        SmartDashboard.putBoolean("Autopilot Ready", false);
        return -1;
    }

    /**
     * 
     * @param a A point such that a.y > b.y
     * @param b A point such that b.y < a.y
     * @return The angle between a and b
     */
    private static double getAngleBetween(Point a, Point b) {
        return Math.toDegrees(
            Math.atan2(a.y - b.y, a.x - b.x)
        );
    }

    private static int distance(Point a, Point b, int height, int width) {
        if (coPlatformHorizontal(a, b, height)) return (int)(Math.abs(a.x - b.x));
        if (coPlatformVertical(a, b, width)) return (int)(Math.abs(a.y - b.y));
        Point acw = new Point(a.x, a.y), accw = new Point(a.x, a.y);
        boolean docw = true, doccw = true;
        int cwdist = 0, ccwdist = 0;
        for (int fullRotate = 0; fullRotate <= 5; fullRotate++) {
            if (docw) {
                if (coPlatformHorizontal(acw, b, height)) {
                    cwdist += (int)(Math.abs(acw.x - b.x));
                    docw = false;
                }
                else if (coPlatformVertical(acw, b, width)) {
                    cwdist += (int)(Math.abs(acw.x - b.x));
                    docw = false;
                }
                else {
                    cwdist += clockwiseShift(acw, height, width);
                }
            }
            if (doccw) {
                if (coPlatformHorizontal(accw, b, height)) {
                    ccwdist += (int)(Math.abs(accw.x - b.x));
                    doccw = false;
                }
                else if (coPlatformVertical(accw, b, width)) {
                    ccwdist += (int)(Math.abs(accw.x - b.x));
                    doccw = false;
                }
                else {
                    ccwdist += counterClockwiseShift(accw, height, width);
                }
            }
        }
        return (cwdist < ccwdist) ? cwdist : ccwdist;
    }

    private static double counterClockwiseShift(Point p, int height, int width) {
        if (p.x == width) {
            if (p.y == 0) {
                p.x = 0;
                return width;
            }
            else {
                int yShift = (int)p.y;
                p.y = 0;
                return yShift;
            }
        }
        if (p.x == 0) {
            if (p.y == height) {
                p.x = width;
                return width;
            }
            else {
                int yShift = height - (int)p.y;
                p.y = height;
                return yShift;
            }
        }
        else {
            if (p.y == height) {
                int xShift = width - (int)p.x;
                p.x = width;
                return xShift;
            }
            else {
                int xShift = (int)p.x;
                p.x = 0;
                return xShift;
            }
        }
    } 

    private static double clockwiseShift(Point p, int height, int width) {
        if (p.x == 0) {
            if (p.y == 0) {
                p.x = width;
                return width;
            }
            else {
                int yShift = (int)p.y;
                p.y = 0;
                return yShift;
            }
        }
        if (p.x == width) {
            if (p.y == height) {
                p.x = 0;
                return width;
            }
            else {
                int yShift = height - (int)p.y;
                p.y = height;
                return yShift;
            }
        }
        else {
            if (p.y == height) {
                int xShift = (int)p.x;
                p.x = 0;
                return xShift;
            }
            else {
                int xShift = width - (int)p.x;
                p.x = width;
                return xShift;
            }
        }
    } 

    private static boolean coPlatformVertical(Point a, Point b, int width) {
        return (a.x == b.x) ? (a.x == 0 || a.x == width) : false;
    }

    private static boolean coPlatformHorizontal(Point a, Point b, int height) {
        return (a.y == b.y) ? (a.y == 0 || a.y == height) : false;

    }

    private static ArrayList<Point> horizontalScan(Mat data, int height, int width) {
        ArrayList<Point> output = new ArrayList<>();
        boolean target = !activatedPixel(data.get(height, 1));
        boolean waitForLast = false;
        Point latest = null;
        for (int topScan = 1; topScan < width; topScan++) {
            if (activatedPixel(data.get(height, topScan)) == target) {
                if (!waitForLast) {
                    target = !target;
                    output.add(new Point(topScan, height));
                    waitForLast = true;
                } else {
                    latest = new Point(topScan, height);
                }
            }
        }
        if (latest != null) output.add(latest);
        return output;
    }

    private static ArrayList<Point> verticalScan(Mat data, int height, int width) {
        ArrayList<Point> output = new ArrayList<>();
        boolean target = !activatedPixel(data.get(1, width));
        boolean waitForLast = false;
        Point latest = null;
        for (int topScan = 1; topScan < height; topScan++) {
            if (activatedPixel(data.get(topScan, width)) == target) {
                if (!waitForLast) {
                    target = !target;
                    output.add(new Point(width, topScan));
                    waitForLast = true;
                } else {
                    latest = new Point(width, topScan);
                }
            }
        }
        if (latest != null) output.add(latest);
        return output;
    }

    private static boolean activatedPixel(double[] data) {
        return data[0] > 0.6;
    }



}