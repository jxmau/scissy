package web.weather.ScissyWeb.database.air;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AirPrep {

    public Map<String, Object> prepareMapForCurrentAirCondition(Air air){
        Map<String, Object> mapPrepared = new HashMap<>();
        mapPrepared.put("quality", getAirQuality(air.getQuality()));
        mapPrepared.put("co", air.getCo());
        mapPrepared.put("no", air.getNo());
        mapPrepared.put("no2", air.getNo2());
        mapPrepared.put("o3", air.getO3());
        mapPrepared.put("so2", air.getSo2());
        mapPrepared.put("nh3", air.getNh3());
        mapPrepared.put("pm10", air.getPm10());
        mapPrepared.put("pm2_5", air.getPm2_5());
        return mapPrepared;
    }

    private String getAirQuality(String quality){
        return switch (quality) {
            case "1" ->  "Good Condition";
            case "2" ->  "Moderate-to-Good Condition";
            case "3" ->  "Moderate Condition";
            case "4" ->  "Poor Condition";
            case "5" ->  "Bad Condition";
            default -> "Unknown";
        };
    }
}
