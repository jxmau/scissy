package web.weather.ScissyWeb.database.air;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AirRepository extends JpaRepository<Air, Long> {

    @Query(value = "SELECT * FROM air WHERE locationid = ?1", nativeQuery = true)
    Air getAirConditionForLocation(String locationId);
}
