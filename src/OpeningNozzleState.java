public class OpeningNozzleState implements DroneStates{
    @Override
    public void startingDispatchToDestination(DroneSubsystem droneSubsystem, Drone drone) {

    }

    @Override
    public void realTimeUpdateOfDroneLocation(DroneSubsystem droneSubsystem, Drone drone) {

    }

    @Override
    public void reachedNearDestination(DroneSubsystem droneSubsystem, Drone drone) {

    }

    @Override
    public void reachedDestination(DroneSubsystem droneSubsystem, Drone drone) {

    }

    @Override
    public void prepareForAgentRelease(DroneSubsystem droneSubsystem, Drone drone) {
        System.out.println("Drone " + drone.getDroneID() + ", is preparing for opening nozzel/agent release.\n");
        droneSubsystem.nozzleOpened(drone.getDroneID());


    }

    @Override
    public void nozzleOpened(DroneSubsystem droneSubsystem, Drone drone) {
        System.out.println("Nozzle opening for Drone: " + drone.getDroneID() + "...");

        // Simulate opening delay
        try {
            System.out.println("Opening nozzle...........\n");
            Thread.sleep(1000); // simulation time to open nozzle
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Simulate  10% chance of failure
        boolean nozzleStuck = Math.random() < 0.1;
        if (nozzleStuck) {
            System.out.println("Nozzle stuck for Drone: " + drone.getDroneID() + "! Transitioning to FaultDetectedState...");
            drone.setState(new FaultDetectedState());
            drone.getState().faultDetected(droneSubsystem, drone, "NOZZLE_STUCK");
            return;
        }

        // No fault detected, transition to the next state
        System.out.println("Nozzle successfully opened for Drone: " + drone.getDroneID());
        drone.setState(new DroppingAgentState());
        droneSubsystem.dispensingAgent(drone.getDroneID());
        //ask scheduler if the fire is extinguished, i.e the ground sensor might have informed the scheduler
        //if so we have to change our state to closeingNozzelState.
        //droneSubsystem.RequestStateChange(droneSubsystem, drone);

    }

    @Override
    public void droppingAgent(DroneSubsystem droneSubsystem, Drone drone) {

    }

    @Override
    public void nozzleClosed(DroneSubsystem droneSubsystem, Drone drone) {

    }

    @Override
    public void faultDetected(DroneSubsystem droneSubsystem, Drone drone, String fault) {

    }

    @Override
    public void returnToBase(DroneSubsystem droneSubsystem, Drone drone) {

    }

    @Override
    public void arrivedAtBase(DroneSubsystem droneSubsystem, Drone drone) {

    }
}
