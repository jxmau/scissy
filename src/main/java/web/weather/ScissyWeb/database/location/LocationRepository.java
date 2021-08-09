package web.weather.ScissyWeb.database.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {

    @Query(value = "SELECT * FROM location WHERE island = ?1", nativeQuery = true)
    List<Location> getLocationsOfIsland(String island);

    @Query(value = "SELECT * FROM location WHERE bailiwick = ?1", nativeQuery = true)
    List<Location> getLocationsOfBailiwick(String bailiwick);

    @Query(value = "SELECT * FROM location WHERE name LIKE ?1% OR name LIKE ?2%", nativeQuery = true)
    List<Location> getLocationFollowingResearch(String lowerCases, String capitalized);

    @Query(value = "SELECT * FROM location WHERE locationid = ?1", nativeQuery = true)
    Location getLocationById(Long locationId);
}
