package web.weather.ScissyWeb.thread.weather;

import org.springframework.stereotype.Component;

import web.weather.ScissyWeb.database.location.Location;
import web.weather.ScissyWeb.database.weather.Weather;
import web.weather.ScissyWeb.database.weather.WeatherRepository;

import java.util.List;
import java.util.Map;

@Component
public class WeatherParser {

    private final WeatherRepository weatherRepository;

    public WeatherParser(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public void parse(Map<String, Object> mapReceived, Location location){
        List<Map<String, Object>> weatherMapList = (List<Map<String, Object>>) mapReceived.get("weather");
        Map<String, Object> weatherMap = weatherMapList.get(0);
        Map<String, Object> main = (Map<String, Object>) mapReceived.get("main");
        Map<String, Object> wind = (Map<String, Object>) mapReceived.get("wind");
        Map<String, Object> sys = (Map<String, Object>) mapReceived.get("sys");

        Weather currentWeather = weatherRepository.getCurrentWeatherForLocation(location.getLocationId().toString());

        if (currentWeather == null ){
            Weather newWeather = new Weather(location.getLocationId().toString(), weatherMap.get("icon").toString(),
                     getDouble(main.get("feels_like")), null, null, (Long) main.get("pressure"),
                    (Long) main.get("humidity"), getDouble(wind.get("speed")), getDouble(wind.get("deg")), "Current",
                    (Long) mapReceived.get("dt"), (Long) sys.get("sunrise"), (Long) sys.get("sunset"));
            weatherRepository.save(newWeather);
        } else {
            currentWeather.setCondition(weatherMap.get("icon").toString());
            currentWeather.setTemp(getDouble(main.get("feels_like")));
            currentWeather.setPressure((Long) main.get("pressure"));
            currentWeather.setHumidity((Long) main.get("humidity"));
            currentWeather.setWindSpeed(getDouble(wind.get("speed")));
            currentWeather.setWindDir(getDouble(wind.get("deg")));
            currentWeather.setDt((Long) mapReceived.get("dt"));
            currentWeather.setSunrise((Long) sys.get("sunrise"));
            currentWeather.setSunset((Long) sys.get("sunset"));
            weatherRepository.save(currentWeather);

        }
    }

    private Double getDouble(Object objet){
        return Double.valueOf(objet.toString());
    }
}
