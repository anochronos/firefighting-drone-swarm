public class OpeningNozzleState implements DroneStates{
    @Override
    public void startingDispatchToDestination(DroneSubsystem droneSubsystem, Drone drone) {}

    @Override
    public void realTimeUpdateOfDroneLocation(DroneSubsystem droneSubsystem, Drone drone) {}

    @Override
    public void reachedNearDestination(DroneSubsystem droneSubsystem, Drone drone) {}

    @Override
    public void reachedDestination(DroneSubsystem droneSubsystem, Drone drone) {}

    @Override
    public void prepareForAgentRelease(DroneSubsystem droneSubsystem, Drone drone) {
        System.out.println("\nDrone " + drone.getDroneID() + ", is preparing to release agent.");
        try {
            System.out.println("Opening nozzle...........\n");
            Thread.sleep(1000); // simulation time to open nozzle
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        droneSubsystem.nozzleOpened(drone.getDroneID());
    }

    @Override
    public void nozzleOpened(DroneSubsystem droneSubsystem, Drone drone) {
        System.out.println("Nozzle successfully opened for Drone: " + drone.getDroneID());
        droneSubsystem.dispensingAgent(drone.getDroneID());
        //ask scheduler if the fire is extinguished, i.e the ground sensor might have informed the scheduler
        //if so we have to change our state to closingNozzleState.
        //droneSubsystem.RequestStateChange(droneSubsystem, drone);

    }

    @Override
    public void droppingAgent(DroneSubsystem droneSubsystem, Drone drone) {}

    @Override
    public void nozzleClosed(DroneSubsystem droneSubsystem, Drone drone) {}

    @Override
    public void faultDetected(DroneSubsystem droneSubsystem, Drone drone, String fault) {}

    @Override
    public void returnToBase(DroneSubsystem droneSubsystem, Drone drone) {}

    @Override
    public void arrivedAtBase(DroneSubsystem droneSubsystem, Drone drone) {}
}
