import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import frc.robot.subsystems.Intake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.math.controller.PIDController;


public class IntakeTest {
    public static final double DELTA = 0.5;
    
    PIDController PIDMock;
    TalonSRX motorMock;
    Intake testIntake;

    @Before
    public void setup() {
        motorMock = mock(TalonSRX.class);
        PIDMock = mock(PIDController.class);
        testIntake = new Intake(motorMock, PIDMock);
    }

    @After
    public void shutdown() throws Exception {
        testIntake.close();
        PIDMock.close();
    }

    @Test
    public void setupTest() {
        // Arrange
        // Act
        // Assert
        verify(PIDMock).setSetpoint(0);
        verify(PIDMock).reset();
    }

    @Test
    public void setStateToTest() {
        // Arrange
        reset(motorMock, PIDMock);
        // Act
        testIntake.setStateTo(true);
        // Assert
        assertEquals(testIntake.isIntakeOpen, true);
        // Act
        testIntake.setStateTo(false);
        // Assert
        assertEquals(testIntake.isIntakeOpen, false);
    }

    @Test
    public void resetPosition() {
        // Arrange
        reset(motorMock, PIDMock);
        when(motorMock.getSelectedSensorPosition()).thenReturn(5000.);
        // Act
        testIntake.resetSetpoint();
        // Assert
        verify(PIDMock).setSetpoint(5000);
        verify(motorMock).set(ControlMode.PercentOutput, 0.);
    }

    @Test
    public void overrideTest() {
        // Arrange
        reset(motorMock, PIDMock);
        when(motorMock.getSelectedSensorPosition()).thenReturn(5000.);
        // Act
        testIntake.moveIntakeOverride(0.7);
        // Assert
        verify(PIDMock).setSetpoint(5000);
        verify(motorMock).set(argThat((ControlMode controlmode) -> controlmode == ControlMode.PercentOutput), doubleThat((Double value) -> Math.abs(value - 0.7 * 0.2) < 0.05));
    }

    @Test
    public void moveCalculationTest() {
        // Arrange
        reset(motorMock, PIDMock);
        when(motorMock.getSelectedSensorPosition()).thenReturn(5000.);
        when(PIDMock.calculate(5000.)).thenReturn(1.2);
        // Act
        testIntake.moveIntake(false);
        // Assert
        verify(motorMock).set(ControlMode.PercentOutput, -0.3);
        // Arrange
        reset(motorMock, PIDMock);
        when(motorMock.getSelectedSensorPosition()).thenReturn(5000.);
        when(PIDMock.calculate(5000.)).thenReturn(-0.2);
        // Act
        testIntake.moveIntake(false);
        // Assert
        verify(motorMock).set(ControlMode.PercentOutput, 0.2);
    }

    @Test
    public void moveWhileOpen() {
        // Arrange
        reset(motorMock, PIDMock);
        when(PIDMock.getSetpoint()).thenReturn(0.);
        testIntake.setStateTo(true);
        // Act
        testIntake.changeState();
        // Assert
        verify(PIDMock).setSetpoint(2048);
    }

    @Test
    public void moveWhileClosed() {
        // Arrange
        reset(motorMock, PIDMock);
        when(PIDMock.getSetpoint()).thenReturn(0.);
        testIntake.setStateTo(false);
        // Act
        testIntake.changeState();
        // Assert
        verify(PIDMock).setSetpoint(-2048);
    }
}
