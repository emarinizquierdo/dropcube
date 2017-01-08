package com.dropcube.constants;


/**
 * Created by marosa on 31/08/15.
 */
public class Params {

    public final static String ADMIN_USER = "e.marin.izquierdo@gmail.com";

    public final static String CHARSET_UTF8 = ";charset=UTF-8";

    public final static String PARAM_JSON = "json";

    public final static String PARAM_ID = "id";

    public final static int REQ_TIMEOUT = 60000;

    public final static int REQ_RETRIES = 3;

    /**
     * INPUT PARAMS
     */
    public final static String PARAM_LANGUAGE = "lang";

    /**
     * LIGHTS
     */
    public final static String URL_GMAPS = "https://maps.googleapis.com/maps/api/timezone/json?location=";
    public final static String GMAPS_KEY = "&timestamp=1331161200&key=AIzaSyDjlKGoghg7xvbk_B7Ym4OEzl1JPGd2CNk";
    public final static double BINARY_MAX_REL = 1023;
    public final static int BINARY_MAX = 1023;

    /**
     * Weather
     */
    public final static String DARK_SKY_API_URL = "https://api.forecast.io/forecast/";
    public final static String DARK_SKY_API_TOKEN = "b290219f260ca3a384400c3a019b21fd/";
    public final static double RAIN_CONSTANT = 0.05;
    public final static double INCHES_TO_MM = 25.4;
    public final static double HOT_CONSTANT = 0.08333333333;
    public final static double WIND_CONSTANT = 0.04545454545;

    /**
     * MODES
     */
    public enum MODE {
        RAINBOW, TEST_HOT, TEST_RAIN, TEST_SNOW, TEST_WIND, TEST_STORM, NORMAL;
    }

}
