/**
 * Drones will make calls to the Scheduler which will then reply when there is work to
 * be done. The Drone will then send the data back to the Scheduler who will then send
 * it back to the Fire Incident subsystem
 */

public class DroneSubsystem implements Runnable{
    Scheduler scheduler;

    /**
     * Constructor for the DroneSubsystem class
     *
     * @param scheduler to be used for the drone to communicate with
     */
    public DroneSubsystem(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    /**
     * Runs the drone thread, which receives fire incident tickets from the scheduler
     * and sends a message back once the task has been completed.
     */
    @Override
    public void run(){

        try {
            while (!Thread.currentThread().isInterrupted()) {
                FireIncidentTicket eventTicket = scheduler.sendMessageToDrone();
                System.out.println("Drone: Received Task");
                Thread.sleep(1000);
                System.err.println("Drone: Completed Task");
                scheduler.receiveMessageFromDrone(eventTicket);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
