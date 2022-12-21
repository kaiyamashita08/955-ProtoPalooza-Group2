package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.photonvision.PhotonCamera;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
    double kI = 0.00000;
    double kD = 0.00000;
    // DrivebaseXbox
    DrivebaseControl drivebaseControl;
    IntakeControl intakeControl;
    // Limelight
    PhotonCamera camera;
    Vision photonvision;

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
        drivebase = new Drivebase(differentialDrive);
        
        intakeMotor = new TalonSRX(9);//need terminal
        intakeMotor.configPeakCurrentLimit(40);
        intakeMotor.enableCurrentLimit(true);
        intakePID = new PIDController(kP, kI, kD);
        intake = new Intake(intakeMotor, intakePID);

        camera = new PhotonCamera("photonvision");
        photonvision = new Vision(camera);

        drivebaseControl = new DrivebaseControl(drivebaseXbox, drivebase, photonvision);
        intakeControl = new IntakeControl(intakeXbox, elevator, intake);
    }
    
    public DrivebaseControl getDrivebaseControl() {
        return drivebaseControl;
    }

    public IntakeControl getIntakeControl() {
        return intakeControl;
    }

    public void displayInfo() {
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
        SmartDashboard.putNumber("Setpoint", intakePID.getSetpoint());
        SmartDashboard.putBoolean("Is intake open", intake.isIntakeOpen);
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
        intake.close();
        drivebaseControl.close();
        intakeControl.close();
    }
}