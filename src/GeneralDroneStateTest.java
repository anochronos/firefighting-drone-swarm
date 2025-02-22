import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GeneralDroneStateTest {

    private DroneSubsystem droneSubsystem;
    private Drone drone;

    @BeforeAll
    void setUp() {
        droneSubsystem = new DroneSubsystem(new Scheduler());
        drone = new Drone(1, 100, 10.0);
        droneSubsystem.addDrone(drone);
    }

    @Test
    void testIdleState() {
        drone.setState(new IdleState());
        assertTrue(drone.getState() instanceof IdleState, "Drone should be in IdleState.");
    }

    @Test
    void testEnRouteState() {
        drone.setState(new EnRouteState());
        drone.getState().realTimeUpdateOfDroneLocation(droneSubsystem, drone);
        assertTrue(drone.getState() instanceof EnRouteState, "Drone should be in EnRouteState.");
    }

    @Test
    void testApproachingDestinationState() {
        drone.setState(new ApproachingDestinationState());
        drone.getState().realTimeUpdateOfDroneLocation(droneSubsystem, drone);
        assertTrue(drone.getState() instanceof ApproachingDestinationState, "Drone should be in ApproachingDestinationState.");
    }

    @Test
    void testDroppingAgentState() {
        drone.setState(new DroppingAgentState());
        assertTrue(drone.getState() instanceof DroppingAgentState, "Drone should be in DroppingAgentState.");
    }

    @Test
    void testClosingNozzleState() {
        drone.setState(new ClosingNozzleState());
        assertTrue(drone.getState() instanceof ClosingNozzleState, "Drone should transition to ReturningToBaseState after closing nozzle.");
    }

    @Test
    void testReturningToBaseState() {
        drone.setState(new ReturningToBaseState());
        assertTrue(drone.getState() instanceof ReturningToBaseState, "Drone should be in ReturningToBaseState.");
    }

    @Test
    void testFaultDetectedState() {
        drone.setState(new FaultDetectedState());
        assertTrue(drone.getState() instanceof FaultDetectedState, "Drone should transition to ReturningToBaseState after detecting a fault.");
    }

    @AfterAll
    void tearDown() {
        droneSubsystem = null;
        drone = null;
    }
}