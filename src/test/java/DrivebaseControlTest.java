import org.junit.*;

import static org.mockito.Mockito.*;

import frc.robot.subsystems.DrivebaseControl;
import frc.robot.subsystems.Drivebase;

import edu.wpi.first.wpilibj.XboxController;

public class DrivebaseControlTest {
    Drivebase mockDrivebase;
    XboxController mockDriveXbox;
    DrivebaseControl testDrivebaseXbox;

    @Before
    public void setup() {
        mockDrivebase = mock(Drivebase.class);
        mockDriveXbox = mock(XboxController.class);
        testDrivebaseXbox = new DrivebaseControl(mockDriveXbox, mockDrivebase);
    }

    @After
    public void shutdown() throws Exception {
        mockDrivebase.close();
    }

    @Test
    public void motorTestNormalMovement() {
        // Arrange
        reset(mockDrivebase, mockDriveXbox);
        when(mockDriveXbox.getLeftBumper()).thenReturn(false);
        when(mockDriveXbox.getLeftY()).thenReturn(0.7);
        when(mockDriveXbox.getRightX()).thenReturn(0.4);
        // Act
        testDrivebaseXbox.DrivebaseTick();
        // Assert
        verify(mockDrivebase).drive(0.7, -0.4);
    }

    @Test
    public void motorTestSlowMovement() {
        // Arrange
        reset(mockDrivebase, mockDriveXbox);
        when(mockDriveXbox.getLeftBumper()).thenReturn(true);
        when(mockDriveXbox.getLeftY()).thenReturn(0.7);
        when(mockDriveXbox.getRightX()).thenReturn(0.4);
        // Act
        testDrivebaseXbox.DrivebaseTick();
        // Assert
        verify(mockDrivebase).drive(doubleThat((Double value) -> value > 0.275 && value < 0.285), doubleThat((Double value) -> value > -0.165 && value < -0.155));
    }
}
