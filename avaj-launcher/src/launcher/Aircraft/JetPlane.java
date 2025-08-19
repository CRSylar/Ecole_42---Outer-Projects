package launcher.Aircraft;

import launcher.FileHandler.Writer;
import launcher.Weather.WeatherTower;

public class JetPlane extends Aircraft implements Flyable {


    public JetPlane(long p_id, String p_name, Coordinates p_coordinate) {
        super(p_id, p_name, p_coordinate);
    }

    @Override
    public void updateConditions() {
        String condition = weatherTower.getWeather(this.coordinates);
        String id = "JetPlane#"+this.name+" ("+this.id+"): ";
        System.out.println("Condition for "+id+" "+condition);

        String message = "";
        switch (condition) {
            case "RAIN":
                coordinates = new Coordinates(
                        coordinates.getLongitude(),
                        coordinates.getLatitude() +5,
                        coordinates.getHeight()
                );
                message = "It's raining, increasing Latitude...";
                break;

            case "SUN":
                coordinates = new Coordinates(
                        coordinates.getLongitude(),
                        coordinates.getLatitude() + 10,
                        coordinates.getHeight() +2
                );
                message = "What a warm sun, gaining quota";
                break;
            case "FOG":
                coordinates = new Coordinates(
                        coordinates.getLongitude(),
                        coordinates.getLatitude() +1,
                        coordinates.getHeight()
                );
                message = "Solid Fog Here! reducing Latitude...";
                break;
            case "SNOW":
                coordinates = new Coordinates(
                  coordinates.getLongitude(),
                  coordinates.getLatitude(),
                  coordinates.getHeight() - 7
                );
                message = "A Raging snow storm! decreasing Height";
                break;
        }
        Writer.getInstance().write(id+message);
        if (this.coordinates.getHeight() <= 0){
            Writer.getInstance().write(id + "Landing...");
            weatherTower.unregister(this);
        }
    }

    @Override
    public void registerTower(WeatherTower p_tower) {
        if (weatherTower == null) weatherTower = p_tower;
        if (p_tower == null) throw new Error("Weather Tower cant be Null! skipping registration...");
        p_tower.register(this);
    }

   @Override
    public long getId() {
        return this.id;
    }
    @Override
    public String getName() {
        return this.name;
    }
}
