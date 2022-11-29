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
            drivebase.drive(0.4 * drivebaseXbox.getLeftY(), 0.4 * drivebaseXbox.getRightX());
        } else {
            drivebase.drive(drivebaseXbox.getLeftY(), drivebaseXbox.getRightX());
        }
    }

    @Override
    public void close() throws Exception {
        drivebase.close();
    }
}
