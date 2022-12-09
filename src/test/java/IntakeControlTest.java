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

    @Test
    public void doesntMoveWithoutLeftTrigger() {
        // Arrange
        reset(mockElevator, mockIntakeXbox, mockIntake);
        when(mockIntakeXbox.getLeftTriggerAxis()).thenReturn(0.2);
        when(mockIntakeXbox.getRightY()).thenReturn(0.8);
        // Act
        testIntakeControl.IntakeTick();
        // Assert
        verify(mockElevator).moveElevator(0);
        verify(mockElevator, never()).moveElevator(0.8);
    }

    @Test
    public void movesProperly() {
        // Arrange
        reset(mockElevator, mockIntakeXbox, mockIntake);
        when(mockIntakeXbox.getLeftTriggerAxis()).thenReturn(0.9);
        when(mockIntakeXbox.getRightY()).thenReturn(0.8);
        // Act
        testIntakeControl.IntakeTick();
        // Assert
        verify(mockElevator, never()).moveElevator(0);
        verify(mockElevator).moveElevator(-0.8);
    }
}
