package frc.robot.subsystems;

import edu.wpi.first.wpilibj.XboxController;

public class DrivebaseXbox implements AutoCloseable {
    XboxController driveXboxController;
    Drivebase drivebase;

    public DrivebaseXbox(XboxController driveXboxController, Drivebase drivebase) {
        this.driveXboxController = driveXboxController;
        this.drivebase = drivebase;
    }

    public void DrivebaseTick() {
        if (driveXboxController.getLeftBumper()) {
            drivebase.drive(0.4 * driveXboxController.getLeftY(), 0.4 * driveXboxController.getRightX());
        } else {
            drivebase.drive(driveXboxController.getLeftY(), driveXboxController.getRightX());
        }
        drivebase.displayDriveInfo();
    }

    @Override
    public void close() throws Exception {
        drivebase.close();
    }
}
