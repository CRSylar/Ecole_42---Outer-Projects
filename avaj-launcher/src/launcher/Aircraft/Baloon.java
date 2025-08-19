package launcher.Aircraft;

import launcher.FileHandler.Writer;
import launcher.Weather.WeatherTower;

public class Baloon extends Aircraft implements Flyable{

    public Baloon(long p_id, String p_name, Coordinates p_coordinate) {
        super(p_id, p_name, p_coordinate);
    }

    @Override
    public void updateConditions() {
        String condition = weatherTower.getWeather(this.coordinates);
        String id = "Baloon#"+this.name+" ("+this.id+"): ";
        String message = "";
        switch (condition) {
            case "RAIN":
                coordinates = new Coordinates(
                        coordinates.getLongitude(),
                        coordinates.getLatitude(),
                        coordinates.getHeight() -5
                );
                message = "It's raining, must watch out for thunders";
                break;

            case "SUN":
                coordinates = new Coordinates(
                        coordinates.getLongitude() + 2,
                        coordinates.getLatitude(),
                        coordinates.getHeight() + 4
                );
                message = "It's Sunny, let take some pictures!";
                break;
            case "FOG":
                coordinates = new Coordinates(
                        coordinates.getLongitude(),
                        coordinates.getLatitude(),
                        coordinates.getHeight() - 3
                );
                message = "I feel like a stone in a pond...";
                break;
            case "SNOW":
                coordinates = new Coordinates(
                        coordinates.getLongitude(),
                        coordinates.getLatitude(),
                        coordinates.getHeight() - 15
                );
                message = "it's Snowing! i'm not prepared to this!";
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
