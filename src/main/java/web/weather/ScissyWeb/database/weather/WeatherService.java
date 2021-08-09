package web.weather.ScissyWeb.database.weather;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import web.weather.ScissyWeb.database.location.Location;
import web.weather.ScissyWeb.database.location.LocationRepository;
import web.weather.ScissyWeb.database.location.LocationService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class WeatherService {

    private final WeatherRepository weatherRepository;
    private final WeatherPrep weatherPrep;
    private final LocationRepository locationRepository;
    private final LocationService locationService;

    public WeatherService(WeatherRepository weatherRepository, WeatherPrep weatherPrep, LocationRepository locationRepository, LocationService locationService) {
        this.weatherRepository = weatherRepository;
        this.weatherPrep = weatherPrep;
        this.locationRepository = locationRepository;
        this.locationService = locationService;
    }

    // To get the current weather
    @Async
    public Map<String, Object> getCurrentWeatherForLocation(String locationId, String system){
        Weather weather = weatherRepository.getCurrentWeatherForLocation(locationId);
        Map<String, Object> weatherPrepared = weatherPrep.prepareMap( weather, system);
        weatherPrepared.put("sunrise", getTimeOfWeather(weather.getSunrise()));
        weatherPrepared.put("sunset", getTimeOfWeather(weather.getSunset()));
        return weatherPrepared;
    }

    // Get Hourly Forecast
    @Async
    public List<Map<String, Object>> getHourlyForecastForLocation(String locationId, String system){
        List<Weather> hourlyForecasts = weatherRepository.getHourlyForecastForLocation(locationId);
        List<Map<String, Object>> forecastsPrepared = new ArrayList<>();
        for (int i = 0; i < 6; ++i){
            Map<String, Object> hourlyForecastPrepared = weatherPrep.prepareMap(hourlyForecasts.get(i), system);
            hourlyForecastPrepared.put("time", getTimeOfWeather(hourlyForecasts.get(i).getDt()));
            forecastsPrepared.add(hourlyForecastPrepared);
        }

        return forecastsPrepared;
    }

    private String getTimeOfWeather(Long dt){
        LocalDateTime localDateTime = LocalDateTime.of(1970, 1, 1, 0, 0, 0).plusHours(1).plusSeconds(dt);
        int minute = localDateTime.getMinute();
        int hour = localDateTime.getHour();
        String minuteFormatted = minute + "";
        if (minute < 10) {
            minuteFormatted = "0" + minuteFormatted;
        }
        if (hour > 12){
            return hour + ":" + minuteFormatted + " PM";
        } else {
            return hour + ":" + minuteFormatted + " AM";
        }
    }

    // Get Daily Forecast
    @Async
    public List<Map<String, Object>> getDailyForecastForLocation(String locationId, String system){
        List<Weather> dailyForecastForLocation = weatherRepository.getDailyForecastForLocation(locationId);
        List<Map<String, Object>> forecastsPrepared = new ArrayList<>();
        for (Weather dailyForecast : dailyForecastForLocation){
            Map<String, Object> dailyForecastPrepared = weatherPrep.prepareMapForDailyForecast(dailyForecast, system);
            dailyForecastPrepared.put("date", formatDayForDailyForecast(dailyForecast.getDt()));
            System.out.println(dailyForecastPrepared);
            forecastsPrepared.add(dailyForecastPrepared);
        }
        return forecastsPrepared;
    }

    private String formatDayForDailyForecast(Long dt){
        LocalDateTime localDateTime = LocalDateTime.of(1970, 1, 1, 0, 0, 0).plusHours(1).plusSeconds(dt);
        int day = localDateTime.getDayOfMonth();
        String month = getMonth(localDateTime.getMonthValue())+ " ";
        return switch (day) {
          case 1, 21, 31 -> month + day + "st";
          case 2, 22 -> month + day + "nd";
          case 3, 23 -> month + day + "rd";
            default -> month + day + "th";
        };
    }

    private String getMonth(int month){
        return switch (month){
          case 1 -> "January";
          case 2 -> "February";
          case 3 -> "March";
          case 4 -> "April";
          case 5 -> "May";
          case 6 -> "June";
          case 7 -> "July";
          case 8 -> "August";
          case 9 -> "September";
          case 10 -> "October";
          case 11 -> "November";
          case 12 -> "December";
          default -> throw new IllegalStateException("IllegalInput");
        };
    }


    public List<Map<String, Object>> getWeatherForResearchResult(String query){
        List<Location> locations = locationService.getLocationsFollowingResearch(query);
        return getWeatherForResearch(locations, "metric");
    }


    public List<Map<String, Object>> getWeatherForIslandLocations(String island, String system){
        List<Location> locations = locationRepository.getLocationsOfIsland(island);
        return getWeatherForResearch(locations, system);
    }

    public List<Map<String, Object>> getWeatherForBailiwickLocations(String bailiwick, String system){
        List<Location> locations = locationRepository.getLocationsOfBailiwick(bailiwick);
        return getWeatherForResearch(locations, system);
    }

    public List<Map<String, Object>> getWeatherForAllLocations(String system){
        List<Location> locations = locationRepository.findAll();
        return getWeatherForResearch(locations, system);
    }



    private List<Map<String, Object>> getWeatherForResearch(List<Location> locations, String system){
        List<Map<String, Object>> listMapPrepd = new ArrayList<>();
        for (Location location : locations) {
            Map<String, Object> mapPrepared = weatherPrep.prepareMap(
                    weatherRepository.getCurrentWeatherForLocation(location.getLocationId().toString()), system
            );
            mapPrepared.put("name", location.getName());
            mapPrepared.put("island", location.getIsland());
            mapPrepared.put("bailiwick", location.getBailiwick());
            mapPrepared.put("id", location.getLocationId());
            listMapPrepd.add(mapPrepared);
        }
        return listMapPrepd;
    }
}
