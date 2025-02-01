
// Imports
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

/**
 * Fire Incident subsystem is to read in events using the format shown above:
 * Time, Zone ID, Event Type and Severity. Each line of input is to be sent to
 * the Scheduler
 */
public class FireIncidentSubsystem implements Runnable {

    Scheduler scheduler;
    private List<FireIncidentTicket> eventTickets;
    /**
     * Reads the event file into an ArrayList.
     *
     * @param filePath Path to the event file.
     */
    private void readFile(String filePath) {
        try (Scanner scanner = new Scanner(new File(filePath))) {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] lineData = line.split(",");
                FireIncidentTicket eventTicket = new FireIncidentTicket(
                    lineData[0],
                    Integer.parseInt(lineData[1]),
                    lineData[2],
                    lineData[3]);
                eventTickets.add(eventTicket);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Event file not found: " + e.getMessage());
        }
    }



    @Override
    public void run() {

        readFile("resources\\sample_event_file.csv");

        for (FireIncidentTicket eventTicket : eventTickets) {
            scheduler.receiveRequest(eventTicket);
        }
    }
}
