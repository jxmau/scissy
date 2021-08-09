package web.weather.ScissyWeb.thread.forecast;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;
import web.weather.ScissyWeb.database.location.Location;
import web.weather.ScissyWeb.database.location.LocationRepository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Component
public class ForecastRequest {

    private final ForecastParser forecastParser;
    private final LocationRepository locationRepository;

    public ForecastRequest(ForecastParser forecastParser, LocationRepository locationRepository) {
        this.forecastParser = forecastParser;
        this.locationRepository = locationRepository;
    }

    public void request(Location location){
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet(getUrl(location));

            HttpResponse response = httpClient.execute(request);
            String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(responseBody);
            JSONArray jsonArray = (JSONArray) json.get("hourly");
            List<Map<String, Object>> hourlyForecastList = (List<Map<String, Object>>) parser.parse(jsonArray.toString());
            JSONArray jsonArrayTwo = (JSONArray) json.get("daily");
            List<Map<String, Object>> dailyForecastList = (List<Map<String, Object>>) parser.parse(jsonArrayTwo.toString());

            forecastParser.parseList(hourlyForecastList, dailyForecastList, location);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private String getUrl(Location location){
        https://api.openweathermap.org/data/2.5/onecall?lat={lat}&lon={lon}&exclude={part}&appid={API key}
        return "https://api.openweathermap.org/data/2.5/onecall?lat=" + location.getLatitude()
                +"&lon=" + location.getLongitude() +"&exclude=current&appid=7e85f6c979a75f9eda0257e640b239f1";
    }}
