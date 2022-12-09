package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class IntakeMotorTest {
    TalonSRX motor;
    XboxController xbox;
    double kP = 0.002;
    double kI = 0.000000;
    double kD = 0.00000;
    PIDController intakePID;
    private double gearRatio = 1;
    private boolean isIntakeOpen = true;
    public double rotationsToClose = 0.5;

    public IntakeMotorTest(TalonSRX motor, XboxController xbox) {
        this.motor = motor;
        this.xbox = xbox;
        intakePID = new PIDController(kP, kI, kD);
        motor.setSelectedSensorPosition(0);
        intakePID.setSetpoint(0);
    }

    public void setStateTo(boolean isOpen) {
        isIntakeOpen = isOpen;
    }

    public void resetSetpoint() {
        intakePID.setSetpoint(motor.getSelectedSensorPosition());
        motor.set(ControlMode.PercentOutput, 0);
    }

    public void moveIntakeOverride(double percentOutput) {
        motor.set(ControlMode.PercentOutput, percentOutput);
        intakePID.setSetpoint(motor.getSelectedSensorPosition());
    }

    public void moveIntake() {
        motor.set(ControlMode.PercentOutput, MathUtil.clamp(intakePID.calculate(motor.getSelectedSensorPosition()), -1, 1));
    }

    public void changeState() {
        if (isIntakeOpen == true) {
            intakePID.setSetpoint(intakePID.getSetpoint() + rotationsToClose * gearRatio * 4096);
            isIntakeOpen = false;
        } else {
            intakePID.setSetpoint(intakePID.getSetpoint() - rotationsToClose * gearRatio * 4096);
            isIntakeOpen = true;
        }
    }

    public void tick() {
        if (xbox.getRightBumperPressed()) {
            changeState();
        }
        if (xbox.getXButton()) {
            moveIntakeOverride(-xbox.getLeftY());
        } else {
            moveIntake();
        }
        if (xbox.getYButton()) {
            setStateTo(true);
            resetSetpoint();
        }
        SmartDashboard.putNumber("Motor Position", motor.getSelectedSensorPosition());
        SmartDashboard.putNumber("Setpoint", intakePID.getSetpoint());
        SmartDashboard.putBoolean("Is intake open", isIntakeOpen);
    }
}
