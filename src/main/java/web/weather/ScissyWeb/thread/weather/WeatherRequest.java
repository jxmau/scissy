package web.weather.ScissyWeb.thread.weather;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;
import web.weather.ScissyWeb.database.location.Location;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class WeatherRequest {

    private final WeatherParser weatherParser;

    public WeatherRequest(WeatherParser weatherParser) {
        this.weatherParser = weatherParser;
    }

    public void request(Location location){

        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet(getUrl(location));

            HttpResponse response = httpClient.execute(request);
            String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

            JSONParser parser = new JSONParser();
            Map<String, Object> responseMap = (Map<String, Object>) parser.parse(responseBody);
            weatherParser.parse(responseMap, location);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

    }

    public String getUrl(Location location){
        return "https://api.openweathermap.org/data/2.5/weather?lat=" + location.getLatitude()
                +"&lon=" + location.getLongitude() +"&appid=7e85f6c979a75f9eda0257e640b239f1";
    }
}
