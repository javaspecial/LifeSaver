package com.peoples.shield.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "current_location")
public class CurrentLocation {
    @PrimaryKey(autoGenerate = true)
    public Long id;
    public Double lat;
    public Double lng;
    public Long timeStamp;

    public CurrentLocation() {}

    public CurrentLocation(Long timeStamp, Double lng, Double lat) {
        this.timeStamp = timeStamp;
        this.lng = lng;
        this.lat = lat;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
