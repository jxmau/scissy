package web.weather.ScissyWeb.thread.cleaner;

import org.springframework.stereotype.Component;
import web.weather.ScissyWeb.database.weather.Weather;
import web.weather.ScissyWeb.database.weather.WeatherRepository;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Component
public class Cleaner implements Runnable{

    private final WeatherRepository weatherRepository;

    public Cleaner(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public void run(){
        List<Weather> hourlyForecasts = weatherRepository.getAllHourlyForecast();
        int i = 0;
        for (Weather forecast : hourlyForecasts) {
            if (LocalDateTime.ofEpochSecond(forecast.getDt(), 0, ZoneOffset.ofHours(1))
                    .isBefore(LocalDateTime.now(Clock.systemUTC()).plusHours(1))) {
                weatherRepository.delete(forecast);
                ++i;
            }
        }
        System.out.println("Charon " + i +" entries deleted");
    }

    public void start(){
        Thread thread = new Thread(this, "Charon");
        thread.start();
    }

}
