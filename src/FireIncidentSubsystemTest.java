import org.junit.jupiter.api.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FireIncidentSubsystemTest {

    private Scheduler scheduler;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeAll
    void setUp() {
        System.setOut(new PrintStream(outContent));
        scheduler = new Scheduler();
        FireIncidentSubsystem fireIncidentSubsystem = new FireIncidentSubsystem(scheduler);
    }

    @AfterAll
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testSchedulerMessage() {
        assertEquals("Fly to fire", scheduler.getMessage());
    }

    @Test
    void testFireIncidentRequestProcessing() {
        FireIncidentTicket ticket = new FireIncidentTicket("12:00", 1, "Fire", "High");
        scheduler.receiveRequestFromFiresystem(ticket);
        FireIncidentTicket receivedTicket = scheduler.sendMessageToDrone();
        assertEquals(ticket, receivedTicket);

        scheduler.receiveMessageFromDrone(ticket);
        FireIncidentTicket completedTicket = scheduler.completeFiresystemRequest();
        assertEquals(ticket, completedTicket);
        assertEquals(1, scheduler.getRequestsCompleted());
    }

    @Test
    void testSchedulerSynchronization() {
        FireIncidentTicket ticket1 = new FireIncidentTicket("12:00", 1, "Fire", "High");
        FireIncidentTicket ticket2 = new FireIncidentTicket("12:05", 2, "Smoke", "Medium");

        scheduler.receiveRequestFromFiresystem(ticket1);

        FireIncidentTicket processed1 = scheduler.sendMessageToDrone();
        scheduler.receiveMessageFromDrone(processed1);
        scheduler.completeFiresystemRequest();

        scheduler.receiveRequestFromFiresystem(ticket2);

        FireIncidentTicket processed2 = scheduler.sendMessageToDrone();
        scheduler.receiveMessageFromDrone(processed2);
        scheduler.completeFiresystemRequest();

        assertEquals(3, scheduler.getRequestsCompleted());
    }
}
