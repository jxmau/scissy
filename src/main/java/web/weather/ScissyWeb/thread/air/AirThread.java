package web.weather.ScissyWeb.thread.air;


import web.weather.ScissyWeb.database.location.Location;
import web.weather.ScissyWeb.database.location.LocationRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class AirThread implements Runnable{

    private final String name;
    private final Thread t;
    private final LocationRepository locationRepository;
    private final AirRequest airRequest;
    private int sleepTime = 0;

    public AirThread(String name, Thread t, LocationRepository locationRepository, AirRequest airRequest) {
        this.name = name;
        this.t = t;
        this.locationRepository = locationRepository;
        this.airRequest = airRequest;
        getSleepTimeDuration();
    }

    public void run(){

        while (true) {



            try {
                getSleepTimeDuration();
                List<Location> locations = locationRepository.findAll();
                println( locations.size() + " locations have been loaded.");

                for (Location location : locations) {
                    println((locations.indexOf(location)+1) + "/" + locations.size() + " | Fetching the Air Pollution Information for " + location.getName()  + ", " + location.getBailiwick());
                    airRequest.request(location.getLocationId().toString(), location.getLatitude(), location.getLongitude());
                    Thread.sleep(2500);
                }

                println("Next harvest at " + LocalTime.now().plusSeconds(sleepTime/1000));
                Thread.sleep(sleepTime);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void start(String name){
        Thread thread = new Thread(this, name);
        thread.start();
    }

    private void getSleepTimeDuration(){
        LocalTime time = LocalDateTime.now().toLocalTime();
        if (time.isBefore(LocalTime.of(5, 0)) || time.isAfter(LocalTime.of(23, 0))) {
            sleepTime = 2400000;
        } else {
            sleepTime = 1200000;
        }
    }

    private void println(String message){
        System.out.println(LocalTime.now() + " - Éther > " + message);
    }

    private void print(String message){
        System.out.print(LocalTime.now() + " - Éther > " + message);
    }

    public int getSleepTime() {
        return sleepTime;
    }

}
