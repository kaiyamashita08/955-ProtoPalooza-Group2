package frc.robot.subsystems;

import edu.wpi.first.wpilibj.XboxController;

public class DrivebaseControl implements AutoCloseable {
    XboxController drivebaseXbox;
    Drivebase drivebase;

    public DrivebaseControl(XboxController drivebaseXbox, Drivebase drivebase) {
        this.drivebaseXbox = drivebaseXbox;
        this.drivebase = drivebase;
    }

    public void DrivebaseTick() {
        if (drivebaseXbox.getLeftBumper()) {
            drivebase.drive(0.5 * drivebaseXbox.getLeftY(), -0.5 * drivebaseXbox.getRightX());
        } else {
            drivebase.drive(0.8 * drivebaseXbox.getLeftY(), -0.5 * drivebaseXbox.getRightX());
        }
    }

    @Override
    public void close() throws Exception {
        drivebase.close();
    }
}
