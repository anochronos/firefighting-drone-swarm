import java.util.Map;

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
        System.out.println("Scheduler in State 'Receiving Request from FireIncidentSubsystem'");
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
        System.out.println("Scheduler in State 'Sending Message to DroneSubsystem'");
        System.out.println("\nScheduler: Sending message '" + message +"' to DroneSubsystem");
        Drone chosenDrone = chooseDrone(Map.entry(20,20));
        if (chosenDrone == null) {
            System.out.println("Scheduler: Error could not find a drone");
            System.exit(0);
        }
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
        System.out.println("Scheduler in State 'Receiving Message from DroneSubsystem'");
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
        System.out.println("Scheduler in State 'Sending Message to FireIncidentSubsystem'");
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
     *    1.2 If none, look for drone in DroppingAgent or ClosingNozzle State
     *    1.3 If a drone is chosen, check water level and battery.
     * 2. Check for an idle drone (if no active drones found).
     * 3. Choose any remaining drone
     *
     * @return The chosen drone or null if no suitable drone is found.
     */
    public Drone chooseDrone(Map.Entry<Integer, Integer> fireCoordinates) {
       Drone selectedDrone = null;

        // 1. Check for active drones
        for (Drone drone : droneSubsystem.getDrones().values()) {
            if (drone.getState() instanceof ReturningToBaseState && drone.getWaterTanklvl() > 0 && drone.getBattery() > 50) {
                selectedDrone = drone;
                break;
            }
        }

        for (Drone drone : droneSubsystem.getDrones().values()) {
            if ((drone.getState() instanceof DroppingAgentState || drone.getState() instanceof ClosingNozzleState) && drone.getWaterTanklvl() > 0 && drone.getBattery() > 50) {
                selectedDrone = drone;
                break;
            }
        }

        // 2. If no active drone found, check for an idle drone
        if (selectedDrone == null) {
            selectedDrone = droneSubsystem.getAvailableDrone();
        }

        // 3. Choose any remaining
        if (selectedDrone == null) {
            for (Drone drone : droneSubsystem.getDrones().values()) {
                selectedDrone = drone;
                break;
            }
        }

        if (selectedDrone == null) {
            System.out.println("Scheduler: No available drone found.");
            return null;
        } else {
            System.out.println("Scheduler: Assigning Drone " + selectedDrone.getDroneID() + " to fire incident.");
            //This should be called inside AssignTaskState of scheduler
            Map.Entry<Integer, Integer> coordinates = validateCoordinates(fireCoordinates);
            droneSubsystem.scheduleFlight(selectedDrone.getDroneID(), coordinates.getKey(), coordinates.getValue()); }
        return selectedDrone;
    }

    /**
     * Ensures that coordinates are not of negative value
     * @param coordinates of the fire to be handled
     * @return coordinates of the fire
     */
    private Map.Entry<Integer, Integer> validateCoordinates(Map.Entry<Integer, Integer> coordinates) {
        int x = coordinates.getKey();
        int y = coordinates.getValue();

        if (coordinates.getKey() < 0) {
            System.out.println("Scheduler: Negative X coordinates received. Converting to positive");
            x = Math.abs(coordinates.getKey());
        }
        if (coordinates.getValue() < 0) {
            System.out.println("Scheduler: Negative Y coordinates received. Converting to positive");
            y = Math.abs(coordinates.getKey());
        }
        return Map.entry(x, y);
    }
    
}
