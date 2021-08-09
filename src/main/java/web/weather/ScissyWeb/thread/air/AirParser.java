package web.weather.ScissyWeb.thread.air;

import org.springframework.stereotype.Component;
import web.weather.ScissyWeb.database.air.Air;
import web.weather.ScissyWeb.database.air.AirRepository;


import java.util.HashMap;
import java.util.Map;

@Component
public class AirParser {

    private final AirRepository airRepository;

    public AirParser(AirRepository airRepository) {
        this.airRepository = airRepository;
    }

    public void parseMap(String locationId, Map<String, Object> responseMap){
        Map<String, Object> main = (Map<String, Object>) responseMap.get("main");
        String quality = main.get("aqi").toString();
        Map<String, Object> pollutants = (Map<String, Object>) responseMap.get("components");
        Map<String, Double> pollutantsVerified = verifyDataType(pollutants);

        Air airInfo =  airRepository.getAirConditionForLocation(locationId);

        if (airInfo == null) {
            Air newEntry = new Air(locationId, quality,  pollutantsVerified.get("co"), pollutantsVerified.get("no"),
                    pollutantsVerified.get("no2"), pollutantsVerified.get("o3"),
            pollutantsVerified.get("so2"), pollutantsVerified.get("nh3"),
            pollutantsVerified.get("pm10"), pollutantsVerified.get("pm2_5"), (Long) responseMap.get("dt"));
            airRepository.save(newEntry);
        } else {
            airInfo.setQuality(quality);
            airInfo.setCo(pollutantsVerified.get("co"));
            airInfo.setNo(pollutantsVerified.get("no"));
            airInfo.setNo2(pollutantsVerified.get("no2"));
            airInfo.setO3(pollutantsVerified.get("o3"));
            airInfo.setSo2(pollutantsVerified.get("so2"));
            airInfo.setNh3(pollutantsVerified.get("nh3"));
            airInfo.setPm10(pollutantsVerified.get("pm10"));
            airInfo.setPm2_5(pollutantsVerified.get("pm2_5"));
            airInfo.setDt((Long) responseMap.get("dt"));
            airRepository.save(airInfo);
        }

    }

    private static Map<String, Double> verifyDataType(Map<String, Object> mapReceived){
        Map<String, Double> pollutantsVerified = new HashMap<>();
        for (String component : mapReceived.keySet()){
            pollutantsVerified.put(component, Double.valueOf(mapReceived.get(component).toString()));
        }
        return pollutantsVerified;
    }

}
