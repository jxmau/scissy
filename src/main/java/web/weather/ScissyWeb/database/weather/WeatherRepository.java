package web.weather.ScissyWeb.database.weather;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WeatherRepository extends JpaRepository<Weather, Long> {

    @Query(value = "SELECT * FROM weather WHERE informationtype = 'Current' AND locationid = ?1", nativeQuery = true)
    public Weather getCurrentWeatherForLocation(String locationId);

    @Query(value = "SELECT * FROM weather WHERE informationtype = 'Current'", nativeQuery = true)
    public List<Weather> getAllCurrentWeather();

    @Query(value = "SELECT * FROM weather WHERE informationtype = 'Hourly' AND locationid = ?1", nativeQuery = true)
    public List<Weather> getHourlyForecastForLocation(String locationId);

    @Query(value = "SELECT * FROM weather WHERE informationtype = 'Hourly'", nativeQuery = true)
    public List<Weather> getAllHourlyForecast();

    @Query(value = "SELECT * FROM weather WHERE informationtype = 'Daily' AND locationid = ?1", nativeQuery = true)
    public List<Weather> getDailyForecastForLocation(String locationId);

    @Query(value = "SELECT * FROM weather WHERE informationtype = 'Daily'", nativeQuery = true)
    public List<Weather> getAllDailyForecast();


}
