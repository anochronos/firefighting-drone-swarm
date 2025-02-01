public class Main {
    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler();
        
        Thread fireSubsystem = new Thread(new FireIncidentSubsystem(scheduler), "Fire Subsystem");

        Thread droneSubsystem = new Thread(new DroneSubsystem(scheduler), "Drone Subsystem");

        fireSubsystem.start();
        droneSubsystem.start();
    }
}
