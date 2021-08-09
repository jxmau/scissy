package web.weather.ScissyWeb.database.weather;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class WeatherPrep {

    public Map<String, Object> prepareMap(Weather weather, String system){
        Map<String, Object> mapPrepared = new HashMap<>();
        mapPrepared.put("condition", getConditionAbbrev(weather.getCondition()));
        mapPrepared.put("temp", temp(weather.getTemp(), system));
        mapPrepared.put("pressure", weather.getPressure());
        mapPrepared.put("humidity", weather.getHumidity());
        mapPrepared.put("windDirection", getWindOriginAbvWithSpeed(weather.getWindDir()));
        mapPrepared.put("windSpeed", getWindSpeed(weather.getWindSpeed(), system));
        mapPrepared.put("icon", getIconSource(weather.getCondition()));
        return mapPrepared;
    }

    public Map<String, Object> prepareMapForDailyForecast(Weather weather, String system){
        Map<String, Object> mapPrepared = new HashMap<>();
        mapPrepared.put("condition", getConditionAbbrev(weather.getCondition()));
        mapPrepared.put("temp", temp(weather.getTempMin(), system).replace("°C","").replace("°F", "")
                + " | " + temp(weather.getTempMax(), system));
        mapPrepared.put("pressure", weather.getPressure());
        mapPrepared.put("humidity", weather.getHumidity());
        mapPrepared.put("windDirection", getWindOriginAbvWithSpeed(weather.getWindDir()));
        mapPrepared.put("windSpeed", getWindSpeed(weather.getWindSpeed(), system));
        mapPrepared.put("icon", getIconSource(weather.getCondition()));
        return mapPrepared;
    }

    private String temp(Double temp, String system){
        return switch (system){
            case "metric" -> Math.round( (temp - 273.16) * 10.0) / 10.0 + "°C";
            case "imperial" -> Math.round(((temp - 273.15) * 9/5 + 32) * 10.0) / 10.0 + "°F";
            default -> temp + "°K";
        };
    }

    // To get one decimal, we multiply by ten directly with the factor before dividing by 10.0
    private String getWindSpeed(Double speed, String system){
        return switch (system){
          case "metric" -> Math.round(speed * 36) / 10.0 + " kph";
          case "imperial" -> Math.round(speed * 22.37) / 10.0 + " mph";
            default -> speed + "mps";
        };
    }


    public static String getWindOriginAbvWithSpeed(Double windDirection) {

        if (windDirection > 337.5 || windDirection < 22.5) {
            return windDirection + "° N";
        } else if (isInBetween(22.5, windDirection, 67.5)) {
            return windDirection + "° NW";
        } else if (isInBetween(65.5, windDirection, 112.5)) {
            return windDirection + "° W";
        } else if (isInBetween(112.5, windDirection, 157.5)) {
            return windDirection + "° SW";
        } else if (isInBetween(157.5, windDirection, 202.5)) {
            return windDirection + "° S";
        } else if (isInBetween(202.5, windDirection, 247.5)) {
            return windDirection + "° SE";
        } else if (isInBetween(247.5, windDirection, 292.5)) {
            return windDirection + "° E";
        }  else {
            return windDirection + "° NE";
        }
    }

    private static boolean isInBetween(Double lowLimit, Double windDirection, Double highLimit){
        return windDirection >= lowLimit && windDirection < highLimit;
    }

    private String getIconSource(String icon){
        return switch (icon){
            case "01d" -> "/icons/weather/016-sun.svg";
            case "02d", "03d" -> "/icons/weather/011-cloudy.svg";
           // case "03d" -> "/icons/006-cloud-1.svg"; TODO add slightly overcast icon
            case "04d" -> "/icons/weather/015-cloud.svg";
            case "09d", "10d" -> "/icons/weather/010-raining.svg";
            case "11d" -> "/icons/weather/012-storm.svg";
            //case "13d" -> "/icons/016-sun.svg";
            //case "50d" -> "/icons/016-sun.svg";
            default -> "/icons/weather/016-sun.svg";
        };
    }

    private String getConditionAbbrev(String icon){
        return switch (icon){
            case "01d" -> "Sunny";
            case "02d" -> "Overcast";
            case "03d", "04d" -> "Slight Overcast";
            case "09d", "10d" -> "Rain";
            case "11d" -> "Thunderstorm";
            case "13d" -> "Snow";
            case "50d" -> "Mist";
            default -> "Unknown";
        };
    }
}
