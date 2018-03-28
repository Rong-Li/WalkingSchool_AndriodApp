package ca.sfu.Navy.walkinggroup.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by kfhg on 2018/3/27.
 */

public class GpsLocation {
    private Double lat;
    private Double lng;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date timestamp;

    public Double getLat() {
        return lat;
    }
    public Double getLng() {
        return lng;
    }
    public Date getTimestamp() {
        return timestamp;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
    public void setLng(Double lng) {
        this.lng = lng;
    }
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "GpsLocation{" +
                "lat=" + lat +
                ", lng='" + lng  +
                ", timeStamp=" + timestamp +
                '}';
    }
}
