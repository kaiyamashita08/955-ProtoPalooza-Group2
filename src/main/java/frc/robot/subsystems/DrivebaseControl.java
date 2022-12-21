package frc.robot.subsystems;

import edu.wpi.first.wpilibj.XboxController;

public class DrivebaseControl implements AutoCloseable {
    XboxController drivebaseXbox;
    Drivebase drivebase;
    Vision photonvision;

    public DrivebaseControl(XboxController drivebaseXbox, Drivebase drivebase, Vision photonvision) {
        this.drivebaseXbox = drivebaseXbox;
        this.drivebase = drivebase;
        this.photonvision = photonvision;
    }

    public void DrivebaseTick() {
        if (drivebaseXbox.getAButton()) {
            photonvision.calculate();
            drivebase.drive(0.5 * photonvision.getForwardSpeed(), 0.5 * photonvision.getRotationSpeed());
        } else {
            if (drivebaseXbox.getLeftBumper()) {
                drivebase.drive(0.5 * drivebaseXbox.getLeftY(), -0.5 * drivebaseXbox.getRightX());
            } else {
                drivebase.drive(0.8 * drivebaseXbox.getLeftY(), -0.8 * drivebaseXbox.getRightX());
            }
        }
    }

    @Override
    public void close() throws Exception {
        drivebase.close();
    }
}
