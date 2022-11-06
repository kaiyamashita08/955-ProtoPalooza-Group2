// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

import frc.robot.subsystems.Drivebase;
import frc.robot.subsystems.Elevator;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.XboxController;

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

  Drivebase drivebase;
  XboxController driveXbox;
  XboxController intakeXbox;
  //elevator
  Elevator elevator;
  TalonFX elevatorMotor;
  DigitalInput limitSwitchTop;
  DigitalInput limitSwitchBottom;
  DoubleSolenoid elevatorSolenoidLock;

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
    drivebase = new Drivebase();
    driveXbox = new XboxController(0);
    intakeXbox = new XboxController(1);
    elevatorMotor = new TalonFX(12);
    limitSwitchTop = new DigitalInput(4);
    limitSwitchBottom = new DigitalInput(1);
    elevatorSolenoidLock = new DoubleSolenoid(PneumaticsModuleType.REVPH, 6, 5);
    elevator = new Elevator(elevatorMotor, limitSwitchTop, limitSwitchBottom, elevatorSolenoidLock);
  }

  @Override
  public void teleopPeriodic() {
    drivebase.drive(driveXbox.getLeftY(), driveXbox.getRightX());
    elevator.displayElevatorInfo();
    if (intakeXbox.getLeftBumper()) {
      elevator.unlockElevator();
      elevator.moveElevator(intakeXbox.getLeftY());
    } else {
      elevator.lockElevator();
    }
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
