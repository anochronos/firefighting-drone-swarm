public class EnRouteState implements DroneStates {

    @Override
    public void startingDispatchToDestination(DroneSubsystem droneSubsystem, Drone drone) {

    }

    @Override
    public void realTimeUpdateOfDroneLocation(DroneSubsystem droneSubsystem, Drone drone) {
        System.out.println("Drone " + drone.getDroneID() + " is currently at " + drone.getCurrentLocation());

        double stepSize = 1.0; // Adjust for smoother movement
        double totalDistanceX = drone.getDestination_X() - drone.getCurrent_X();
        double totalDistanceY = drone.getDestination_Y() - drone.getCurrent_Y();

        double distanceMagnitude = Math.sqrt(totalDistanceX * totalDistanceX + totalDistanceY * totalDistanceY);
        double stepX = (totalDistanceX / distanceMagnitude) * stepSize;
        double stepY = (totalDistanceY / distanceMagnitude) * stepSize;

        while (Math.abs(drone.getCurrent_X() - drone.getDestination_X()) > stepSize ||
                Math.abs(drone.getCurrent_Y() - drone.getDestination_Y()) > stepSize) {

            double newCurrentX = drone.getCurrent_X() + stepX;
            double newCurrentY = drone.getCurrent_Y() + stepY;

            if (Math.abs(newCurrentX - drone.getDestination_X()) < stepSize) {
                newCurrentX = drone.getDestination_X();
            }
            if (Math.abs(newCurrentY - drone.getDestination_Y()) < stepSize) {
                newCurrentY = drone.getDestination_Y();
            }

            drone.setCurrentLocation(newCurrentX, newCurrentY);
            System.out.println("Drone " + drone.getDroneID() + " is now at (" + newCurrentX + ", " + newCurrentY + ")");

            // Let Drone decide the next state, either reaching near destination or faultdeDetected state)
            drone.checkForStateTransition(droneSubsystem);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }



    @Override
    public void reachedNearDestination(DroneSubsystem droneSubsystem, Drone drone) {

        System.out.println("Drone with ID: " + drone.getDroneID() + "intially at " + drone.getCurrentLocation());
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

    }

    @Override
    public void arrivedAtBase(DroneSubsystem droneSubsystem, Drone drone) {

    }
}
