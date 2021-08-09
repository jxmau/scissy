package web.weather.ScissyWeb.database.weather;

import javax.persistence.*;

@Entity
@Table
public class Weather {
    @Id
    @GeneratedValue
    @Column(name = "weatherid") private Long weatherId;
    @Column(name = "locationid") private  String locationId;
    private  String condition;
    private  Double temp;
    private  Double tempMax;
    private  Double tempMin;
    private  Long pressure;
    private  Long humidity;
    @Column(name = "windspeed") private Double windSpeed;
    @Column(name = "winddir") private Double windDir;
    @Column(name = "informationtype") private String informationType;
    private Long dt;
    private Long sunrise;
    private Long sunset;

    public Weather() {
    }

    public Weather(String locationId, String condition, Double temp,  Double tempMax,  Double tempMin, Long pressure, Long humidity, Double windSpeed, Double windDir, String informationType, Long dt, Long sunrise, Long sunset) {
        this.locationId = locationId;
        this.condition = condition;
        this.temp = temp;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windDir = windDir;
        this.informationType = informationType;
        this.dt = dt;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    public Long getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(Long weatherId) {
        this.weatherId = weatherId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Long getPressure() {
        return pressure;
    }

    public void setPressure(Long pressure) {
        this.pressure = pressure;
    }

    public Long getHumidity() {
        return humidity;
    }

    public void setHumidity(Long humidity) {
        this.humidity = humidity;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Double getWindDir() {
        return windDir;
    }

    public void setWindDir(Double windDir) {
        this.windDir = windDir;
    }

    public String getInformationType() {
        return informationType;
    }

    public void setInformationType(String informationType) {
        this.informationType = informationType;
    }

    public Long getDt() {
        return dt;
    }

    public void setDt(Long dt) {
        this.dt = dt;
    }

    public Double getTempMax() {
        return tempMax;
    }

    public void setTempMax(Double tempMax) {
        this.tempMax = tempMax;
    }

    public Double getTempMin() {
        return tempMin;
    }

    public void setTempMin(Double tempMin) {
        this.tempMin = tempMin;
    }

    public Long getSunrise() {
        return sunrise;
    }

    public void setSunrise(Long sunrise) {
        this.sunrise = sunrise;
    }

    public Long getSunset() {
        return sunset;
    }

    public void setSunset(Long sunset) {
        this.sunset = sunset;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "weatherId=" + weatherId +
                ", locationId='" + locationId + '\'' +
                ", condition='" + condition + '\'' +
                ", temp=" + temp +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", windSpeed=" + windSpeed +
                ", windDir=" + windDir +
                ", informationType='" + informationType + '\'' +
                ", dt=" + dt +
                '}';
    }
}
