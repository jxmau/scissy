package web.weather.ScissyWeb.thread.air;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;
import web.weather.ScissyWeb.config.ScissyConfig;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Component
public class AirRequest {

    private final AirParser airParser;

    public AirRequest(AirParser airParser) {
        this.airParser = airParser;
    }

    public void request(String locationId, String lat, String lon){
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet(getUrl(lat, lon));

            HttpResponse response = httpClient.execute(request);
            String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

            JSONParser parser = new JSONParser();
            Map<String, Object> responseRaw = (Map<String, Object>) parser.parse(responseBody);
            List<Map<String, Object>> responseMap = (List<Map<String, Object>>) responseRaw.get("list");

            airParser.parseMap(locationId, responseMap.get(0));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

    }

    public String getUrl(String lat, String lon){
        return "http://api.openweathermap.org/data/2.5/air_pollution?lat="+ lat + "&lon=" + lon + "&appid=" + ScissyConfig.getOWMKey() ;
    }
}
