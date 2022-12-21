import org.junit.*;

import static org.mockito.Mockito.*;

import frc.robot.subsystems.DrivebaseControl;
import frc.robot.subsystems.Drivebase;
import frc.robot.subsystems.Vision;

import edu.wpi.first.wpilibj.XboxController;

public class DrivebaseControlTest {
    Drivebase mockDrivebase;
    XboxController mockDriveXbox;
    Vision mockPhotonvision;
    DrivebaseControl testDrivebaseXbox;

    @Before
    public void setup() {
        mockDrivebase = mock(Drivebase.class);
        mockDriveXbox = mock(XboxController.class);
        mockPhotonvision = mock(Vision.class);
        testDrivebaseXbox = new DrivebaseControl(mockDriveXbox, mockDrivebase, mockPhotonvision);
    }

    @After
    public void shutdown() throws Exception {
        mockDrivebase.close();
    }

    @Test
    public void motorTestNormalMovement() {
        // Arrange
        reset(mockDrivebase, mockDriveXbox, mockPhotonvision);
        when(mockDriveXbox.getLeftBumper()).thenReturn(false);
        when(mockDriveXbox.getLeftY()).thenReturn(0.7);
        when(mockDriveXbox.getRightX()).thenReturn(0.4);
        // Act
        testDrivebaseXbox.DrivebaseTick();
        // Assert
        verify(mockDrivebase).drive(doubleThat((Double value) -> value > 0.555 && value < 0.565), doubleThat((Double value) -> value > -0.325 && value < -0.315));
    }

    @Test
    public void motorTestSlowMovement() {
        // Arrange
        reset(mockDrivebase, mockDriveXbox, mockPhotonvision);
        when(mockDriveXbox.getLeftBumper()).thenReturn(true);
        when(mockDriveXbox.getLeftY()).thenReturn(0.7);
        when(mockDriveXbox.getRightX()).thenReturn(0.4);
        // Act
        testDrivebaseXbox.DrivebaseTick();
        // Assert
        verify(mockDrivebase).drive(doubleThat((Double value) -> value > 0.345 && value < 0.355), doubleThat((Double value) -> value > -0.205 && value < -0.195));
    }

    @Test
    public void doesntCalculateWithoutAButton() {
        // Arrange
        reset(mockDrivebase, mockDriveXbox, mockPhotonvision);
        when(mockDriveXbox.getAButton()).thenReturn(false);
        when(mockPhotonvision.getForwardSpeed()).thenReturn(0.8);
        when(mockPhotonvision.getRotationSpeed()).thenReturn(-0.8);
        // Act
        testDrivebaseXbox.DrivebaseTick();
        // Assert
        verify(mockPhotonvision, never()).calculate();
        verify(mockDrivebase, never()).drive(0.4, -0.4);
   }

   @Test
   public void calculatesWhenAButton() {
       // Arrange
       reset(mockDrivebase, mockDriveXbox, mockPhotonvision);
       when(mockDriveXbox.getAButton()).thenReturn(true);
       when(mockPhotonvision.getForwardSpeed()).thenReturn(0.8);
       when(mockPhotonvision.getRotationSpeed()).thenReturn(-0.8);
       // Act
       testDrivebaseXbox.DrivebaseTick();
       // Assert
       verify(mockPhotonvision).calculate();
       verify(mockDrivebase).drive(0.4, -0.4);
  }
}
