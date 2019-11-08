package frc.robot.robotModes;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.AutoDrive;
import frc.robot.commands.AutoHatch;
import frc.robot.commands.CargoManager;
import frc.robot.commands.TeleDrive;
import frc.robot.commands.HatchManager;
import frc.robot.NumberConstants;
import frc.robot.Robot;

/* ===================================================
 * This CommandGroup calls the autonomous command to
 * drive to the base line.
 * =================================================*/

public class Auto extends CommandGroup
{
	final double meterTime = 3.1;
	// CONSTRUCTOR //
	public Auto()
	{
		// addSequential(new AutoDrive(NumberConstants.AUTO_SPEED, 0), 1*meterTime);
		addSequential(new AutoHatch("claw", true));
		addParallel(new CargoManager());
        addParallel(new HatchManager());
        addParallel(new TeleDrive());
		// Robot.driveTrain.disableSafety();
		// addSequential(new AutoDrive(0, NumberConstants.AUTO_TURNING_SPEED), NumberConstants.AUTO_TURN_TIME);
		// addSequential(new AutoDrive(NumberConstants.AUTO_SPEED, 0), 0.7*meterTime);
		// addSequential(new AutoDrive(0, NumberConstants.AUTO_TURNING_SPEED), NumberConstants.AUTO_TURN_TIME);

		
    }
	
}