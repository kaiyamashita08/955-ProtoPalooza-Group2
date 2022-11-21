import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import frc.robot.subsystems.Elevator;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj.DigitalInput;

public class ElevatorTest {
    public static final double DELTA = 0.5;
    
    TalonFX elevatorMock;
    DigitalInput topLimitMock;
    DigitalInput bottomLimitMock;
    Elevator testElevator;

    @Before
    public void setup() {
        elevatorMock = mock(TalonFX.class);
        topLimitMock = mock(DigitalInput.class);
        bottomLimitMock = mock(DigitalInput.class);
        testElevator = new Elevator(elevatorMock, topLimitMock, bottomLimitMock);
    }

    @After
    public void shutdown() throws Exception {
        testElevator.close();
    }

    @Test
    public void setupTest() {
        verify(elevatorMock).setNeutralMode(NeutralMode.Brake);
    }
}
