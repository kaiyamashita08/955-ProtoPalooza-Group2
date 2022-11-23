package frc.robot.subsystems;

import edu.wpi.first.wpilibj.XboxController;

public class IntakeXbox {
    XboxController intakeXboxController;
    Elevator elevator;
    Intake intake;

    public IntakeXbox(XboxController intakeXboxController, Elevator elevator, Intake intake) {
        this.intakeXboxController = intakeXboxController;
        this.elevator = elevator;
        this.intake = intake;
    }

    public void IntakeTick() {
        if (intakeXboxController.getLeftTriggerAxis() > 0.5) {
          elevator.moveElevator(-intakeXboxController.getLeftY());
        }
        // if (intakeXboxController.getLeftBumperPressed()) {
        //   intake.changeTargetby(-1 / 3);
        // }
        // if (intakeXboxController.getRightBumperPressed()) {
        //   intake.changeTargetby(1 / 3);
        // }
        // if (intakeXboxController.getXButton()) {
        //   intake.moveIntakeOverride(intakeXboxController.getRightY());
        // } else {
        //   intake.moveIntake();
        // }
        elevator.displayElevatorInfo();
    }
}
