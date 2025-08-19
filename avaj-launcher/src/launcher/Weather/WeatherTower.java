package launcher.Weather;

import launcher.Aircraft.Coordinates;
import launcher.Tower.Tower;

public class WeatherTower extends Tower {
    public String getWeather(Coordinates p_coordinates) {
        return WeatherProvider.getInstance().getCurrentWeather(p_coordinates);
    }

    public void changeWeather() {
        this.conditionChanged();
    }

}
