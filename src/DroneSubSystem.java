


public class DroneSubsystem implements Runnable{
    Scheduler scheduler;
   
    public DroneSubsystem(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

        
    @Override
    public void run(){

        try {
            while (!Thread.currentThread().isInterrupted()) {
                FireIncidentTicket eventTicket = scheduler.sendMessageToDrone();
                System.out.println("Drone: Received Task");
                Thread.sleep(1000);
                System.err.println("Drone: Completed Task");
                scheduler.receiveMessageToDrone(eventTicket);
            }
        } catch (InterruptedException e) {}
    }
}
