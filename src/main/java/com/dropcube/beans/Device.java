package com.dropcube.beans;

import com.dropcube.constants.Params;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.*;

import java.lang.String;
import java.util.*;

import com.dropcube.beans.User;

/**
 * The @Entity tells Objectify about our entity.  We also register it in {@link OfyHelper}
 * Our primary key @Id is set automatically by the Google Datastore for us.
 *
 * We add a @Parent to tell the object about its ancestor. We are doing this to support many
 * guestbooks.  Objectify, unlike the AppEngine library requires that you specify the fields you
 * want to index using @Index.  Only indexing the fields you need can lead to substantial gains in
 * performance -- though if not indexing your data from the start will require indexing it later.
 *
 * NOTE - all the properties are PUBLIC so that can keep the code simple.
 **/
@Entity
@Cache
public class Device {

    @Id public Long id;

    @Index
    public String deviceId;

    @Index
    public Long userId;

    public String name;
    public String description;
    public Boolean status;
    public Double lat = 40.4165000;
    public Double lng = -3.7025600;
    public Integer minHour = 8;
    public Integer maxHour = 18;
    public List<Boolean> hours = new ArrayList<Boolean>(Arrays.asList(new Boolean[24]));
    public Boolean hourly = true;
    public String mode = "NORMAL";

    public Boolean getHourly() {
        return hourly;
    }

    public void setHourly(Boolean hourly) {
        this.hourly = hourly;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public List<Boolean> getHours() {
        return hours;
    }

    public void setHours(List<Boolean> hours) {
        this.hours = hours;
    }

    public Device(){
        Collections.fill(hours, Boolean.FALSE);
    }

    public Device(String pDeviceId, String pName, Long pUserId){
        this();
        deviceId = pDeviceId;
        name = pName;
        userId = pUserId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Integer getMinHour() {
        return minHour;
    }

    public void setMinHour(Integer minHour) {
        this.minHour = minHour;
    }

    public Integer getMaxHour() {
        return maxHour;
    }

    public void setMaxHour(Integer maxHour) {
        this.maxHour = maxHour;
    }
}
