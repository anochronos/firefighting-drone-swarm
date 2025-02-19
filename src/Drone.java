import java.util.AbstractMap;
import java.util.Map;

public class Drone {
    private int droneID;
    private DroneStates currentState;
    private double battery;
    private double waterTanklvl;
    private double destination_X;  // eg fire zone center X
    private double destination_Y;  // fire zone center Y
    private double current_X;
    private double current_Y;

    public Drone(int droneID, double battery, double waterTanklvl) {
        this.droneID = droneID;
        this.battery = battery;
        this.waterTanklvl = waterTanklvl;
        this.currentState = new IdleState();// Initial state
        current_X = 0.0;
        current_Y =0.0;
    }


    public void assignCoordinates(double x, double y) {
        destination_X = x;
        destination_Y = y;
        System.out.println("Drone " + droneID + " assigned to mission at center (" + destination_X + ", " + destination_Y + ")");
    }


    public void setState(DroneStates newState) {
        this.currentState = newState;
        System.out.println("Drone " + droneID + " transitioned to " + newState.getClass().getSimpleName());
    }



    public void startFlightToDestination(DroneSubsystem droneSubsystem) {
        currentState.startingDispatchToDestination(droneSubsystem, this);
        if (currentState instanceof EnRouteState) {
            currentState.realTimeUpdateOfDroneLocation(droneSubsystem,this);
        } else {
            System.out.println("Drone " + droneID + " did not enter EnRouteState. Real-time update canceled.");
        }

    }

    public void checkForStateTransition(DroneSubsystem droneSubsystem) {
        boolean faultDetected = Math.random() < 0.1; // Simulated 10% fault probability

        if (faultDetected) {
            System.out.println("Drone " + droneID + " detected a fault! Transitioning to FaultDetectedState...");
            this.setState(new FaultDetectedState());
            currentState.faultDetected(droneSubsystem, this, "ENGINE_FAILURE");
            return;
        }

        if (currentState instanceof EnRouteState) {
            // Check if the drone is near the destination
            if (Math.abs(getCurrent_X() - getDestination_X()) < 5 &&
                    Math.abs(getCurrent_Y() - getDestination_Y()) < 5) {
                this.setState(new ApproachingDestinationState());
                reachedNearDestination(droneSubsystem);
            }
        }

        if (currentState instanceof ApproachingDestinationState) {
            // Check if the drone has fully reached its destination
            if (Math.abs(getCurrent_X() - getDestination_X()) < 1 &&
                    Math.abs(getCurrent_Y() - getDestination_Y()) < 1) {
                //System.out.println("Drone " + droneID + " has arrived at the exact destination.");
                reachedNearDestination(droneSubsystem);
            }
        }
        if (currentState instanceof OpeningNozzleState) {
            // Check if the drone has fully reached its destination
            if (Math.abs(getCurrent_X() - getDestination_X()) < 1 &&
                    Math.abs(getCurrent_Y() - getDestination_Y()) < 1) {
                System.out.println("Drone " + droneID + " has arrived at the exact destination.");
                beginAgentDropPreparation(droneSubsystem);
                nozzleOpened(droneSubsystem);

            }
        }
    }


    public void reachedNearDestination(DroneSubsystem droneSubsystem) {
        System.out.println("Drone " + droneID + " has reached near its destination.");
        currentState.reachedNearDestination(droneSubsystem, this);
    }




    public void beginAgentDropPreparation(DroneSubsystem droneSubsystem) {
        currentState.prepareForAgentRelease(droneSubsystem, this);

    }

    public void nozzleOpened(DroneSubsystem droneSubsystem) {
        currentState.nozzleOpened(droneSubsystem, this);
    }

    public void startingAgentDispensing(DroneSubsystem droneSubsystem) {
        currentState.droppingAgent(droneSubsystem, this);
    }

    public void nozzleClosed(DroneSubsystem droneSubsystem) {
        currentState.nozzleClosed(droneSubsystem, this);
    }

    public void faultDetected(DroneSubsystem droneSubsystem, String faultCode) {
        currentState.faultDetected(droneSubsystem, this, faultCode);
    }

    public void returnToBase(DroneSubsystem droneSubsystem) {
        currentState.returnToBase(droneSubsystem, this);
    }

    public void arrivedAtBase(DroneSubsystem droneSubsystem) {
        currentState.arrivedAtBase(droneSubsystem, this);
    }


    public int getDroneID() { return droneID; }
    public double getBattery() { return battery; }
    public double getWaterTanklvl() { return waterTanklvl; }


    public void setBattery(double battery) { this.battery = battery; }
    public void setWaterTanklvl(double waterTanklvl) { this.waterTanklvl = waterTanklvl; }
    public DroneStates getState() {
        return currentState; }

    public Map.Entry<Integer, Integer> getCoordinates() {
        return new AbstractMap.SimpleEntry<>((int) destination_X, (int) destination_Y);
    }

    public Map.Entry<Integer, Integer> getCurrentLocation() {
        return new AbstractMap.SimpleEntry<>((int) current_X, (int) current_Y);
    }

    public void setDroneID(int droneID) {
        this.droneID = droneID;
    }



    public double getDestination_X() {
        return destination_X;
    }

    public void setDestination_X(double destination_X) {
        this.destination_X = destination_X;
    }

    public double getDestination_Y() {
        return destination_Y;
    }

    public void setDestination_Y(double destination_Y) {
        this.destination_Y = destination_Y;
    }

    public double getCurrent_X() {
        return current_X;
    }

    public void setCurrent_X(double current_X) {
        this.current_X = current_X;
    }

    public double getCurrent_Y() {
        return current_Y;
    }

    public void setCurrent_Y(double current_Y) {
        this.current_Y = current_Y;
    }

    public void setCurrentLocation(double x, double y){
        this.current_X= x;
        this.current_Y = y;
    }
}
