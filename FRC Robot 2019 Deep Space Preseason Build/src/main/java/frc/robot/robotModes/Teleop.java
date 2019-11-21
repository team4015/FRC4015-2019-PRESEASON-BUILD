
package frc.robot.robotModes;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.AutoAlign;
import frc.robot.commands.CargoManager;
import frc.robot.commands.TeleDrive;
import frc.robot.commands.HatchManager;

public class Teleop extends CommandGroup {

    public Teleop() {

        addParallel(new CargoManager());
        addParallel(new HatchManager());
        addParallel(new TeleDrive());
        addParallel(new AutoAlign());


    }

}