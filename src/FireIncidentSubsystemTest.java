import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FireIncidentSubsystemTest {
    private Scheduler scheduler;
    private FireIncidentSubsystem fireIncidentSubsystem;
    private HashMap<Integer, Zone> zones;

    @BeforeEach
    void setUp() {
        scheduler = new Scheduler();
        fireIncidentSubsystem = new FireIncidentSubsystem(scheduler);
        zones = new HashMap<>();

        // Creating a real zone instance
        zones.put(1, new Zone(1, Map.entry(0, 0), Map.entry(100, 100)));
    }

    @Test
    void testParseCoordinates() {
        Map.Entry<Integer, Integer> coordinates = FireIncidentSubsystem.parseCoordinates("(10;20)");
        assertEquals(10, coordinates.getKey(), "X coordinate should be 10");
        assertEquals(20, coordinates.getValue(), "Y coordinate should be 20");
    }

    @Test
    void testReceiveRequestFromFireSystem() {
        FireIncidentTicket ticket = new FireIncidentTicket("12:00", 1, "Fire", "High");
        scheduler.receiveRequestFromFiresystem(ticket, zones, 1);
        FireIncidentTicket completedTicket = scheduler.completeFiresystemRequest();

        assertNotNull(completedTicket, "Completed ticket should not be null");
    }
}