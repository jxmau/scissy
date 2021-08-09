package web.weather.ScissyWeb.config;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import web.weather.ScissyWeb.database.location.LocationRepository;
import web.weather.ScissyWeb.database.weather.WeatherRepository;
import web.weather.ScissyWeb.thread.air.AirRequest;
import web.weather.ScissyWeb.thread.air.AirThread;
import web.weather.ScissyWeb.thread.forecast.ForecastRequest;
import web.weather.ScissyWeb.thread.forecast.ForecastThread;
import web.weather.ScissyWeb.thread.weather.WeatherRequest;
import web.weather.ScissyWeb.thread.weather.WeatherThread;


@Component
public class StartupHouseKepper implements ApplicationListener<ContextRefreshedEvent> {

    private final LocationRepository locationRepository;
    private final AirRequest airRequest;
    private final WeatherRequest weatherRequest;
    private final WeatherRepository weatherRepository;
    private final ForecastRequest forecastRequest;

    public StartupHouseKepper(LocationRepository locationRepository, AirRequest airRequest, WeatherRequest weatherRequest, WeatherRepository weatherRepository, ForecastRequest forecastRequest) {
        this.locationRepository = locationRepository;
        this.airRequest = airRequest;
        this.weatherRequest = weatherRequest;
        this.weatherRepository = weatherRepository;
        this.forecastRequest = forecastRequest;
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent contextRefreshedEvent){
        new AirThread("Éther", new Thread(), locationRepository, airRequest).start("Current Air Condition Thread");
        new WeatherThread("Zeus", new Thread(), locationRepository, weatherRequest, weatherRepository).start("Current Weather Thread");
        new ForecastThread("Janus", new Thread(), locationRepository, forecastRequest).start("Forecast Weather Thread");

    }
}
