package com.dropcube.authentication;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.extensions.appengine.auth.oauth2.AbstractAppEngineAuthorizationCodeServlet;
import com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by edu on 20/03/16.
 */
public class SigninServlet extends AbstractAppEngineAuthorizationCodeServlet {


    static final UrlFetchTransport HTTP_TRANSPORT = new UrlFetchTransport();
    static final JacksonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // do stuff
    }

    @Override
    protected String getRedirectUri(HttpServletRequest req) throws ServletException, IOException {
        return Utils.getRedirectUri(req);
    }

    @Override
    protected AuthorizationCodeFlow initializeFlow() throws IOException {
        return Utils.newFlow();
    }

    @Override
    protected String getUserId(HttpServletRequest req) throws ServletException, IOException {
        return null;
    }
}
