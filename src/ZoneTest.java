import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The `ZoneTest` class contains unit tests for the `Zone` class and the `Scheduler`'s coordinate validation logic.
 * It tests the handling of negative coordinates, out-of-bounds coordinates, and valid coordinates within a zone.
 */
class ZoneTest {
    private Scheduler scheduler;
    private HashMap<Integer, Zone> zones;

    /**
     * Sets up the test environment before each test case.
     * Initializes the `Scheduler` and a `Zone` with specific boundaries for testing.
     */
    @BeforeEach
    void setUp() {
        scheduler = new Scheduler();
        zones = new HashMap<>();

        // Creating a zone with specific boundaries
        Zone zone = new Zone(1, Map.entry(0,0), Map.entry(50,50));
        zones.put(1, zone);
    }

    @Test
    void testNegativeCoordinates() {
        Map.Entry<Integer, Integer> inputCoordinates = Map.entry(-10, -20);
        Map.Entry<Integer, Integer> validated = scheduler.validateCoordinates(inputCoordinates);

        assertEquals(10, validated.getKey(), "X coordinate should be converted to positive");
        assertEquals(20, validated.getValue(), "Y coordinate should be converted to positive");
    }

    @Test
    void testOutOfBoundsCoordinates() {
        scheduler.receiveRequestFromFiresystem(new FireIncidentTicket("14:10:00",1,"DRONE_REQUEST","Moderate"), zones, 1);
        Map.Entry<Integer, Integer> inputCoordinates = Map.entry(60, 60);
        Map.Entry<Integer, Integer> validated = scheduler.validateCoordinates(inputCoordinates);

        assertEquals(25, validated.getKey(), "X coordinate should be adjusted to zone center");
        assertEquals(25, validated.getValue(), "Y coordinate should be adjusted to zone center");
    }

    @Test
    void testValidCoordinates() {
        Map.Entry<Integer, Integer> inputCoordinates = Map.entry(30, 40);
        Map.Entry<Integer, Integer> validated = scheduler.validateCoordinates(inputCoordinates);

        assertEquals(30, validated.getKey(), "X coordinate should remain unchanged");
        assertEquals(40, validated.getValue(), "Y coordinate should remain unchanged");
    }
}
