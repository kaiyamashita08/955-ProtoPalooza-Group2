import org.junit.*;

import static org.mockito.Mockito.*;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.subsystems.Drivebase;

public class DrivebaseTest {
    public static final double DELTA = 0.5;

    CANSparkMax leftLeadMock;
    CANSparkMax leftFollowMock;
    CANSparkMax rightLeadMock;
    CANSparkMax rightFollowMock;
    DifferentialDrive differentialDriveMock;
    DifferentialDrive mockedMotorDrive;
    Drivebase testDrivebase;
    Drivebase mockedMotorTestDrivebase;

    @Before
    public void setup() {
        leftLeadMock = mock(CANSparkMax.class);
        leftFollowMock = mock(CANSparkMax.class);
        rightLeadMock = mock(CANSparkMax.class);
        rightFollowMock = mock(CANSparkMax.class);
        differentialDriveMock = mock(DifferentialDrive.class);
        mockedMotorDrive = new DifferentialDrive(leftLeadMock, rightLeadMock);
        mockedMotorTestDrivebase = new Drivebase(leftLeadMock, rightLeadMock, leftFollowMock, rightFollowMock, mockedMotorDrive);
        testDrivebase = new Drivebase(leftLeadMock, rightLeadMock, leftFollowMock, rightFollowMock, differentialDriveMock);
    }

    @After
    public void shutdown() throws Exception {
        mockedMotorTestDrivebase.close();
        testDrivebase.close();
    }

    @Test
    public void setupTest() {
        // Assert (arrange and act were done in setup())
        verify(leftLeadMock, times(2)).set(0);
        verify(rightLeadMock, times(2)).set(0);
    }

    @Test
    public void driveTestStraight() {
        // Arrange
        reset(leftLeadMock, leftFollowMock, rightLeadMock, rightFollowMock);
        // Act
        mockedMotorTestDrivebase.drive(0.7, 0);
        // Assert
        verify(leftLeadMock).set(doubleThat((Double motor) -> motor > 0));
        verify(rightLeadMock).set(doubleThat((Double motor) -> motor > 0));
    }

    @Test
    public void driveTestTurnInPlace() {
        // Arrange
        reset(leftLeadMock, leftFollowMock, rightLeadMock, rightFollowMock);
        // Act
        mockedMotorTestDrivebase.drive(0, 0.6);
        // Assert
        verify(leftLeadMock).set(doubleThat((Double motor) -> motor > 0));
        verify(rightLeadMock).set(doubleThat((Double motor) -> motor < 0));
    }

    @Test
    public void doesDriveWork() {
        // Arrange
        reset(leftLeadMock,leftFollowMock, rightLeadMock, rightFollowMock, differentialDriveMock);
        // Act
        testDrivebase.drive(0.536, -0.322);
        // Assert
        verify(differentialDriveMock).arcadeDrive(0.536, -0.322 * 0.8);
    }
}
