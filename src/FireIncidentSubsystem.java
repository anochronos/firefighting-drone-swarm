// Imports
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Fire Incident subsystem is to read in events using the format shown above:
 * Time, Zone ID, Event Type and Severity. Each line of input is to be sent to
 * the Scheduler
 */
public class FireIncidentSubsystem implements Runnable {

    Scheduler scheduler;
    FireIncidentTicket completedTicket;
    HashMap<Integer, Zone> zones = new HashMap<>();

    /**
     * Reads the event file into an ArrayList.
     *
     * @param scheduler to be used to communicate with
     */
    public FireIncidentSubsystem(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    private static Map.Entry<Integer, Integer> parseCoordinates(String coordinate) {
        coordinate = coordinate.replace("(", "").replace(")", "");
        String[] coords = coordinate.split(";");
        return Map.entry(Integer.parseInt(coords[0].trim()), Integer.parseInt(coords[1].trim()));
    }

    /**
     * Run the FireIncidentSubsystem thread, which parses the csv file and creates event tickets.
     * Send event ticket request to the scheduler and message once the ticket is completed.
     */
    @Override
    public void run() {

        try (Scanner scanner = new Scanner(new File("src\\resources\\sample_zone_file.csv"))) {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            int zoneId = 1;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] lineData = line.split(",");
                Zone fireZone = new Zone(
                        Integer.parseInt(lineData[0]),
                        parseCoordinates(lineData[1].trim()),
                        parseCoordinates(lineData[2].trim()));

                zones.put(zoneId, fireZone); zoneId++;
                System.out.println("Initialised a Zone --> " + fireZone);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Zone file not found: " + e.getMessage());
        }
        System.out.println("Fire Incident Subsystem: Finished Parsing CSV zone file\n");

        try (Scanner scanner = new Scanner(new File("src\\resources\\sample_event_file.csv"))) {
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

                System.out.println("Fire Incident Subsystem: Finished Parsing CSV event file");
                System.out.println("Fire Incident Subsystem: Sent request to the scheduler");
                scheduler.receiveRequestFromFiresystem(eventTicket);
                completedTicket = scheduler.completeFiresystemRequest();
                System.out.println("Fire Incident Subsystem: Received completed ticket from scheduler\n\n");
            }
        } catch (FileNotFoundException e) {
            System.err.println("Event file not found: " + e.getMessage());
        }
        System.exit(0);
    }
}
