// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subsystems.Setup;
import frc.robot.subsystems.DrivebaseControl;
import frc.robot.subsystems.IntakeControl;
import frc.robot.subsystems.IntakePidTuning;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code
   */
  Setup setup;
  DrivebaseControl drivebaseControl;
  IntakeControl intakeControl;

  // Test
  // IntakePidTuning IntakePidTuning;
  // TalonSRX intakeMotor = new TalonSRX(2);
  // XboxController testXbox = new XboxController(0);

  @Override
  public void robotInit() {}

  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    setup = new Setup();
    setup.teleopSetup();
    drivebaseControl = setup.getDrivebaseControl();
    intakeControl = setup.getIntakeControl();

    //IntakePidTuning = new IntakePidTuning(intakeMotor, testXbox);
  }

  @Override
  public void teleopPeriodic() {
    intakeControl.IntakeTick();
    drivebaseControl.DrivebaseTick();
    setup.displayInfo();
    // IntakePidTuning.tick();
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}
