package com.dropcube.handlers;

import com.dropcube.beans.User;
import com.dropcube.beans.mocks.TimezoneResponse;
import com.dropcube.constants.Params;
import com.google.appengine.repackaged.org.joda.time.DateTime;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
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

    public TimezoneResponse getTimeZone(Double lat, Double lng){

        LOGGER.info("Retrieving timezone data --");
        StringBuffer path = new StringBuffer ();
        path.append(Params.URL_GMAPS);
        path.append(lat.toString());
        path.append(",");
        path.append(lng.toString());
        path.append(Params.GMAPS_KEY);

        String response = REQUESTOR.request(path.toString());

        LOGGER.info("Timezone request" + path);
        LOGGER.info("Timezone response: " + response);

        TimezoneResponse timezoneResponse = GSON.fromJson(response, TimezoneResponse.class);

        return timezoneResponse;

    }

    public int localHour(long current, Double lat, Double lng){

        TimezoneResponse timezone = getTimeZone(lat, lng);
        Date date = new Date((current + timezone.getRawOffset()) * 1000);
        DateFormat formater = new SimpleDateFormat("HH");
        formater.setTimeZone(TimeZone.getTimeZone("UTC"));
        String hour = formater.format(date);

        LOGGER.info("Local hour is: " + hour);

        return Integer.valueOf(hour);

    }

}
