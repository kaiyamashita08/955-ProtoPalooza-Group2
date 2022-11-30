package frc.robot.subsystems;

import edu.wpi.first.wpilibj.XboxController;

public class IntakeControl implements AutoCloseable {
    XboxController intakeXbox;
    Elevator elevator;
    Intake intake;

    public IntakeControl(XboxController intakeXbox, Elevator elevator, Intake intake) {
        this.intakeXbox = intakeXbox;
        this.elevator = elevator;
        this.intake = intake;
    }

    public void IntakeTick() {
        if (intakeXbox.getLeftTriggerAxis() > 0.5) {
          elevator.moveElevator(-intakeXbox.getLeftY());
        }
        // if (intakeXbox.getLeftBumperPressed()) {
        //   intake.changeTargetby(-1 / 3);
        // }
        // if (intakeXbox.getRightBumperPressed()) {
        //   intake.changeTargetby(1 / 3);
        // }
        // if (intakeXbox.getXButton()) {
        //   intake.moveIntakeOverride(intakeXbox.getRightY());
        // } else {
        //   intake.moveIntake();
        // }
    }

    public void close() throws Exception {
        elevator.close();
        //intake.close();
    }
}
