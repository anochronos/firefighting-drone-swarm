
public class Scheduler{
    private int requestsCompleted = 0;
    private boolean requestAvailable = false;
    private final String message;
    private boolean droneAvailable = false;
    FireIncidentTicket currentTicket;
    FireIncidentTicket completedTicket;


    public Scheduler() {
        this.message = "Fly to fire";
    }
    
    public String getMessage(){
        return message;
    }
    
    public int getRequestsCompleted() {
        return requestsCompleted;
    }

    public synchronized void receiveRequest(FireIncidentTicket request) { // fire system
        while (requestAvailable) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        currentTicket = request;
        System.out.println("Scheduler: Received request from the fire incident system");
        requestAvailable = true;
        notifyAll();
    }

    public synchronized FireIncidentTicket sendMessage() { // drone
        while (!requestAvailable) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        System.out.println("Scheduler: Sent message '" + message +"' to available drone");
        requestAvailable = false;
        notifyAll();
        return currentTicket;
    }

    public synchronized void receiveMessage(FireIncidentTicket eventTicket) { // drone
        while (droneAvailable) {
            try {
                wait();
            } catch (InterruptedException e) {

            }
        }
        completedTicket = eventTicket;
        System.out.println("Scheduler: Received response from drone");
        droneAvailable = true;
        
        notifyAll();
    }

    public synchronized FireIncidentTicket completeRequest() {
        while (!droneAvailable) {
            try {
                wait();
            } catch (InterruptedException e) {

            }
        }
        droneAvailable = false;
        System.out.println("Scheduler: Sending completed ticket to fire system");
        requestsCompleted++;
        notifyAll();
        return completedTicket;
    }


    
}
