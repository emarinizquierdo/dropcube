package com.dropcube.biz;

import com.dropcube.beans.Device;
import com.dropcube.beans.User;
import com.dropcube.beans.mocks.Weather;
import com.dropcube.exceptions.DropcubeException;
import com.dropcube.handlers.Requestor;
import com.dropcube.handlers.Timezone;
import com.google.gson.Gson;

import java.util.logging.Logger;

import static com.dropcube.constants.Params.*;

/**
 * Created by edu on 7/01/17.
 */
public class WeatherBiz {

    private User user;

    private final static Logger LOGGER = Logger.getLogger(WeatherBiz.class.getName());

    private DeviceBiz DEVICE_BIZ = null;
    private Requestor REQUESTOR = null;
    private Timezone TIMEZONE = null;

    private Gson GSON = new Gson();

    public WeatherBiz(User user) {
        this.user = user;
        DEVICE_BIZ = new DeviceBiz(this.user);
        REQUESTOR = new Requestor(this.user);
        TIMEZONE = new Timezone(this.user);
    }

    public Weather get(Long deviceId) throws DropcubeException{

        LOGGER.info("Getting weather for device" + deviceId + " for coordinates: ");

        Device device = DEVICE_BIZ.get(deviceId);

        Integer offset = TIMEZONE.getOffset(device.getLat(), device.getLng());

        StringBuffer path = new StringBuffer();
        path.append(DARK_SKY_API_URL);
        path.append(DARK_SKY_API_TOKEN);
        path.append(device.getLat().toString());
        path.append(",");
        path.append(device.getLng().toString());
        path.append(",");
        path.append(String.valueOf(offset/1000));
        path.append("?units=si&exclude=daily,flags");

        String response = REQUESTOR.request(path.toString());

        Weather weather = GSON.fromJson(response, Weather.class);

        return weather;

    }


}
