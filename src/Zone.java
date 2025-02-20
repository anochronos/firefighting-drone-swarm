import java.util.Map;

public record Zone(int zoneID, Map.Entry<Integer,Integer> zoneStart, Map.Entry<Integer,Integer> zoneEnd){

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
