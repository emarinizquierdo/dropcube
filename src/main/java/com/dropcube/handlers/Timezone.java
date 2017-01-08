package com.dropcube.handlers;

import com.dropcube.beans.User;
import com.dropcube.beans.mocks.TimezoneResponse;
import com.dropcube.constants.Params;
import com.google.gson.Gson;

import java.util.Date;
import java.util.logging.Logger;

/**
 * Created by edu on 7/01/17.
 */
public class Timezone {

    private User user;

    private final static Logger LOGGER = Logger.getLogger(Timezone.class.getName());

    private Gson GSON = new Gson();

    private Requestor REQUESTOR = null;

    public Timezone(User user) {
        this.user = user;
        REQUESTOR = new Requestor(this.user);
    }

    public int getOffset(Double latitude, Double longitude){

        LOGGER.info("Retrieving timezone data --");
        StringBuffer path = new StringBuffer ();
        path.append(Params.URL_GMAPS);
        path.append(latitude.toString());
        path.append(",");
        path.append(longitude.toString());
        path.append(Params.GMAPS_KEY);

        String response = REQUESTOR.request(path.toString());

        LOGGER.info("Timezone request" + path);
        LOGGER.info("Timezone response: " + response);

        TimezoneResponse timezoneResponse = GSON.fromJson(response, TimezoneResponse.class);

        Integer offset = toLocalTime(toLocalTime(timezoneResponse.getRawOffset()));

        LOGGER.info("Timezone ofset is: " + offset);

        return offset;

    }


    public Integer toLocalTime(Integer poffset) {

        Date d = new Date();
        Integer offset = ((new Date().getTimezoneOffset() + (poffset / 60)));
        Integer n = new Date(d.getTime() + offset * 60 * 1000).getHours();

        return n;

    }

}
