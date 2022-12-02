package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DisplayControl implements AutoCloseable{
    //elevator
    TalonFX elevatorMotor;
    DigitalInput limitSwitchTop;
    DigitalInput limitSwitchBottom;
    //drivebase
    CANSparkMax leftLeadMotor;
    CANSparkMax rightLeadMotor;
    CANSparkMax leftFollowMotor;
    CANSparkMax rightFollowMotor;
    // intake
    TalonSRX intakeMotor;
    
    public DisplayControl(TalonFX elevatorMotor, DigitalInput limitSwitchTop, DigitalInput limitSwitchBottom, CANSparkMax leftLeadMotor, CANSparkMax rightLeadMotor, CANSparkMax leftFollowMotor, CANSparkMax rightFollowMotor, TalonSRX intakeMotor) {
        this.elevatorMotor = elevatorMotor;
        this.limitSwitchTop = limitSwitchTop;
        this.limitSwitchBottom = limitSwitchBottom;
        this.leftLeadMotor = leftLeadMotor;
        this.rightLeadMotor = rightLeadMotor;
        this.leftFollowMotor = leftFollowMotor;
        this.rightFollowMotor = rightFollowMotor;
        this.intakeMotor = intakeMotor;
    }

    public void DisplayInfo() {
        // Drivebase
        SmartDashboard.putNumber("Left Lead Amps", leftLeadMotor.getOutputCurrent());
        SmartDashboard.putNumber("Right Lead Amps", rightLeadMotor.getOutputCurrent());
        SmartDashboard.putNumber("Left Follow Amps", leftFollowMotor.getOutputCurrent());
        SmartDashboard.putNumber("Right Follow Amps", rightFollowMotor.getOutputCurrent());

        // Elevator
        SmartDashboard.putBoolean("Elevator Top Limit Hit", limitSwitchTop.get());
        SmartDashboard.putBoolean("Elevator Bottom Limit Hit", limitSwitchBottom.get());
        SmartDashboard.putNumber("Elevator Amp", elevatorMotor.getStatorCurrent());
        SmartDashboard.putNumber("Elevator Position", elevatorMotor.getSelectedSensorPosition());

        // Intake
        SmartDashboard.putNumber("Intake Position", intakeMotor.getSelectedSensorPosition());
    }

    public void close() throws Exception {
        leftLeadMotor.close();
        rightLeadMotor.close();
        leftFollowMotor.close();
        rightFollowMotor.close();
        limitSwitchTop.close();
        limitSwitchBottom.close();
    }
}
