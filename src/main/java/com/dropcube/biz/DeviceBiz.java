package com.dropcube.biz;

import com.dropcube.beans.Device;
import com.dropcube.beans.User;
import com.dropcube.constants.StatusCodes;
import com.dropcube.exceptions.DropcubeException;
import com.dropcube.handlers.Firebase;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


/**
 * Created by edu on 2/01/17.
 */
public class DeviceBiz {

    private User user;

    private final static Logger LOGGER = Logger.getLogger(DeviceBiz.class.getName());
    private Firebase FIREBASE = null;

    public DeviceBiz(User user) {
        this.user = user;
        FIREBASE = new Firebase( this.user );
    }

    public Device get(Long id) throws DropcubeException{

        //We get datastore device info
        Device device = ObjectifyService.ofy().load().key(Key.create(Device.class, id)).now();

        if(device == null){
            LOGGER.info("Device not found");
            throw new DropcubeException("NOT FOUND", StatusCodes.STATUS_CODE_NOT_FOUND);
        }

        LOGGER.info(device.getUserId().toString());
        LOGGER.info(this.user.getId().toString());
        //If user doesn't have permission, we return 401 status
        if (device.getUserId() != null && this.user.getId() != null && (device.getUserId().compareTo(this.user.getId()) != 0)) {
            LOGGER.info("Unauthorized");
            throw new DropcubeException("Unauthorized", StatusCodes.STATUS_CODE_UNAUTHORIZED);
        }

        return device;

    }

    public Boolean refreshDevice(Long deviceId){

        try{
            LOGGER.warning("refreshing " + deviceId + " device");

            Map<String,String> valores = new HashMap<>();

            valores.put("MODE", getMode(deviceId));

            return FIREBASE.write(String.valueOf(deviceId), valores);

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }


    private String getMode(Long deviceId) throws DropcubeException{

        Device device = get(deviceId);

        return device.getMode();

    }


}
