package com.dropcube.authentication;

import com.google.api.client.extensions.appengine.datastore.AppEngineDataStoreFactory;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;
import com.google.api.services.plus.PlusScopes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import static com.dropcube.authentication.SigninServlet.HTTP_TRANSPORT;
import static com.dropcube.authentication.SigninServlet.JSON_FACTORY;

/**
 * Created by edu on 2/04/16.
 */

class Utils {

    //private static final AppEngineDataStoreFactory DATA_STORE_FACTORY =
    // AppEngineDataStoreFactory.getDefaultInstance();

    public static String getRedirectUri(HttpServletRequest req) {
        GenericUrl url = new GenericUrl(req.getRequestURL().toString());
        url.setRawPath("/oauth2callback");
        return url.build();
    }

    public static GoogleAuthorizationCodeFlow newFlow() throws IOException {

        OAuthProperties oAuthProperties = new OAuthProperties();

        return new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
                    oAuthProperties.getClientId(),
                    oAuthProperties.getClientSecret(),
                    PlusScopes.all())
                //.setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline").build();
    }
}