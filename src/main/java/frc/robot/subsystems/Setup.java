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
    XboxController driveXboxController;
    XboxController intakeXboxController;
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
    DrivebaseXbox drivebaseXbox;
    IntakeXbox intakeXbox;

    public void teleopSetup() {
        driveXboxController = new XboxController(0);
        intakeXboxController = new XboxController(1);
    
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

        drivebaseXbox = new DrivebaseXbox(driveXboxController, drivebase);
        intakeXbox = new IntakeXbox(intakeXboxController, elevator, intake);
    }
    
    public DrivebaseXbox getDrivebaseXbox() {
        return drivebaseXbox;
    }

    public IntakeXbox getIntakeXbox() {
        return intakeXbox;
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
        // drivebaseXbox.close();  (uncomment when we can close these)
        // intakeXbox.close();
    }
}