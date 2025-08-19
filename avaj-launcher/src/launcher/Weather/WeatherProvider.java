package launcher.Weather;

import launcher.Aircraft.Coordinates;

import java.util.Date;
import java.util.Random;

public class WeatherProvider {
    private WeatherProvider() {
        System.out.println("Weather Provider Singleton Created");
    }
    private static final String[] weather = {"RAIN", "FOG", "SUN", "SNOW"};
    // Helper Nested Class to Ensure the Singleton
    private static class SingletonHelper {
        private static final WeatherProvider instance = new WeatherProvider();
    }
    public String getCurrentWeather(Coordinates p_coordinates) {
       int rand = (p_coordinates.getHeight()+
               p_coordinates.getLongitude()+
               p_coordinates.getLatitude()) +
               new Random().nextInt(100);
       return weather[rand % 4];
    }

    public static WeatherProvider getInstance() {
        return SingletonHelper.instance;
    }
}
