public class ApproachingDestinationState implements DroneStates{
    @Override
    public void startingDispatchToDestination(DroneSubsystem droneSubsystem, Drone drone) {

    }

    @Override
    public void realTimeUpdateOfDroneLocation(DroneSubsystem droneSubsystem, Drone drone) {
        System.out.println("Drone " + drone.getDroneID() + " is in final approach to " + drone.getDestination_X() + ", " + drone.getDestination_Y());

        double stepSize = 0.5; // Slower movement for precision landing
        double newCurrentX, newCurrentY;

        while (Math.abs(drone.getCurrent_X() - drone.getDestination_X()) > stepSize ||
                Math.abs(drone.getCurrent_Y() - drone.getDestination_Y()) > stepSize) {

            newCurrentX = drone.getCurrent_X() + stepSize;
            newCurrentY = drone.getCurrent_Y() + stepSize;

            if (Math.abs(newCurrentX - drone.getDestination_X()) < stepSize) {
                newCurrentX = drone.getDestination_X();
            }
            if (Math.abs(newCurrentY - drone.getDestination_Y()) < stepSize) {
                newCurrentY = drone.getDestination_Y();
            }

            drone.setBattery(drone.getBattery() - 1);
            drone.setCurrentLocation(newCurrentX, newCurrentY);
            System.out.println("Drone " + drone.getDroneID() + " is  currently at (" + newCurrentX + ", " + newCurrentY + ")");

            // Let Drone.java check for state transition
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
        // Transition to the next state (OpeningNozzleState)
        //drone.setState(new OpeningNozzleState());
        //drone.checkForStateTransition(droneSubsystem);
    }

    @Override
    public void reachedDestination(DroneSubsystem droneSubsystem, Drone drone) {
        //request transition to next state from scheduler.
        //droneSubsystem.RequestStateChange(droneSubsystem, drone);
        System.out.println("Drone " + drone.getDroneID() + " has reached its destination.");
        droneSubsystem.beginAgentDropPreparation(drone.getDroneID());
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
