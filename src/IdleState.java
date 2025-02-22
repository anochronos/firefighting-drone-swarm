public class IdleState implements DroneStates {

    @Override
    public void startingDispatchToDestination(DroneSubsystem droneSubsystem, Drone drone) {
        System.out.println("Drone " + drone.getDroneID() + " is assigned a flight to " + drone.getCoordinates());
    }

    @Override
    public void realTimeUpdateOfDroneLocation(DroneSubsystem droneSubsystem, Drone drone) {}

    @Override
    public void reachedNearDestination(DroneSubsystem droneSubsystem, Drone drone) {}

    @Override
    public void reachedDestination(DroneSubsystem droneSubsystem, Drone drone) {}

    @Override
    public void prepareForAgentRelease(DroneSubsystem droneSubsystem, Drone drone) {}

    @Override
    public void nozzleOpened(DroneSubsystem droneSubsystem, Drone drone) {}

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
