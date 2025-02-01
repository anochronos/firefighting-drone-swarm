
public class Scheduler implements Runnable{
    private int requestsCompleted = 0;
    private boolean requestAvailable;
    private String message;
    private boolean droneAvailable;


    public Scheduler() {
        this.message = "Fly to fire";
    }

    public int getRequestsCompleted() {
        return requestsCompleted;
    }


    public synchronized void sendMessage(String message) { // drone
        while (!droneAvailable) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        System.out.println("Scheduler: Sent message '" + message +"' to available drone");
        droneAvailable = false;
        notifyAll();
    }

    public synchronized void receiveMessage(String message) { // drone
        while (droneAvailable) {
            try {
                wait();
            } catch (InterruptedException e) {

            }
        }
        System.out.println("Scheduler: Received message '" + message +"' from drone");
        droneAvailable = true;
        requestAvailable = true;
        requestsCompleted++;
        notifyAll();
    }

    public synchronized void receiveRequest(FireIncidentTicket request) { // fire system
        while (!requestAvailable) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        System.out.println("Scheduler: Received request from the fire incident system");
        sendMessage(message);
        notifyAll();
    }
    
    @Override
    public void run() {

    }

}
