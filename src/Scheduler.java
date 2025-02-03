/**
 * The Scheduler class coordinates communication between the Fire Incident System and the Drone System.
 * It manages fire incident requests, sends messages to drones, and receives responses from drones.
 * The scheduler ensures synchronized access to shared resources.
 */

public class Scheduler{
    private int requestsCompleted = 0;
    private boolean requestAvailable = false;
    private final String message;
    private boolean droneAvailable = false;
    FireIncidentTicket currentTicket;
    FireIncidentTicket completedTicket;

    /**
     * Constructor for scheduler class
     */
    public Scheduler() {
        this.message = "Fly to fire";
    }

    /**
     * Retrieves the message to be sent to the drone.
     *
     * @return The message instructing the drone.
     */
    public String getMessage(){
        return message;
    }

    /**
     * Retrieves the total number of requests that have been successfully completed.
     *
     * @return The number of completed requests.
     */
    public int getRequestsCompleted() {
        return requestsCompleted;
    }

    /**
     * Receives a fire incident request from the fire system and stores it for processing.
     * This method waits if there is already a pending request until the previous one is processed.
     *
     * @param request The fire incident ticket to be processed.
     */
    public synchronized void receiveRequestFromFiresystem(FireIncidentTicket request) { // fire system
        while (requestAvailable) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        currentTicket = request;
        System.out.println("Scheduler: Received request from the fire incident system");
        requestAvailable = true;
        notifyAll();
    }

    /**
     * Sends a message to the drone when a fire incident request is available.
     * This method waits if no request is available.
     *
     * @return The fire incident ticket assigned to the drone.
     */
    public synchronized FireIncidentTicket sendMessageToDrone() { // drone
        while (!requestAvailable) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Scheduler: Sent message '" + message +"' to available drone");
        requestAvailable = false;
        notifyAll();
        return currentTicket;
    }

    /**
     * Receives a response from the drone system after it has completed handling a fire incident.
     * This method waits if there is already a pending drone response.
     *
     * @param eventTicket The completed fire incident ticket from the drone.
     */
    public synchronized void receiveMessageFromDrone(FireIncidentTicket eventTicket) { // drone
        while (droneAvailable) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        completedTicket = eventTicket;
        System.out.println("Scheduler: Received response from drone");
        droneAvailable = true;
        
        notifyAll();
    }

    /**
     * Completes the fire incident request by sending the processed ticket back to the fire system.
     * This method waits if there is no completed request from the drone.
     *
     * @return The completed fire incident ticket.
     */
    public synchronized FireIncidentTicket completeFiresystemRequest() {
        while (!droneAvailable) {
            try {
                System.out.println("Scheduler: Waiting for drone to be available");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        droneAvailable = false;
        System.out.println("Scheduler: Sending completed ticket to fire system");
        requestsCompleted++;
        notifyAll();
        return completedTicket;
    }
    
}
