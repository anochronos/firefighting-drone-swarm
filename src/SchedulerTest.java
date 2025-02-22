import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class SchedulerTest {
    private Scheduler scheduler;
    private FireIncidentTicket ticket1;
    private FireIncidentTicket ticket2;
    private HashMap<Integer, Zone> zones;

    @BeforeEach
    void setUp() {
        scheduler = new Scheduler();
        ticket1 = new FireIncidentTicket("2024-02-07T12:00:00", 1, "Fire", "High");
        ticket2 = new FireIncidentTicket("2024-02-07T12:30:00", 2, "Smoke", "Medium");
        zones = new HashMap<>();
        Zone zone = new Zone(1, Map.entry(0,0), Map.entry(50,50));
        zones.put(1, zone);
    }

    @Test
    void testMessageRetrieval() {
        assertEquals("FLY TO FIRE", scheduler.getMessage(), "Message should be 'FLY TO FIRE'");
    }

    @Test
    void testReceiveRequestFromFiresystem() throws InterruptedException {
        Thread fireSystemThread = new Thread(() -> scheduler.receiveRequestFromFiresystem(ticket1, zones, 1));
        fireSystemThread.start();
        fireSystemThread.join();

        assertEquals(ticket1, scheduler.sendMessageToDrone(), "The ticket sent to the drone should match the request received");
    }

    @Test
    void testDroneProcessing() throws InterruptedException {
        Thread fireSystemThread = new Thread(() -> scheduler.receiveRequestFromFiresystem(ticket1, zones, 1));
        Thread droneThread = new Thread(() -> {
            FireIncidentTicket assignedTicket = scheduler.sendMessageToDrone();
            scheduler.receiveMessageFromDrone(assignedTicket);
        });
        Thread fireCompletionThread = new Thread(() -> {
            FireIncidentTicket completedTicket = scheduler.completeFiresystemRequest();
            assertEquals(ticket1, completedTicket, "Completed ticket should match the processed ticket");
        });

        fireSystemThread.start();
        droneThread.start();
        fireCompletionThread.start();

        fireSystemThread.join();
        droneThread.join();
        fireCompletionThread.join();

        assertEquals(1, scheduler.getRequestsCompleted(), "Requests completed should be 1");
    }
}

