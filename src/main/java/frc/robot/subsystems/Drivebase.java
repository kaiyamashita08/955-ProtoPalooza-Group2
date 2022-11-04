package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

public class Drivebase {
    CANSparkMax leftFrontCAN = new CANSparkMax(3, MotorType.kBrushless);
    CANSparkMax leftBackCAN = new CANSparkMax(2, MotorType.kBrushless);
    CANSparkMax rightFrontCAN = new CANSparkMax(5, MotorType.kBrushless);
    CANSparkMax rightBackCAN = new CANSparkMax(6, MotorType.kBrushless);

    MotorControllerGroup m_left = new MotorControllerGroup(leftBackCAN, leftFrontCAN);
    MotorControllerGroup m_right = new MotorControllerGroup(rightBackCAN, rightFrontCAN);
 
    DifferentialDrive m_drive = new DifferentialDrive(m_left, m_right);

    public void drive(double xSpeed, double zRotation) {
        if (xSpeed >= 0.05 || xSpeed <= -0.05) {
            m_drive.curvatureDrive(xSpeed, zRotation, true);
        } else {
            m_drive.curvatureDrive(xSpeed, zRotation, false);
        }
    }
}
