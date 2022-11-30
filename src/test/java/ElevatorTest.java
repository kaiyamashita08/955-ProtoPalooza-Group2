import org.junit.*;

import static org.mockito.Mockito.*;
import frc.robot.subsystems.Elevator;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj.DigitalInput;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;

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
        // Arrange
        SupplyCurrentLimitConfiguration currentLimitConfiguration = new SupplyCurrentLimitConfiguration(true, 40, 40, 0);
        // Act
        // Assert
        verify(elevatorMock).setNeutralMode(NeutralMode.Brake);
        verify(elevatorMock).configSupplyCurrentLimit(argThat((SupplyCurrentLimitConfiguration configuration) -> configuration.equals(currentLimitConfiguration)));
        verify(elevatorMock).setSelectedSensorPosition(0);
    }

    @Test
    public void resetPositionTest() {
        // Arrange
        reset(elevatorMock, topLimitMock, bottomLimitMock);
        // Act
        testElevator.resetPosition();
        // Assert
        verify(elevatorMock).setSelectedSensorPosition(0);
    }

    @Test
    public void canMoveNormally() {
        // Arrange
        reset(elevatorMock, topLimitMock, bottomLimitMock);
        when(topLimitMock.get()).thenReturn(false);
        when(elevatorMock.getSelectedSensorPosition()).thenReturn(10000.);
        // Act
        testElevator.moveElevator(0.5);
        // Assert
        verify(elevatorMock).set(ControlMode.PercentOutput, 0.5);
    }

    @Test
    public void moveUpAtBottom() {
        // Arrange
        reset(elevatorMock, topLimitMock, bottomLimitMock);
        when(elevatorMock.getSelectedSensorPosition()).thenReturn(150.);
        // Act
        testElevator.moveElevator(0.3);
        // Assert
        verify(elevatorMock, never()).set(ControlMode.PercentOutput, 0);
        verify(elevatorMock).set(ControlMode.PercentOutput, 0.3);
    }

    @Test
    public void moveDownAtTop() {
        // Arrange
        reset(elevatorMock, topLimitMock, bottomLimitMock);
        when(topLimitMock.get()).thenReturn(true);
        when(elevatorMock.getSelectedSensorPosition()).thenReturn(220000.);
        // Act
        testElevator.moveElevator(-0.3);
        // Assert
        verify(elevatorMock, never()).set(ControlMode.PercentOutput, 0);
        verify(elevatorMock).set(ControlMode.PercentOutput, -0.3);
    }

    @Test
    public void cantMoveDownAtBottom() {
        // Arrange
        reset(elevatorMock, topLimitMock, bottomLimitMock);
        when(elevatorMock.getSelectedSensorPosition()).thenReturn(150.);
        // Act
        testElevator.moveElevator(-0.3); // controls reversed as of now
        // Assert
        verify(elevatorMock).set(ControlMode.PercentOutput, 0);
        verify(elevatorMock, never()).set(ControlMode.PercentOutput, -0.3);
    }

    @Test
    public void cantMoveUpAtTop() {
        // Arrange
        reset(elevatorMock, topLimitMock, bottomLimitMock);
        when(topLimitMock.get()).thenReturn(true);
        // Act
        testElevator.moveElevator(-0.3);
        // Assert
        verify(elevatorMock).set(ControlMode.PercentOutput, 0);
        verify(elevatorMock, never()).set(ControlMode.PercentOutput, -0.3);
    }
}
