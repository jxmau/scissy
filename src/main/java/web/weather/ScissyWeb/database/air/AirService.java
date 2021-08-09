package web.weather.ScissyWeb.database.air;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AirService {

    private final AirRepository airRepository;
    private final AirPrep airPrep;

    public AirService(AirRepository airRepository, AirPrep airPrep) {
        this.airRepository = airRepository;
        this.airPrep = airPrep;
    }

    //Get Current Air Condition
    @Async
    public Map<String, Object> getCurrentAirCondition(String locationid){
        return airPrep.prepareMapForCurrentAirCondition(airRepository.getAirConditionForLocation(locationid));
    }
}
