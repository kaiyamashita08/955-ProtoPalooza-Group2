package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;

public class Intake implements AutoCloseable {
    // 4096 units per rotation for TalonSRX's
    private double gearRatio = 60;
    private PIDController pidController;
    private TalonSRX intakeMotor;
    public boolean isIntakeOpen;
    public double rotationsToClose = 0.5;

    public Intake(TalonSRX intakeMotor, PIDController pidController) {
        isIntakeOpen = true;
        this.intakeMotor = intakeMotor;
        this.pidController = pidController;
        this.pidController.setSetpoint(0);
        this.pidController.reset();
    }

    public void setStateTo(boolean isOpen) {
        isIntakeOpen = isOpen;
    }

    public void resetSetpoint() {
        pidController.setSetpoint(intakeMotor.getSelectedSensorPosition());
        intakeMotor.set(ControlMode.PercentOutput, 0);
    }

    public void moveIntakeOverride(double percentOutput) {
        intakeMotor.set(ControlMode.PercentOutput, percentOutput);
        pidController.setSetpoint(intakeMotor.getSelectedSensorPosition());
    }

    public void moveIntake() {
        intakeMotor.set(ControlMode.PercentOutput, MathUtil.clamp(pidController.calculate(intakeMotor.getSelectedSensorPosition()), -1, 1));
    }

    public void changeState() {
        if (isIntakeOpen == true) {
            pidController.setSetpoint(pidController.getSetpoint() + rotationsToClose * gearRatio * 4096);
            isIntakeOpen = false;
        } else {
            pidController.setSetpoint(pidController.getSetpoint() - rotationsToClose * gearRatio * 4096);
            isIntakeOpen = true;
        }
    }

    public void close() throws Exception {
        pidController.close();
    }
}
