package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Drivebase implements AutoCloseable {
    DifferentialDrive differentialDrive;

    public Drivebase(DifferentialDrive differentialDrive) {
        this.differentialDrive  = differentialDrive;
        this.differentialDrive.arcadeDrive(0, 0);
    }

    public void drive(double speed, double curvature) {
        differentialDrive.arcadeDrive(speed, curvature * 0.8);
    }

    @Override
    public void close() throws Exception {
        differentialDrive.close();
    }
}
