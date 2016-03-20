package com.dropcube.constants;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by marosa on 31/08/15.
 */
public class Params {

    public final static String CHARSET_UTF8 = ";charset=UTF-8";

    public final static String PARAM_JSON = "json";

    public final static String PARAM_ID = "id";

    public final static String PARAM_FILTERS = "filters";

    public static final String PARAM_SCOPE = "scope";

    public static final String PARAM_USER = "user";

    public static final String PARAM_OBJECT_ID = "objectId";

    public final static String PARAM_START_CURSOR = "start-cursor";

    public final static String PARAM_MAX_RESULTS = "max-results";

    public final static String PARAM_START_INDEX = "start-index";

    public final static String PARAM_ORDER_FILTER = "order-filters";

    public static final String PARAM_STATUS = "status";

    public final static Level LOG_LEVEL = Level.FINE;

    /* session attributes */
    public final static String TARGET_URI = "TargetUri";

    public final static String AUTH_USER_ID = "UserEmail";

    public final static String AUTH_USER_EMAIL = "UserEmail";

    public final static String AUTH_USER_NICKNAME = "UserNickname";

    public final static String GOOG_CREDENTIAL_STORE = "GoogleCredentialStore";

    public final static String GOOG_CREDENTIAL = "GoogleCredentialL";

    public final static String VIDEO_FAVS = "VideoFavs";
	/* end of session attributes */

    public final static String GOOGLE_YOUTUBE_FEED = "https://gdata.youtube.com/feeds/api/users/default/favorites/";

    public final static String GOOGLE_SPREADSHEET_FEED = "https://docs.google.com/feeds/default/private/full/-/spreadsheet";

    public final static String GOOGLE_RESOURCE = "https://gdata.youtube.com/";

    public final static String AUTH_RESOURCE_LOC = "/client_secrets.json";

    public final static List<String> SCOPES = Arrays.asList(
            "https://gdata.youtube.com/feeds/api/users/default/favorites/");

    // Use for running on GAE
    //final static String OATH_CALLBACK = "http://tennis-coachrx.appspot.com/authSub";

    // Use for local testing
    public final static String OATH_CALLBACK = "http://localhost:8888/authSub";

}
