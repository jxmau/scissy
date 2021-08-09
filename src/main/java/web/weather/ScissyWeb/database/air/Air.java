package web.weather.ScissyWeb.database.air;

import javax.persistence.*;

@Table
@Entity
public class Air {
    @Id
    @GeneratedValue
    @Column(name = "airid") private Long airId;
    @Column(name="locationid") private String locationId;
    private String quality;
    private Double co;
    private Double no;
    private Double no2;
    private Double o3;
    private Double so2;
    private Double nh3;
    private Double pm10;
    private Double pm2_5;
    private Long dt;

    public Air() {
    }

    public Air(String locationId, String quality, Double co, Double no, Double no2, Double o3, Double so2, Double nh3, Double pm10, Double pm2_5, Long dt) {
        this.locationId = locationId;
        this.quality = quality;
        this.co = co;
        this.no = no;
        this.no2 = no2;
        this.o3 = o3;
        this.so2 = so2;
        this.nh3 = nh3;
        this.pm10 = pm10;
        this.pm2_5 = pm2_5;
        this.dt = dt;
    }

    public Long getAirId() {
        return airId;
    }

    public void setAirId(Long airId) {
        this.airId = airId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public Double getCo() {
        return co;
    }

    public void setCo(Double co) {
        this.co = co;
    }

    public Double getNo() {
        return no;
    }

    public void setNo(Double no) {
        this.no = no;
    }

    public Double getNo2() {
        return no2;
    }

    public void setNo2(Double no2) {
        this.no2 = no2;
    }

    public Double getO3() {
        return o3;
    }

    public void setO3(Double o3) {
        this.o3 = o3;
    }

    public Double getSo2() {
        return so2;
    }

    public void setSo2(Double so2) {
        this.so2 = so2;
    }

    public Double getNh3() {
        return nh3;
    }

    public void setNh3(Double nh3) {
        this.nh3 = nh3;
    }

    public Double getPm10() {
        return pm10;
    }

    public void setPm10(Double pm10) {
        this.pm10 = pm10;
    }

    public Double getPm2_5() {
        return pm2_5;
    }

    public void setPm2_5(Double pm2_5) {
        this.pm2_5 = pm2_5;
    }

    public Long getDt() {
        return dt;
    }

    public void setDt(Long dt) {
        this.dt = dt;
    }

    @Override
    public String toString() {
        return "Air{" +
                "airId=" + airId +
                ", locationId='" + locationId + '\'' +
                ", quality='" + quality + '\'' +
                ", co=" + co +
                ", no=" + no +
                ", no2=" + no2 +
                ", o3=" + o3 +
                ", so2=" + so2 +
                ", nh3=" + nh3 +
                ", pm10=" + pm10 +
                ", pm2_5=" + pm2_5 +
                ", dt=" + dt +
                '}';
    }
}
