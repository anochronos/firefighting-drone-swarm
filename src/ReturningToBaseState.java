public class ReturningToBaseState implements DroneStates{
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

    }

    @Override
    public void nozzleOpened(DroneSubsystem droneSubsystem, Drone drone) {

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
        System.out.println("Drone " + drone.getDroneID() + " is returning to base...");

        // base coordinates (0,0)
        drone.assignCoordinates(0.0, 0.0);

        // Simulate movement to base
        double stepSize = 2.0;
        while (Math.abs(drone.getCurrent_X() - 0.0) > stepSize ||
                Math.abs(drone.getCurrent_Y() - 0.0) > stepSize) {

            double newCurrentX = drone.getCurrent_X() + stepSize * Math.signum(0.0 - drone.getCurrent_X());
            double newCurrentY = drone.getCurrent_Y() + stepSize * Math.signum(0.0 - drone.getCurrent_Y());

            drone.setCurrentLocation(newCurrentX, newCurrentY);
            System.out.println("Drone " + drone.getDroneID() + " en route to base at (" + newCurrentX + ", " + newCurrentY + ")");

            //we could add fault, but since wer returning to base and for simplicity i didnt include it.

            try {
                Thread.sleep(400); //diplaydelay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        //arrived at the base
        drone.arrivedAtBase(droneSubsystem);

    }


    @Override
    public void arrivedAtBase(DroneSubsystem droneSubsystem, Drone drone) {
        System.out.println("Drone " + drone.getDroneID() + " has arrived at the base.");
        //for simplicity, automatically set to idle
        //we have to reset the drones fields.
        drone.setState(new IdleState());

    }
}
