package web.weather.ScissyWeb.database.location;

import javax.persistence.*;
import java.time.Clock;
import java.time.LocalDateTime;

@Entity
@Table
public class Location {
    @Id
    @GeneratedValue
    @Column(name = "locationid") private Long locationId;
    private String name;
    private String bailiwick;
    private String island;
    private String latitude;
    private String longitude;

    public Location(Long locationId, String name, String bailiwick, String island, String latitude, String longitude) {
        this.locationId = locationId;
        this.name = name;
        this.bailiwick = bailiwick;
        this.island = island;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location() {
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBailiwick() {
        return bailiwick;
    }

    public void setBailiwick(String bailiwick) {
        this.bailiwick = bailiwick;
    }

    public String getIsland() {
        return island;
    }

    public void setIsland(String island) {
        this.island = island;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPoliticalLocation(){
        if (bailiwick.equals(island)) {
            return bailiwick;
        } else {
            return island + ", " + bailiwick;
        }
    }

    public String getLocalTime(){
        LocalDateTime localDateTime = LocalDateTime.now(Clock.systemUTC()).plusHours(1);
        int minute = localDateTime.getMinute();
        int hour = localDateTime.getHour();
        String minuteFormatted = minute + "";
        if (minute < 10) {
            minuteFormatted = "0" + minuteFormatted;
        }
        if (hour > 12){
            return hour + ":" + minuteFormatted + " PM";
        } else {
            return hour + ":" + minuteFormatted + " AM";
        }
    }

    @Override
    public String toString() {
        return "Location{" +
                "locationId=" + locationId +
                ", name='" + name + '\'' +
                ", bailiwick='" + bailiwick + '\'' +
                ", island='" + island + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
