import org.junit.*;

import static org.mockito.Mockito.*;

import frc.robot.subsystems.DrivebaseXbox;
import frc.robot.subsystems.Drivebase;

import edu.wpi.first.wpilibj.XboxController;

public class DrivebaseXboxTest {
    Drivebase mockDrivebase;
    XboxController mockDriveXbox;
    DrivebaseXbox testDrivebaseXbox;

    @Before
    public void setup() {
        mockDrivebase = mock(Drivebase.class);
        mockDriveXbox = mock(XboxController.class);
        testDrivebaseXbox = new DrivebaseXbox(mockDriveXbox, mockDrivebase);
    }

    @After
    public void shutdown() throws Exception {
        mockDrivebase.close();
    }
}
