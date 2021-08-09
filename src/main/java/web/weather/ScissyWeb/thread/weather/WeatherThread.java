package web.weather.ScissyWeb.thread.weather;


import org.springframework.context.annotation.Bean;
import web.weather.ScissyWeb.database.location.Location;
import web.weather.ScissyWeb.database.location.LocationRepository;
import web.weather.ScissyWeb.database.weather.WeatherRepository;
import web.weather.ScissyWeb.thread.cleaner.Cleaner;

import java.time.LocalTime;
import java.util.List;

public class WeatherThread implements Runnable {

    private final String name;
    private final Thread t;
    private final LocationRepository locationRepository;
    private final WeatherRequest weatherRequest;
    private final WeatherRepository weatherRepository;

    public WeatherThread(String name, Thread t, LocationRepository locationRepository, WeatherRequest weatherRequest, WeatherRepository weatherRepository) {
        this.name = name;
        this.t = t;
        this.locationRepository = locationRepository;
        this.weatherRequest = weatherRequest;
        this.weatherRepository = weatherRepository;
    }

    public void run(){
        try {


            while (true) {
                // new Cleaner(weatherRepository).start();
                List<Location> locations = locationRepository.findAll();
                println(locations.size() + " locations have been loaded.");

                for (Location location : locations) {
                    println((locations.indexOf(location) + 1) + "/" + locations.size() + " | Fetching the Current Weather Information for " + location.getName() + ", " + location.getBailiwick() );
                    weatherRequest.request(location);

                    Thread.sleep(2000);
                }
                int sleepTime = getSleepTime();
                println("Next harvest at " + LocalTime.now().plusSeconds(sleepTime/1000));
                Thread.sleep(sleepTime);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void start(String name){
        Thread thread = new Thread(this, name);
        thread.start();
    }

    public int getSleepTime(){
        if (LocalTime.now().isBefore(LocalTime.of(6, 0)) || LocalTime.now().isAfter(LocalTime.of(23, 0))) {
            return 1800000;
        } else {
            return 900000;
        }
    }

    public void println(String message){
        System.out.println(LocalTime.now() + " - " + this.name + "  > " + message);
    }

    public void print(String message){
        System.out.print(LocalTime.now() + " - " + this.name + "  > " + message);
    }
}
