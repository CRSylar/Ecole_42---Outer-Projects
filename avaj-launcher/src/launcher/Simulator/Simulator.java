package launcher.Simulator;

import java.io.*;
import java.lang.RuntimeException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import launcher.Aircraft.AircraftFactory;
import launcher.Aircraft.Coordinates;
import launcher.Aircraft.Flyable;
import launcher.Errors.*;
import launcher.FileHandler.MyReader;
import launcher.FileHandler.Writer;
import launcher.Weather.WeatherTower;

public class Simulator
{
    private static final WeatherTower weaterTower = new WeatherTower();
    private static final List<Flyable> flyableList = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        // If Got more or less than 1 args throw and exit
        if (args.length != 1)
            throw new UsageException("Usage Error, ");
        try {
            MyReader reader = new MyReader(args[0]);
            String[] readerRow = reader.getRow();
            if (readerRow == null) throw new UsageException("Empty File! ");
            int simulation = Integer.parseInt(readerRow[0]);

            System.out.println("Debug, Simulation: "+simulation);
            if (simulation <= 0)
                throw new RuntimeException("The number of simulations must be greater than zero");

            while ((readerRow = reader.getRow()) != null) {
                if (readerRow.length != 5)
                    throw new UsageException("Error: Each line of the file, except the first one, should look like this: [TYPE NAME LONGITUDE LATITUDE HEIGHT]");

                Flyable flyable = AircraftFactory.newAircraft(
                        readerRow[0],
                        readerRow[1],
                        new Coordinates(
                                Integer.parseInt(readerRow[2]),
                                Integer.parseInt(readerRow[3]),
                                Integer.parseInt(readerRow[4])
                        )
                );
                flyableList.add(flyable);
                flyable.registerTower(weaterTower);
            }
            reader.closeFile();
            for (int i = 1; i <= simulation; i++) {
                Writer.getInstance().write("\nStarting Simulation n"+i+ ":");
                weaterTower.changeWeather();
            }


        }  catch (Exception exception) {
            System.out.println("Nel catch");
            System.out.println(exception);
        }
    };
};