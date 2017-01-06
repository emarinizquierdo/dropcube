package com.dropcube.biz;

import com.dropcube.beans.Device;
import com.dropcube.beans.User;
import com.dropcube.handlers.jfirebase.Driver;
import com.googlecode.objectify.ObjectifyService;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


/**
 * Created by edu on 2/01/17.
 */
public class DeviceBiz {

    private final static Logger LOGGER = Logger.getLogger(DeviceBiz.class.getName());
    private Driver DRIVER = new Driver();

    public Boolean refreshDevice(Long deviceId, User user){

        try{
            LOGGER.warning("refreshing " + deviceId + " device");

            Map<String,String> valores = new HashMap<>();

            valores.put("MODE", getMode(deviceId,user));

            return DRIVER.write(String.valueOf(deviceId), valores);

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }


    private String getMode(Long deviceId, User user){

        Device device = ObjectifyService.ofy().load().type(Device.class).id(deviceId).now();

        if(device.userId.compareTo(user.id) != 0){

        }

        return device.getMode();

    }


}
