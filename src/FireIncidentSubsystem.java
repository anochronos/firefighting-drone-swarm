// Imports
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *  Fire Incident subsystem is to read in events using the format shown above:
 * Time, Zone ID, Event Type and Severity. Each line of input is to be sent to the Scheduler
 */
public class FireIncidentSubsystem {
    /**
     * Reads the event file into an ArrayList.
     *
     * @param filePath Path to the event file.
     */
    public void readFile(String filePath) {
        try (Scanner scanner = new Scanner(new File(filePath))) {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] lineData = line.split(",");
                createFireRequest(lineData);
                }
            }
        catch (FileNotFoundException e) {
            System.err.println("Event file not found: " + e.getMessage());
        }
    }
    public void createFireRequest(String[] event){
        FireIncidentTicket eventTicket = new FireIncidentTicket(event[0],event[1],event[2],event[3]);
        System.out.println(eventTicket);
    }

    public void sendFireRequest(){
    }

    /**
     * Main method to test IF THE CLASS WORKS.
     */
    public static void main(String[] args) {
        FireIncidentSubsystem subsystem = new FireIncidentSubsystem();
        String eventFilePath = "src/Config/Sample_event_file.csv";
        subsystem.readFile(eventFilePath);

    }
}