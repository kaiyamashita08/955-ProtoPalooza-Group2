package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;

public class Intake {
    // 4096 units per rotation for TalonSRX's
    private double gearRatio = 50;
    private PIDController pidController;
    private TalonSRX intakeMotor;
    public Intake(TalonSRX intakeMotor, PIDController pidController) {
        this.intakeMotor = intakeMotor;
        this.pidController = pidController;
        this.pidController.setSetpoint(0);
        this.pidController.reset();
    }

    public void moveIntakeOverride(double percentOutput) {
        intakeMotor.set(ControlMode.PercentOutput, percentOutput);
        pidController.setSetpoint(intakeMotor.getSelectedSensorPosition());
    }

    public void moveIntake() {
        intakeMotor.set(ControlMode.PercentOutput, MathUtil.clamp(pidController.calculate(intakeMotor.getSelectedSensorPosition()), -1, 1));
    }

    public void changeTargetby(double rotations) {
        pidController.setSetpoint(pidController.getSetpoint() + rotations * gearRatio * 4096);
    }
}
