package web.weather.ScissyWeb.config;

import org.springframework.stereotype.Component;

@Component
public class ScissyConfig {

    public static String getOWMKey() {
        return System.getenv("SCISSY_OWM_KEY");
    }
}
