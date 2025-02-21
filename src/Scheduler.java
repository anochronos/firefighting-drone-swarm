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
    private final DroneSubsystem droneSubsystem;

    /**
     * Constructor for scheduler class
     */
    public Scheduler() {
        droneSubsystem = new DroneSubsystem(this);
        message = "FLY TO FIRE";
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
    public synchronized void receiveRequestFromFiresystem(FireIncidentTicket request) {
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
    public synchronized FireIncidentTicket sendMessageToDrone() {
        while (!requestAvailable) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        Drone chosenDrone = chooseDrone();
        if (chosenDrone == null) {
            System.out.println("Scheduler: Error could not find a drone");
            System.exit(0);
        }
        System.out.println("Scheduler: Sent message '" + message +"' to DroneSubsystem");
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

    /**
     * Chooses an available drone based on priority order.
     * 1. Check for drones not in idle state.
     *    1.1 Prioritize drones returning to base (thus they can go immediately)
     *    1.2 If none, look for drone in DroppingAgent State
     *    1.3 If a drone is chosen, check water level and battery.
     * 2. Check for an idle drone (if no active drones found).
     *
     * @return The chosen drone or null if no suitable drone is found.
     */
    public Drone chooseDrone() {
       Drone selectedDrone;

        selectedDrone = droneSubsystem.getAvailableDrone();

        // 1. Check for active drones returning to base
        if (selectedDrone == null) {
        for (Drone drone : droneSubsystem.getDrones().values()) {
            if (drone.getState() instanceof ReturningToBaseState && drone.getWaterTanklvl() > 0 && drone.getBattery() > 0) {
                selectedDrone = drone;
                break;
            }
        }
        }

        // 2. If no active drone found, check for an idle drone


        // 3. Choose any remaining
//        if (selectedDrone == null) {
//            for (Drone drone : droneSubsystem.getDrones().values()) {
//                selectedDrone = drone;
//                break;
//            }
//        }

        if (selectedDrone == null) {
            System.out.println("Scheduler: No available drone found.");
            //add drone to the hashmap in dronesubsystem
            return null;
        } else {
            System.out.println("Scheduler: Assigning Drone " + selectedDrone.getDroneID() + " to fire incident.");
            //This should be called inside AssignTaskState of scheduler
            droneSubsystem.scheduleFlight(selectedDrone.getDroneID(), 10, 10); }
        return selectedDrone;
    }
    
}
