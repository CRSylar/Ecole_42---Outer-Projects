package launcher.Aircraft;

import launcher.Weather.WeatherTower;

public interface Flyable {

    public void updateConditions();
    public void registerTower(WeatherTower p_tower);

    public long getId();
    public String getName();
}
