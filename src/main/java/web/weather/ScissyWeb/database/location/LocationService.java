package web.weather.ScissyWeb.database.location;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    // Get location following query
    public List<Location> getLocationsFollowingResearch(String query){
        return locationRepository.getLocationFollowingResearch(
                query.toLowerCase(), query.substring(0,1).toUpperCase() + query.substring(1));
    }

    //Get Location By Id
    @Async
    public Location getLocationInformation(Long locationId){
        return locationRepository.getLocationById(locationId);
    }

    // Get All Locations
    public List<Location> getAllLocation(){
        return locationRepository.findAll();
    }

    // Get Island's locations
    public List<Location> getIslandLocation(String island){
        return locationRepository.getLocationsOfIsland(island);
    }
}
