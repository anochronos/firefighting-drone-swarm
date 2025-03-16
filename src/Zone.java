import java.util.Map;
/**
 * The `Zone` record represents a fire zone with a unique ID, start coordinates, and end coordinates.
 * It provides methods to calculate the center of the zone and retrieve its start and end coordinates.
 *
 * This record is used by the `FireIncidentSubsystem` to manage fire zones and their locations.
 *
 * @param zoneID The unique identifier for the zone.
 * @param zoneStart The starting coordinates of the zone as a `Map.Entry<Integer, Integer>`.
 * @param zoneEnd The ending coordinates of the zone as a `Map.Entry<Integer, Integer>`.
 */
public record Zone(int zoneID, Map.Entry<Integer,Integer> zoneStart, Map.Entry<Integer,Integer> zoneEnd){

    /**
     * Calculates and returns the center coordinates of the zone.
     * The center is calculated as the midpoint between the start and end coordinates.
     *
     * @return A `Map.Entry<Integer, Integer>` representing the center coordinates of the zone.
     */
    public Map.Entry<Integer, Integer> getZoneCenter() {
        int centerX = (zoneStart.getKey() + zoneEnd.getKey()) / 2;
        int centerY = (zoneStart.getValue() + zoneEnd.getValue()) / 2;
        return Map.entry(centerX, centerY);
    }

    /**
     * Returns the starting coordinates of the zone.
     *
     * @return A `Map.Entry<Integer, Integer>` representing the start coordinates of the zone.
     */
    public Map.Entry<Integer, Integer> getZoneStart() { return zoneStart; }

    /**
     * Returns the ending coordinates of the zone.
     *
     * @return A `Map.Entry<Integer, Integer>` representing the end coordinates of the zone.
     */
    public Map.Entry<Integer, Integer> getZoneEnd() { return zoneEnd; }

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
