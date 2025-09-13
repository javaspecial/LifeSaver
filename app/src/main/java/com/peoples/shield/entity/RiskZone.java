package com.peoples.shield.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "risk_zone")
public class RiskZone {

    @PrimaryKey(autoGenerate = true)
    public Long id;
    public Double lat;
    public Double lng;
    public Float radius;

    public RiskZone() {}

    public RiskZone(Double lat, Double lng, Float radius) {
        this.radius = radius;
        this.lng = lng;
        this.lat = lat;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Float getRadius() {
        return radius;
    }

    public void setRadius(Float radius) {
        this.radius = radius;
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
}
