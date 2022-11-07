package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drivebase {
    CANSparkMax leftLeadMotor;
    CANSparkMax rightLeadMotor;
    CANSparkMax leftFollowMotor;
    CANSparkMax rightFollowMotor;
    DifferentialDrive differentialDrive;

    public Drivebase(CANSparkMax leftLeadMotor, CANSparkMax rightLeadMotor,CANSparkMax leftFollowMotor, CANSparkMax rightFollowMotor, DifferentialDrive differentialDrive) {
        this.leftLeadMotor = leftLeadMotor;
        this.rightLeadMotor = rightLeadMotor;
        this.leftFollowMotor = leftFollowMotor;
        this.rightFollowMotor = rightFollowMotor;
        this.differentialDrive  = differentialDrive;
        this.leftLeadMotor.setInverted(false);
        this.rightLeadMotor.setInverted(true);
        this.leftLeadMotor.setSmartCurrentLimit(40);
        this.rightLeadMotor.setSmartCurrentLimit(40);
    }

    public void drive(double speed, double curvature) {
        differentialDrive.arcadeDrive(speed, curvature * 0.8);
    }

    public void displayDriveInfo() {
        SmartDashboard.putNumber("Left Lead Amps", leftLeadMotor.getOutputCurrent());
        SmartDashboard.putNumber("Right Lead Amps", rightLeadMotor.getOutputCurrent());
        SmartDashboard.putNumber("Left Follow Amps", leftFollowMotor.getOutputCurrent());
        SmartDashboard.putNumber("Right Follow Amps", rightFollowMotor.getOutputCurrent());
    }
}
