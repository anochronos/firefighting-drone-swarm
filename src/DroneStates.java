public interface DroneStates {
    void startingDispatchToDestination(DroneSubsystem droneSubsystem, Drone drone);
    void realTimeUpdateOfDroneLocation(DroneSubsystem droneSubsystem, Drone drone);
    void reachedNearDestination(DroneSubsystem droneSubsystem, Drone drone);

    void prepareForAgentRelease(DroneSubsystem droneSubsystem, Drone drone);

    void nozzleOpened(DroneSubsystem droneSubsystem, Drone drone);
    void droppingAgent(DroneSubsystem droneSubsystem, Drone drone);
    void nozzleClosed(DroneSubsystem droneSubsystem, Drone drone);
    void faultDetected(DroneSubsystem droneSubsystem,Drone drone, String fault);
    void returnToBase(DroneSubsystem droneSubsystem, Drone drone);
    void arrivedAtBase(DroneSubsystem droneSubsystem, Drone drone);
}
