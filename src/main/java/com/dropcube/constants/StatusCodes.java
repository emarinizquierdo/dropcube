package com.dropcube.constants;

/**
 * Created by edu on 7/01/17.
 */
public class StatusCodes extends com.google.api.client.http.HttpStatusCodes {

    public static final int DELETE_USER_SESSION = 210;
    public static final int DELETE_REQUEST_SESSION = 211;
    public static final int UPDATE_USER_SESSION = 212;
    public static final int BAD_REQUEST = 400;
    public static final int OAUTH_CLIENT_TIMEOUT = 522;
    public static final int OAUTH_CLIENT_URL_FETCH = 525;
    public static final int OAUTH_CLIENT_INVALID_COOKIE = 527;
    public static final int OAUTH_CLIENT_UNSUPPORTED_ENCODING = 530;
    public static final int OAUTH_CLIENT_INVALID_RESPONSE = 531;
    public static final int OAUTH_CLIENT_GET_TOKEN = 540;
    public static final int OAUTH_CLIENT_INVALID_CREDENTIALS = 541;
    public static final int SERVER_ERROR_MAX = 599;

    public StatusCodes() {
    }
}

