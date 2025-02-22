/**
 * This class initializes the system by creating and starting the Fire Incident and Drone subsystems,
 * which are responsible for handling fire incidents and drone operations, respectively.
 */

public class Main {

    /**
     * The main method that initializes the scheduler and starts the subsystems in separate threads.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        System.out.println("\n-----Fire Subsystem started-----\n");

        // Create a shared scheduler instance
        Scheduler scheduler = new Scheduler();

        // Initialize and start the Fire Incident Subsystem thread
        Thread fireSubsystem = new Thread(new FireIncidentSubsystem(scheduler), "Fire Subsystem");

        // Initialize and start the Drone Subsystem thread
        Thread droneSubsystem = new Thread(new DroneSubsystem(scheduler), "Drone Subsystem");

        // Start both subsystems
        fireSubsystem.start();
        droneSubsystem.start();

        try {
            fireSubsystem.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.exit(0);
    }
}
