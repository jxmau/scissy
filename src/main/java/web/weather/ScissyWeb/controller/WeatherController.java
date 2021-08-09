package web.weather.ScissyWeb.controller;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.weather.ScissyWeb.database.air.AirService;
import web.weather.ScissyWeb.database.location.Location;
import web.weather.ScissyWeb.database.location.LocationService;
import web.weather.ScissyWeb.database.weather.WeatherService;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

@Controller
public class WeatherController {

    private final WeatherService weatherService;
    private final LocationService locationService;
    private final AirService airService;

    public WeatherController(WeatherService weatherService, LocationService locationService, AirService airService) {
        this.weatherService = weatherService;
        this.locationService = locationService;
        this.airService = airService;
    }

    @GetMapping("/weather")
    public String weather(@RequestParam(name="id", required = false, defaultValue = "1") String locationId,
                          @RequestParam(name="system", required = false, defaultValue = "metric") String system,
                          Model model) {
        Map<String, Object> weather = weatherService.getCurrentWeatherForLocation(locationId, system);
        Location location = locationService.getLocationInformation(formatLocationId(locationId));
        Map<String, Object> air = airService.getCurrentAirCondition(locationId);
        model.addAttribute("weather", weather);
        model.addAttribute("location", location);
        model.addAttribute("icon-source", weather.get("iconSource"));
        model.addAttribute("air", air);
        model.addAttribute("weathers", weatherService.getHourlyForecastForLocation(locationId, system));
        model.addAttribute("dailyForecasts", weatherService.getDailyForecastForLocation(locationId, system));
        return "weather";
    }

    private Long formatLocationId(String locationId){
        return Math.round(Double.parseDouble(locationId));
    }

}
