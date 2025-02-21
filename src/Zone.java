import java.util.Map;

public record Zone(int zoneID, Map.Entry<Integer,Integer> zoneStart, Map.Entry<Integer,Integer> zoneEnd){

    public Map.Entry<Integer, Integer> getZoneCenter() {
        int centerX = (zoneStart.getKey() + zoneEnd.getKey()) / 2;
        int centerY = (zoneStart.getValue() + zoneEnd.getValue()) / 2;
        return Map.entry(centerX, centerY);
    }

    /**
     * Returns a string representation of the fire zone.
     *
     * @return A formatted string containing incident details.
     */
    @Override
    public String toString(){
        return "[Zone ID="+zoneID+", Zone Start="+zoneStart+", Zone End="+zoneEnd+"]";
    }

}
