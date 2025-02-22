import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class FireIncidentSubsystemTest {

    private Scheduler testScheduler;
    private FireIncidentSubsystem fireIncidentSubsystem;
    private DroneSubsystem droneSubsystem;

    @BeforeEach
    void setUp() {
        testScheduler = new Scheduler();
        fireIncidentSubsystem = new FireIncidentSubsystem(testScheduler);
        droneSubsystem = new DroneSubsystem(testScheduler);
    }

    @Test
    void testFireIncidentSubsystemReadsAndSendsRequests() {
        Thread fireIncidentThread = new Thread(fireIncidentSubsystem);
        Thread droneSubsystemThread = new Thread(droneSubsystem);
        fireIncidentThread.start();
        droneSubsystemThread.start();

        // Wait for the thread to complete execution
        try {
            fireIncidentThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Verify that requests are processed
        assertTrue(testScheduler.getRequestsCompleted() > 0, "Scheduler should have processed at least one request");
    }
}
