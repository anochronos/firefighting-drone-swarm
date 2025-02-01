
public record FireIncidentTicket(String timeStamp, int zoneID, String eventType, String severity){
    @Override
    public String toString(){
        return "FireIncidentTicket [timeStamp="+timeStamp+", " +
                "zoneID="+zoneID+", eventType="+eventType+", severity="+severity+"]";

    }

}

