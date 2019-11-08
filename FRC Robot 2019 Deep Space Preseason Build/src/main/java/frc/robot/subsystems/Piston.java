package frc.robot.subsystems;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/* ===================================================
 * This class enables control of a piston using a double
 * soleniod.  To create a piston, construct and instance
 * of this object with the solenoid channels as arguments.
 * 
 * NOTE: THIS CLASS ONLY WORKS WHEN USING DOUBLE SOLENOIDS
 * =================================================*/
public class Piston {

    public Solenoid solenoidExtend;
    public Solenoid solenoidRetract;
    public String name;
    public boolean extended;

    public Piston(Solenoid solenoidExtend, Solenoid solenoidRetract, String name) {
      this.solenoidExtend = solenoidExtend;
      this.solenoidRetract = solenoidRetract;
      this.name = name;
      retract();
    }

    public void extend() {
      solenoidExtend.set(true);
      solenoidRetract.set(false);
      extended = true;
      SmartDashboard.putBoolean(name, true);
    }

    public void retract() {
      solenoidExtend.set(false);
      solenoidRetract.set(true);
      extended = false;
      SmartDashboard.putBoolean(name, false);
    }

    public void toggle() {
      if (extended) {
        retract();
      }
      else {
        extend();
      }
    }

  }