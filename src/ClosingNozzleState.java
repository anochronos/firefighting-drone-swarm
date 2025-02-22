public class ClosingNozzleState implements DroneStates{
    @Override
    public void startingDispatchToDestination(DroneSubsystem droneSubsystem, Drone drone) {}

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
    public void nozzleClosed(DroneSubsystem droneSubsystem, Drone drone) {
        //simulation of nozzle closing
        System.out.println("Drone " + drone.getDroneID() + " is closing the nozzle...");

        // Simulate nozzle closing time
        try {
            Thread.sleep(1000); // Simulate time delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Simulating  nozzle jam 1% prob
        /**boolean nozzleJam = Math.random() < 0.01;
        if (nozzleJam) {
            System.out.println("Nozzle jammed while closing on Drone " + drone.getDroneID() + "! Transitioning to FaultDetectedState...");
            drone.setState(new FaultDetectedState());
            drone.faultDetected(droneSubsystem, "NOZZLE_JAM");
            return;
        }**/

        //closed
        System.out.println("Nozzle successfully closed for Drone " + drone.getDroneID());

        //for future iterations we can implement here if we want the drone to pass by another fireZone, not necessarily to Base.
        //by droneSubsystem.RequestStateChange();

        //Transition to `ReturningToBaseState`
        droneSubsystem.returnToBase(drone.getDroneID());
    }

    @Override
    public void faultDetected(DroneSubsystem droneSubsystem, Drone drone, String fault) {}

    @Override
    public void returnToBase(DroneSubsystem droneSubsystem, Drone drone) {}

    @Override
    public void arrivedAtBase(DroneSubsystem droneSubsystem, Drone drone) {}
}
