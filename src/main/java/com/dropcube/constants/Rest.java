package com.dropcube.constants;


public class Rest {

    // //////////////
    // /USERS
    // //////////////

    public static final String USER_SERVICE_URL = "/users";
    public static final String USER_GET_URL = "/{" + Params.PARAM_ID + "}";
    public static final String USER_SERVICE_GOOGLE_AUTH = "/gauth";
    public static final String USER_GET_ME = "/me";

    // //////////////
    // /DEVICES
    // //////////////
    public static final String DEVICE_SERVICE_URL = "/devices";
    public static final String DEVICE_REFRESH_URL = "/refresh";
    public static final String DEVICE_GET_URL = "/{" + Params.PARAM_ID + "}";


    // //////////////
    // /LIGHTS
    // //////////////
    public static final String LIGHTS_SERVICE_URL = "/lights";
    public static final String PARTICLE_SERVICE_URL = "/particle";
    public static final String LIGHTS_GET_URL = "/{" + Params.PARAM_ID + "}";

}
