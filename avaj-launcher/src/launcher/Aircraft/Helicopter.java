package launcher.Aircraft;

import launcher.FileHandler.Writer;
import launcher.Weather.WeatherTower;

public class Helicopter extends Aircraft implements Flyable{

    public Helicopter(long p_id, String p_name, Coordinates p_coordinate) {
        super(p_id, p_name, p_coordinate);
    }

    @Override
    public void updateConditions() {
        String condition = weatherTower.getWeather(this.coordinates);
        String id = "Helicopter#"+this.name+" ("+this.id+"): ";
        String message = "";
        switch (condition) {
            case "RAIN":
                coordinates = new Coordinates(
                        coordinates.getLongitude()+5,
                        coordinates.getLatitude(),
                        coordinates.getHeight()
                );
                message = "It's raining,i'm all wet ";
                break;

            case "SUN":
                coordinates = new Coordinates(
                        coordinates.getLongitude() + 10,
                        coordinates.getLatitude(),
                        coordinates.getHeight() +2
                );
                message = "I'm melting with this strong sun!";
                break;
            case "FOG":
                coordinates = new Coordinates(
                        coordinates.getLongitude() +1,
                        coordinates.getLatitude(),
                        coordinates.getHeight()
                );
                message = "Cant see nothing! fog everywhere";
                break;
            case "SNOW":
                coordinates = new Coordinates(
                        coordinates.getLongitude(),
                        coordinates.getLatitude(),
                        coordinates.getHeight() - 12
                );
                message = "I'm Freezing with this snow, i'll lose quota";
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
