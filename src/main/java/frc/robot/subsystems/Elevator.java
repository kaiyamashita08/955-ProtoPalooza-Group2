package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator {
    private TalonFX elevatorMotor;
    private  DigitalInput limitSwitchTop;
    private  DigitalInput limitSwitchBottom;

    //note:  the inputs into this function are motors, digital inputs and doublesolenoids, not integers for the ports
    public Elevator(TalonFX elevatorMotor, DigitalInput limitSwitchTop, DigitalInput limitSwitchBottom) {
        this.elevatorMotor = elevatorMotor;
        this.limitSwitchTop = limitSwitchTop;
        this.limitSwitchBottom = limitSwitchBottom;
        this.elevatorMotor.setNeutralMode(NeutralMode.Brake);
        this.elevatorMotor.setSelectedSensorPosition(0);
        SupplyCurrentLimitConfiguration currentLimitConfiguration = new SupplyCurrentLimitConfiguration(true, 40, 40, 0);
        this.elevatorMotor.configSupplyCurrentLimit(currentLimitConfiguration);
    }

    public void moveElevator(double joystickPosition) {
        joystickPosition = -joystickPosition;
        if (limitSwitchBottom.get() == false) {
            resetPosition();
        }
        if (joystickPosition < 0 && elevatorMotor.getSelectedSensorPosition() <= 5000) {
            elevatorMotor.set(ControlMode.PercentOutput, 0);
        } else if (joystickPosition > 0 && limitSwitchTop.get() == true) {
            elevatorMotor.setSelectedSensorPosition(300000);
            elevatorMotor.set(ControlMode.PercentOutput, 0);
        } else {
            elevatorMotor.set(ControlMode.PercentOutput, joystickPosition);
        }
    }

    public void resetPosition() {
        elevatorMotor.setSelectedSensorPosition(0);
    }

    public void displayElevatorInfo() {
        SmartDashboard.putBoolean("Elevator Top Limit Hit", limitSwitchTop.get());
        SmartDashboard.putBoolean("Elevator Bottom Limit Hit", limitSwitchBottom.get());
        SmartDashboard.putNumber("Elevator Amp", elevatorMotor.getStatorCurrent());
        SmartDashboard.putNumber("Elevator Position", elevatorMotor.getSelectedSensorPosition());
    }
}
