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
            elevator.moveElevator(-intakeXbox.getRightY());
        } else {
            elevator.moveElevator(0);
        }
        if (intakeXbox.getXButton()) {
            intake.moveIntakeOverride(-intakeXbox.getLeftY());
        } //else {
        //     if (intakeXbox.getRightBumperPressed()) {
        //         intake.changeState();
        //     }
        //     intake.moveIntake(intakeXbox.getAButton());
        // }
        if (intakeXbox.getYButton()) {
            intake.setStateTo(true);
            //intake.resetSetpoint();
        }
    }

    public void close() throws Exception {
        elevator.close();
        intake.close();
    }
}
