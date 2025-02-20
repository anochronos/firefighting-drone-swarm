public class DroppingAgentState implements DroneStates{
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
    public void prepareForAgentRelease(DroneSubsystem droneSubsystem, Drone drone) {

    }

    @Override
    public void nozzleOpened(DroneSubsystem droneSubsystem, Drone drone) {

    }

    @Override
    public void droppingAgent(DroneSubsystem droneSubsystem, Drone drone) {
        double waterLevel = drone.getWaterTanklvl();

        System.out.println("Drone " + drone.getDroneID() + " has started dropping agent.");

        while (waterLevel > 0 && drone.getState() instanceof DroppingAgentState) {
            System.out.println("Current water tank level for Drone " + drone.getDroneID() + ": " + waterLevel);

            // decrement water level by 5.
            waterLevel -= 5;
            drone.setWaterTanklvl(waterLevel);

            //Check for faults
            /**boolean pumpFailure = Math.random() < 0.01;
            if (pumpFailure) {
                System.out.println("Drone " + drone.getDroneID() + " encountered a failure! Transitioning to FaultDetectedState...");
                drone.setState(new FaultDetectedState());
                drone.getState().faultDetected(droneSubsystem, drone, "PUMP_FAILURE");
                return;
            }**/

            // Check if fire is extinguished early
            //we can implment here fire severity
            //then also proceed to ClosingNozzleState


            // Simulate time delay for agent release
            try {
                Thread.sleep(1000); // Simulate release interval
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        //tank is empty, transition to `ClosingNozzleState`
        if (waterLevel <= 0) {
            System.out.println("Drone " + drone.getDroneID() + " has emptied its water tank. Closing nozzle...");
            drone.setState(new ClosingNozzleState());
            drone.nozzleClosed(droneSubsystem);
        }
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
