package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Setup implements AutoCloseable{
    //Xbox
    XboxController drivebaseXbox;
    XboxController intakeXbox;
    //elevator
    Elevator elevator;
    TalonFX elevatorMotor;
    DigitalInput limitSwitchTop;
    DigitalInput limitSwitchBottom;
    //drivebase
    Drivebase drivebase;
    CANSparkMax leftLeadMotor;
    CANSparkMax rightLeadMotor;
    CANSparkMax leftFollowMotor;
    CANSparkMax rightFollowMotor;
    DifferentialDrive differentialDrive;
    //intake
    Intake intake;
    TalonSRX intakeMotor;
    PIDController intakePID;
    double kP = 0.002;
    double kI = 0.000003;
    double kD = 0.000002;
    // DrivebaseXbox
    DrivebaseControl drivebaseControl;
    IntakeControl intakeControl;

    public void teleopSetup() {
        drivebaseXbox = new XboxController(0);
        intakeXbox = new XboxController(1);
    
        elevatorMotor = new TalonFX(12);
        limitSwitchTop = new DigitalInput(4);
        limitSwitchBottom = new DigitalInput(1);
        elevator = new Elevator(elevatorMotor, limitSwitchTop, limitSwitchBottom);
    
        leftLeadMotor = new CANSparkMax(3, MotorType.kBrushless);
        rightLeadMotor = new CANSparkMax(5, MotorType.kBrushless);
        leftFollowMotor = new CANSparkMax(2, MotorType.kBrushless);
        rightFollowMotor = new CANSparkMax(6, MotorType.kBrushless);
        differentialDrive = new DifferentialDrive(leftLeadMotor, rightLeadMotor);
    
        rightLeadMotor.setInverted(false);
        leftLeadMotor.setInverted(true);
        rightLeadMotor.setSmartCurrentLimit(40);
        leftLeadMotor.setSmartCurrentLimit(40);
        leftLeadMotor.setIdleMode(IdleMode.kCoast);
        leftFollowMotor.setIdleMode(IdleMode.kCoast);
        rightLeadMotor.setIdleMode(IdleMode.kCoast);
        rightFollowMotor.setIdleMode(IdleMode.kCoast);
        leftFollowMotor.follow(leftLeadMotor);
        rightFollowMotor.follow(rightLeadMotor);
        drivebase = new Drivebase(leftLeadMotor, rightLeadMotor, leftFollowMotor, rightFollowMotor, differentialDrive);
        
        intakeMotor = new TalonSRX(0);//need terminal
        intakePID = new PIDController(kP, kI, kD);
        intake = new Intake(intakeMotor, intakePID);

        drivebaseControl = new DrivebaseControl(drivebaseXbox, drivebase);
        intakeControl = new IntakeControl(intakeXbox, elevator, intake);
    }
    
    public DrivebaseControl getDrivebaseXbox() {
        return drivebaseControl;
    }

    public IntakeControl getIntakeXbox() {
        return intakeControl;
    }
    
    @Override
    public void close() throws Exception {
        limitSwitchTop.close();
        limitSwitchBottom.close();
        elevator.close();
        leftLeadMotor.close();
        leftFollowMotor.close();
        rightLeadMotor.close();
        rightFollowMotor.close();
        differentialDrive.close();
        drivebase.close();
        intakePID.close();
        // intake.close();
        // drivebaseControl.close();  (uncomment when we can close these)
        // intakeControl.close();
    }
}