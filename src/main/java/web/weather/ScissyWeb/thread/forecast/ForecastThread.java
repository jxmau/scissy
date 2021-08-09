package web.weather.ScissyWeb.thread.forecast;


import web.weather.ScissyWeb.database.location.Location;
import web.weather.ScissyWeb.database.location.LocationRepository;

import java.time.LocalTime;
import java.util.List;

public class ForecastThread implements Runnable{

    private final String name;
    private final Thread thread;
    private final LocationRepository locationRepository;
    private final ForecastRequest forecastRequest;

    public ForecastThread(String name, Thread thread, LocationRepository locationRepository, ForecastRequest forecastRequest) {
        this.name = name;
        this.thread = thread;
        this.locationRepository = locationRepository;
        this.forecastRequest = forecastRequest;
    }

    public void run(){

        while (true) {
            try {
                List<Location> locations = locationRepository.findAll();
                println(locations.size() + " locations have been loaded");

                for (Location location : locations){
                    println((locations.indexOf(location) + 1) + "/" + locations.size() + " | Fetching forecast for " + location.getName() + ", " + location.getBailiwick());
                    forecastRequest.request(location);
                    Thread.sleep(2000);
                }

                int sleepTime = getSleepTime();
                println("next harvest at " + LocalTime.now().plusSeconds(sleepTime));
                Thread.sleep(sleepTime * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void start(String name){
        Thread thread = new Thread( this, name);
        thread.start();
    }

    private int getSleepTime(){
        if (LocalTime.now().isBefore(LocalTime.of(8, 0))){
            return LocalTime.of(8, 0).toSecondOfDay() - LocalTime.now().toSecondOfDay();
        } else if (LocalTime.now().isBefore(LocalTime.of(16, 0))){
            return LocalTime.of(16, 0).toSecondOfDay() - LocalTime.now().toSecondOfDay();
        } else {
            // MIDNIGHT has 0 secondOfDays, so if we subtract at midnight, we'll get a negative return.
            return LocalTime.of(23, 59).toSecondOfDay() - LocalTime.now().toSecondOfDay();
        }
    }

    private void println(String message) {
        System.out.println(LocalTime.now() + " - " + this.name + " > " + message);
    }

   /*
   private void print(String message) {
        System.out.print(LocalTime.now() + " - " + this.name + " > " + message);
    }
    */
}
