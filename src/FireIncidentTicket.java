/**
 * Represents a fire incident ticket with details about the event.
 *
 * @param timeStamp The timestamp of the incident in string format.
 * @param zoneID The ID of the zone where the incident occurred.
 * @param eventType The type of fire-related event (e.g., "ALARM", "SMOKE_DETECTED").
 * @param severity The severity level of the incident (e.g., "LOW", "MEDIUM", "HIGH").
 */

public record FireIncidentTicket(String timeStamp, int zoneID, String eventType, String severity){

    /**
     * Returns a string representation of the fire incident ticket.
     *
     * @return A formatted string containing incident details.
     */
    @Override
    public String toString(){
        return "FireIncidentTicket [timeStamp="+timeStamp+", " +
                "zoneID="+zoneID+", eventType="+eventType+", severity="+severity+"]";

    }
}
