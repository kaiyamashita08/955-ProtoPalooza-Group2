import org.junit.*;

import static org.mockito.Mockito.*;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.subsystems.Drivebase;

public class DrivebaseTest {
    public static final double DELTA = 0.5;

    CANSparkMax leftLeadMock;
    CANSparkMax leftFollowMock;
    CANSparkMax rightLeadMock;
    CANSparkMax rightFollowMock;
    DifferentialDrive differentialDriveMock;
    Drivebase testDrivebase;

    @Before
    public void setup() {
        leftLeadMock = mock(CANSparkMax.class);
        leftFollowMock = mock(CANSparkMax.class);
        rightLeadMock = mock(CANSparkMax.class);
        rightFollowMock = mock(CANSparkMax.class);
        differentialDriveMock = new DifferentialDrive(leftLeadMock, rightLeadMock);
        testDrivebase = new Drivebase(leftLeadMock, rightLeadMock, leftFollowMock, rightFollowMock, differentialDriveMock);
    }

    @After
    public void shutdown() throws Exception {
        testDrivebase.close();
    }

    @Test
    public void setupTest() {
        // Assert (arrange and act were done in setup())
        verify(leftFollowMock).follow(leftLeadMock);
        verify(rightFollowMock).follow(rightLeadMock);
        verify(leftLeadMock).setInverted(false);
        verify(rightLeadMock).setInverted(true);
        verify(leftLeadMock).setSmartCurrentLimit(40);
        verify(rightLeadMock).setSmartCurrentLimit(40);
        verify(leftLeadMock).setIdleMode(IdleMode.kCoast);
        verify(leftFollowMock).setIdleMode(IdleMode.kCoast);
        verify(rightLeadMock).setIdleMode(IdleMode.kCoast);
        verify(rightFollowMock).setIdleMode(IdleMode.kCoast);
    }

    @Test
    public void driveTestStraight() {
        // Arrange
        reset(leftLeadMock, leftFollowMock, rightLeadMock, rightFollowMock);
        // Act
        testDrivebase.drive(0.7, 0);
        // Assert
        verify(leftLeadMock).set(doubleThat((Double motor) -> motor > 0));
        verify(rightLeadMock).set(doubleThat((Double motor) -> motor > 0));
    }

    @Test
    public void driveTestTurnInPlace() {
        // Arrange
        reset(leftLeadMock, leftFollowMock, rightLeadMock, rightFollowMock);
        // Act
        testDrivebase.drive(0, 0.6);
        // Assert
        verify(leftLeadMock).set(doubleThat((Double motor) -> motor > 0));
        verify(rightLeadMock).set(doubleThat((Double motor) -> motor < 0));
    }
}
