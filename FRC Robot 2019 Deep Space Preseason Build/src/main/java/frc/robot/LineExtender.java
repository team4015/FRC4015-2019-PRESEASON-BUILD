
package frc.robot;

import org.opencv.core.Point;

import frc.robot.TapeSensorMKII.Line;

public class LineExtender {

    private final Double SLOPE;
    private final Double B;
    private final boolean isVertical;
    private final boolean isHorizontal;
    private final double verticalXConstant;
    
    public LineExtender(Line line) {
        this(line.x1, line.y1, line.x2, line.y2);
    }

    public LineExtender(double x1, double y1, double x2, double y2) {
        isVertical = (x1 == x2) ? true : false;
        isHorizontal = (y1 == y2) ? true : false;
        if (isHorizontal && !isVertical) {
            SLOPE = 0.0;
            B = y1 + 0.0;
            verticalXConstant = 0;
        }
        else if (!isHorizontal && isVertical) {
            SLOPE = Double.NaN;
            B = 0.0;
            verticalXConstant = x1;
        }
        else if (isHorizontal && isVertical) {
            SLOPE = Double.NaN;
            B = y1 + 0.0;
            verticalXConstant = x1;
        }
        else {
            SLOPE = (y2 - y1 + 0.0) / (x2 - x1 + 0.0);
            B = y1 - SLOPE * x1;
            verticalXConstant = 0;
        }
    }

    public LineExtender(Point point, double slope) {
        SLOPE = slope;
        isHorizontal = (SLOPE == 0.0) ? true : false;
        isVertical = (Double.isNaN(SLOPE)) ? true : false;
        if (isHorizontal && !isVertical) {
            B = point.y + 0.0;
            verticalXConstant = 0;
        }
        else if (!isHorizontal && isVertical) {
            B = 0.0;
            verticalXConstant = point.x;
        }
        else {
            B = point.y - SLOPE * point.x;
            verticalXConstant = 0;
        }
    }

    public boolean isHorizontal() {
        return isHorizontal;
    }

    public boolean isVertical() {
        return isVertical();
    }

    public boolean isPoint() {
        return isHorizontal && isVertical;
    }

    public Double getYValueAtX(double x) {
        if (isPoint()) {
            return (x == verticalXConstant) ? B : Double.NaN;
        }
        if (isHorizontal) {
            return B;
        }
        return SLOPE * x + B;
    }

    public Double getXValueAtY(double y) {
        if (isPoint()) {
            return (y == B) ? verticalXConstant : Double.NaN;
        }
        if (isVertical) {
            return 0.0 + verticalXConstant;
        }
        return (y - B) / SLOPE;
    }

    public double GetDistanceFromPoint(Point point) {
        if (isPoint()) {
            return getCartesianDistance(verticalXConstant, B, point.x, point.y);
        }
        if (isVertical) {
            return Math.abs(verticalXConstant - point.x);
        }
        if (isHorizontal) {
            return Math.abs(B - point.y);
        }
        return GetDistanceFromPoint(intersect(new LineExtender(point, -1.0 / SLOPE)));
    }

    public Point intersect(LineExtender lineExtender) {
        double x = (lineExtender.getB() - B) / (SLOPE - lineExtender.getSlope());
        return new Point(x, getYValueAtX(x));
    }

    public Double getSlope() {
        return SLOPE;
    }

    public double getB() {
        return B;
    }

    public double getCartesianDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

}