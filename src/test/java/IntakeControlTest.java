import org.junit.*;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.IntakeControl;

import static org.mockito.Mockito.*;

public class IntakeControlTest {
    public static final double DELTA = 0.5;

    Elevator mockElevator;
    XboxController mockIntakeXbox;
    Intake mockIntake;
    IntakeControl testIntakeControl;

    @Before
    public void setup() {
        mockElevator = mock(Elevator.class);
        mockIntakeXbox = mock(XboxController.class);
        mockIntake = mock(Intake.class);
        testIntakeControl = new IntakeControl(mockIntakeXbox, mockElevator, mockIntake);
    }

    @After
    public void shutdown() throws Exception {
        testIntakeControl.close();
    }
}
