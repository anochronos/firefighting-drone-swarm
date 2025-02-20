import java.util.HashMap;

/**
 * Drones will make calls to the Scheduler which will then reply when there is work to
 * be done. The Drone will then send the data back to the Scheduler who will then send
 * it back to the Fire Incident subsystem
 */

public class DroneSubsystem implements Runnable{
    private Scheduler scheduler;
    private final HashMap<Integer, Drone> drones;
    private static DroneSubsystem droneSubsystemInstance;

    /**
     * Constructor for the DroneSubsystem class
     *
     * @param scheduler to be used for the drone to communicate with
     */
    public DroneSubsystem(Scheduler scheduler) {
        this.scheduler = scheduler;
        drones = new HashMap<>();
        Drone drone1 = new Drone(1, 100, 5.0);
        this.addDrone(drone1);
    }

    public void addDrone(Drone drone){
        drones.put(drone.getDroneID(), drone);
    }

    public void scheduleFlight(int droneId, double x, double y) {
        Drone drone = drones.get(droneId);
        if (drone != null) {
            drone.assignCoordinates(x, y);
            drone.startFlightToDestination(this);
        } else {
            System.out.println("Drone with ID " + droneId + " not found.");
        }
    }

    // Get an available drone (Idle state)
    public Drone getAvailableDrone() {
        for (Drone drone : drones.values()) {
            if (drone.getState() instanceof IdleState) {
                return drone; // Return the first idle drone
            }
        }
        return null;
    }

    public HashMap<Integer, Drone> getDrones() {
        return drones;
    }

    public void reachedNearDestination(int droneId) {
        Drone drone = drones.get(droneId);
        if (drone != null) {
            drone.reachedNearDestination(this);
        }
    }

    public void beginAgentDropPreparation(int droneId) {
        Drone drone = drones.get(droneId);
        if (drone != null) {
            drone.beginAgentDropPreparation(this);
        }
    }

    public void nozzleOpened(int droneId) {
        Drone drone = drones.get(droneId);
        if (drone != null) {
            drone.nozzleOpened(this);
        }
    }

    public void dispensingAgent(int droneId) {
        Drone drone = drones.get(droneId);
        if (drone != null) {
            drone.startingAgentDispensing(this);
        }
    }

    public void nozzleClosed(int droneId) {
        Drone drone = drones.get(droneId);
        if (drone != null) {
            drone.nozzleClosed(this);
        }
    }

    public void faultDetected(int droneId, String faultCode) {
        Drone drone = drones.get(droneId);
        if (drone != null) {
            drone.faultDetected(this, faultCode);
        }
    }

    public void returnToBase(int droneId) {
        Drone drone = drones.get(droneId);
        if (drone != null) {
            drone.returnToBase(this);
        }
    }

    public void arrivedAtBase(int droneId) {
        Drone drone = drones.get(droneId);
        if (drone != null) {
            drone.arrivedAtBase(this);
        }
    }

    /**
     * Runs the drone thread, which receives fire incident tickets from the scheduler
     * and sends a message back once the task has been completed.
     */
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                FireIncidentTicket eventTicket = scheduler.sendMessageToDrone();
                System.out.println("DroneSubsystem: Received Task from Scheduler");
                Thread.sleep(1000);
                System.out.println("DroneSubsystem: Task Completed");
                scheduler.receiveMessageFromDrone(eventTicket);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
}
}
