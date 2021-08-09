package web.weather.ScissyWeb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import web.weather.ScissyWeb.database.location.Location;
import web.weather.ScissyWeb.database.location.LocationService;
import web.weather.ScissyWeb.database.weather.WeatherService;

import java.util.List;

@Controller
public class LocationController {

    public final LocationService locationService;
    private final WeatherService weatherService;

    public LocationController(LocationService locationService, WeatherService weatherService) {
        this.locationService = locationService;
        this.weatherService = weatherService;
    }

    @GetMapping("/locations")
    public String viewLocations(@RequestParam(name = "island", required = false, defaultValue = "N/A") String island,
                                @RequestParam(name = "bailiwick", required = false, defaultValue ="N/A") String bailiwick,
                                @RequestParam(name = "system", required = false, defaultValue ="metric") String system,
                                Model model){

        if (!island.equals("N/A") && bailiwick.equals("N/A") ) {
            model.addAttribute("locations", weatherService.getWeatherForIslandLocations(island, system));
            model.addAttribute("title", "Locations for the island of " + island);
        } else if (island.equals("N/A") && !bailiwick.equals("N/A") ) {
            model.addAttribute("locations", weatherService.getWeatherForBailiwickLocations(bailiwick, system));
            model.addAttribute("title", "Locations for the Bailiwick of " + bailiwick);
        } else {
            model.addAttribute("locations", weatherService.getWeatherForAllLocations(system));
            model.addAttribute("title", "");
        }

        return "locations";
    }

    @GetMapping("locations/search")
    public ModelAndView searchALocation(@RequestParam(name = "q", required = true) String query, Model model) {
        List<Location> locations = locationService.getLocationsFollowingResearch(query);
        if (locations == null) {
            return new ModelAndView("redirect:/locations");
        } else if (locations.size() == 1){
            return new ModelAndView("redirect:/weather?id=" + locations.get(0).getLocationId());
        } else {
            return new ModelAndView("redirect:/locations/search-result?q=" + query);
        }

    }

    @GetMapping("locations/search-result")
    public String viewSearchResult(@RequestParam(name = "q", required = true) String query, Model model) {
        model.addAttribute("locations", weatherService.getWeatherForResearchResult(query));
        return "locations";
    }
}
