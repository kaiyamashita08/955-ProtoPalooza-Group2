package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator {
    private TalonFX elevatorMotor;
    private  DigitalInput limitSwitchTop;
    private  DigitalInput limitSwitchBottom;
    private DoubleSolenoid elevatorSoleniodLock;

    //note:  the inputs into this function are motors, digital inputs and doublesolenoids, not integers for the ports
    public Elevator(TalonFX elevatorMotor, DigitalInput limitSwitchTop, DigitalInput limitSwitchBottom, DoubleSolenoid elevatorSoleniodLock) {
        this.elevatorMotor = elevatorMotor;
        this.limitSwitchTop = limitSwitchTop;
        this.limitSwitchBottom = limitSwitchBottom;
        this.elevatorSoleniodLock = elevatorSoleniodLock;
        this.elevatorMotor.setNeutralMode(NeutralMode.Brake);
        this.elevatorMotor.setSelectedSensorPosition(0);
        SupplyCurrentLimitConfiguration currentLimitConfiguration = new SupplyCurrentLimitConfiguration(true, 40, 40, 0);
        this.elevatorMotor.configSupplyCurrentLimit(currentLimitConfiguration);
        this.elevatorMotor.setInverted(true);
    }

    public void moveElevator(double joystickPosition) {
        if (limitSwitchBottom.get() == false) {
            resetPosition();
        }
        if (elevatorSoleniodLock.get() == DoubleSolenoid.Value.kForward) {
            elevatorMotor.set(ControlMode.PercentOutput, 0);
        } else {
            if (joystickPosition < 0 && elevatorMotor.getSelectedSensorPosition() <= 5000) {
                elevatorMotor.set(ControlMode.PercentOutput, 0);
            } else if (joystickPosition > 0 && limitSwitchTop.get() == true) {
                elevatorMotor.setSelectedSensorPosition(300000);
                elevatorMotor.set(ControlMode.PercentOutput, 0);
            } else {
                elevatorMotor.set(ControlMode.PercentOutput, joystickPosition);
            }
        }
    }

    public void resetPosition() {
        elevatorMotor.setSelectedSensorPosition(0);
    }

    public void lockElevator() {
        if (elevatorMotor.getSelectedSensorVelocity() < 10000) {
            elevatorSoleniodLock.set(DoubleSolenoid.Value.kForward);
        }
        elevatorMotor.set(ControlMode.PercentOutput, 0);
    }

    public void unlockElevator() {
        elevatorSoleniodLock.set(DoubleSolenoid.Value.kReverse);
        elevatorMotor.set(ControlMode.PercentOutput, 0);
    }

    public void displayElevatorInfo() {
        SmartDashboard.putBoolean("Elevator Top Limit Hit", limitSwitchTop.get());
        SmartDashboard.putBoolean("Elevator Bottom Limit Hit", limitSwitchBottom.get());
        SmartDashboard.putNumber("Elevator Amp", elevatorMotor.getStatorCurrent());
        SmartDashboard.putNumber("Elevator Position", elevatorMotor.getSelectedSensorPosition());
        SmartDashboard.putBoolean("Elevator Solenoid Extended (reversed)", getElevatorLockSolenoid());
    }

    private boolean getElevatorLockSolenoid() {
        if (elevatorSoleniodLock.get() == Value.kForward) {
            return true;
        } else {
            return false;
        }
    }
}
