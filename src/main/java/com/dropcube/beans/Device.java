package com.dropcube.beans;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

import java.lang.String;
import java.util.Date;
import java.util.List;

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
public class Device {

    @Id public Long _id;

    @Index public String deviceId;
    @Index public Long userId;

    public String name;
    public String description;
    public Boolean status;
    public Double lat;
    public Double lng;
    public Integer minHour;
    public Integer maxHour;

    public Device(){

    }

    public Device(String pDeviceId, String pName, Long pUserId){
        this();
        deviceId = pDeviceId;
        name = pName;
        userId = pUserId;
    }

    public void setDescription( String pDescription){
        description = pDescription;
    }

    public void setLat( Double pLat){
        lat = pLat;
    }

    public void setLong(Double pLong){
        lng = pLong;
    }

    public void setMinHour(Integer pMinHour){
        minHour = pMinHour;
    }

    public void setMaxHour(Integer pMaxHour){
        maxHour = pMaxHour;
    }


}
