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
}
