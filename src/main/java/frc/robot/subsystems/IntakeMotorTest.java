package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class IntakeMotorTest {
    TalonSRX motor;
    XboxController xbox;

    public IntakeMotorTest(TalonSRX motor, XboxController xbox) {
        this.motor = motor;
        this.xbox = xbox;
    }

    public void tick() {
        motor.set(ControlMode.PercentOutput, 0.15 * xbox.getRightY());
        SmartDashboard.putNumber("Motor Position", motor.getSelectedSensorPosition());
    }
}
