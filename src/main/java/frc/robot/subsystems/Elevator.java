package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;

public class Elevator {
    TalonFX motor;

    DigitalInput bottomLimitSensor;
    DigitalInput topLimitSensor;

    PIDController pid;
    double kP = 0.003;
    double kI = 0.00004;
    double kD = 0.000000053;

    boolean isElevatorReset = false;
    boolean elevatorDriveOverride = false;
    double setpoint = 0;

    public int upperLevelTicks = 100000; // Currently not determined yet

    public Elevator(int motorPort, int bottomLimitPort, int topLimitPort) {
        motor = new TalonFX(motorPort);
        motor.setSelectedSensorPosition(0);
        bottomLimitSensor = new DigitalInput(bottomLimitPort);
        topLimitSensor = new DigitalInput(topLimitPort);
        pid = new PIDController(kP, kI, kD);
    }

    public boolean resetElevator() {
        if (motor.getStatorCurrent() < 40) { // Check if its getStatorCurrent or getSupplyCurrent with electrical before running
            if (!bottomLimitSensor.get() && motor.getSelectedSensorPosition() < 20000) {
                motor.set(TalonFXControlMode.PercentOutput, 0.5);
                isElevatorReset = false;
                return false;
            } else if (!bottomLimitSensor.get() && motor.getSelectedSensorPosition() > 20000) {
                motor.setSelectedSensorPosition(30000);
                motor.set(TalonFXControlMode.PercentOutput, -0.5);
                isElevatorReset = false;
                return false;
            } else {
                motor.set(TalonFXControlMode.PercentOutput, 0);
                motor.setSelectedSensorPosition(0);
                isElevatorReset = true;
                return true;
            }
        } else {
            motor.set(TalonFXControlMode.PercentOutput, 0);
            isElevatorReset = true;
            return true;
        }
    }

    public void setOverride(boolean override) {
        elevatorDriveOverride = override;
    }

    public boolean getOverride() {
        return elevatorDriveOverride;
    }

    public void moveElevator() {
        if (motor.getStatorCurrent() < 40) {
            if (isElevatorReset == true || elevatorDriveOverride == true) {
                motor.set(TalonFXControlMode.PercentOutput, MathUtil.clamp(pid.calculate(setpoint - motor.getSelectedSensorPosition()), -1, 1));
            }
        }
    }

    public void changeTargetTo(double target) {
        setpoint = target;
    }

    public void changeTargetBy(double change) {
        setpoint = setpoint + change;
    }
}
