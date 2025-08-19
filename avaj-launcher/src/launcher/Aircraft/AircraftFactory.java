package launcher.Aircraft;

import java.util.Random;
import java.util.UUID;

public class AircraftFactory {
    public static Flyable newAircraft(String p_type, String p_name, Coordinates p_coordinate){

        Random rand = new Random();
        int bound = 1209323821;
        return switch (p_type) {
            case "Helicopter" -> new Helicopter(rand.nextInt(bound), p_name, p_coordinate);
            case "Baloon" -> new Baloon(rand.nextInt(bound), p_name, p_coordinate);
            case "JetPlane" -> new JetPlane(rand.nextInt(bound), p_name, p_coordinate);
            default -> throw new RuntimeException("Aircraft must be Helicopter|Baloon|JetPlane, found "+p_type);
        };
    }
}
