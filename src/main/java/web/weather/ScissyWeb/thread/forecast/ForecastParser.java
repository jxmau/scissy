package web.weather.ScissyWeb.thread.forecast;


import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;
import web.weather.ScissyWeb.database.location.Location;
import web.weather.ScissyWeb.database.weather.Weather;
import web.weather.ScissyWeb.database.weather.WeatherRepository;

import java.util.List;
import java.util.Map;

@Component
public class ForecastParser {

    private final WeatherRepository weatherRepository;

    public ForecastParser(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    protected void parseList(List<Map<String, Object>> hourlyForecastList,
                             List<Map<String, Object>> dailyForecastList, Location location) {

        List<Weather> forecastInDatabase = weatherRepository.
                getHourlyForecastForLocation(location.getLocationId().toString());
        forecastInDatabase.addAll(weatherRepository.getDailyForecastForLocation(location.getLocationId().toString()));
        if (forecastInDatabase != null){
            weatherRepository.deleteAllInBatch(forecastInDatabase);
        }

        for (Map<String, Object> hourlyForecast : hourlyForecastList){
            try {
                JSONParser jackson = new JSONParser();
                JSONArray jsonArray = (JSONArray) hourlyForecast.get("weather");
                List<Map<String, Object>> weather = (List<Map<String, Object>>) jackson.parse(jsonArray.toString());
                saveHourlyForecast(hourlyForecast, weather.get(0), location);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        for (Map<String, Object> dailyForecast : dailyForecastList){
            try {
                JSONParser jackson = new JSONParser();
                JSONArray jsonArray = (JSONArray) dailyForecast.get("weather");
                List<Map<String, Object>> weather = (List<Map<String, Object>>) jackson.parse(jsonArray.toString());
                saveDailyForecast(dailyForecast, weather.get(0), location);
            } catch (ParseException e) {
                e.printStackTrace();
            }


        }

    }

    private void saveHourlyForecast(Map<String, Object> hourlyForecast, Map<String, Object> weather, Location location) {
        Weather hourlyWeather = new Weather(location.getLocationId().toString(), weather.get("icon").toString(),
                getDouble(hourlyForecast.get("feels_like")), null, null, (Long) hourlyForecast.get("pressure"), (Long) hourlyForecast.get("humidity"),
                getDouble(hourlyForecast.get("wind_speed")), getDouble(hourlyForecast.get("wind_deg")),
                "Hourly", (Long) hourlyForecast.get("dt"), null, null);
        weatherRepository.save(hourlyWeather);
    }

    private void saveDailyForecast(Map<String, Object> dailyForecast, Map<String, Object> weather, Location location) {
        Map<String, Object> temp = (Map<String, Object>) dailyForecast.get("temp");
        Weather hourlyWeather = new Weather(location.getLocationId().toString(), weather.get("icon").toString(), null,
                getDouble(temp.get("max")), getDouble(temp.get("min")), (Long) dailyForecast.get("pressure"), (Long) dailyForecast.get("humidity"),
                getDouble(dailyForecast.get("wind_speed")), getDouble(dailyForecast.get("wind_deg")),
                "Daily", (Long) dailyForecast.get("dt"), null, null);
        weatherRepository.save(hourlyWeather);
    }

    private Double getDouble(Object objet){
        return Double.valueOf(objet.toString());
    }

}
