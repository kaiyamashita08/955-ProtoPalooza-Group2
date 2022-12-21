package frc.robot.subsystems;
 
import org.photonvision.PhotonCamera;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Vision {
    // Change this to match the name of your camera
    PhotonCamera camera = new PhotonCamera("ballcam");

    final double ANGULAR_P = 0.5;
    final double LINEAR_P = 0.5;
    double forwardSpeed;
    double rotationSpeed;

    public Vision(PhotonCamera camera) {
        this.camera = camera;
    }

    public double getForwardSpeed() {
        System.out.println(forwardSpeed);
        return forwardSpeed;
    }

    public double getRotationSpeed() {
        System.out.println(rotationSpeed);
        return rotationSpeed;
    }

    public void calculate() {
        // Vision-alignment mode
        // Query the latest result from PhotonVision
        var result = camera.getLatestResult();

        if (result.hasTargets()) {
            // Calculate angular turn power
            // -1.0 required to ensure positive PID controller effort _increases_ yaw
            rotationSpeed = -MathUtil.clamp(ANGULAR_P * result.getBestTarget().getYaw(), -1, 1);
            forwardSpeed = MathUtil.clamp(LINEAR_P * result.getBestTarget().getPitch(), -1, 1);
        } else {
            // If we have no targets, stay still.
            rotationSpeed = 0;
            forwardSpeed = 0;
        }
        SmartDashboard.putNumber("rotation speed", rotationSpeed);
        SmartDashboard.putNumber("forward speed", forwardSpeed);
        SmartDashboard.putBoolean("has targets", result.hasTargets());
    }
}
