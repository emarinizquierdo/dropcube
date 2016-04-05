package com.dropcube.authentication;

import com.google.api.client.auth.oauth2.*;
import com.google.api.client.extensions.appengine.datastore.AppEngineDataStoreFactory;
import com.google.api.client.extensions.servlet.auth.oauth2.AbstractAuthorizationCodeCallbackServlet;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfoplus;
import com.google.api.services.plus.PlusScopes;
import com.google.api.services.plus.model.Person;
import com.google.api.services.plus.Plus;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dropcube.beans.User;
import com.googlecode.objectify.ObjectifyService;

/**
 * Servlet handling the OAuth callback from the authentication service. We are
 * retrieving the OAuth code, then exchanging it for a refresh and an access
 * token and saving it.
 */
@SuppressWarnings("serial")
public class OAuthCodeCallbackHandlerServlet extends AbstractAuthorizationCodeCallbackServlet {

    private final static Logger LOGGER = Logger.getLogger(OAuthCodeCallbackHandlerServlet.class.getName());

    private static final AppEngineDataStoreFactory DATA_STORE_FACTORY =
            AppEngineDataStoreFactory.getDefaultInstance();
    /** The name of the OAuth code URL parameter */
    public static final String CODE_URL_PARAM_NAME = "code";

    /** The name of the OAuth error URL parameter */
    public static final String ERROR_URL_PARAM_NAME = "error";

    /** The URL suffix of the servlet */
    public static final String URL_MAPPING = "/oauth2callback";

    /** The URL to redirect the user to after handling the callback. Consider
     * saving this in a cookie before redirecting users to the Google
     * authorization URL if you have multiple possible URL to redirect people to. */
    public static final String REDIRECT_URL = "/";


    @Override
    protected void onSuccess(HttpServletRequest req, HttpServletResponse resp, Credential credential)
            throws ServletException, IOException {

        // Getting the "error" URL parameter
        String[] error = req.getParameterValues(ERROR_URL_PARAM_NAME);

        // Checking if there was an error such as the user denied access
        if (error != null && error.length > 0) {
            resp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, "There was an error: \""+error[0]+"\".");
            return;
        }

        // Getting the "code" URL parameter
        String[] code = req.getParameterValues(CODE_URL_PARAM_NAME);

        // Checking conditions on the "code" URL parameter
        if (code == null || code.length == 0) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "The \"code\" URL parameter is missing");
            return;
        }

        // Construct incoming request URL
        //String requestUrl = getOAuthCodeCallbackHandlerUrl(req);

        // Exchange the code for OAuth tokens
        //TokenResponse accessTokenResponse = requestAccessToken(code[0], requestUrl);

        Plus plus = new Plus.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), credential)
                .build();

        // Make the API call
        Person profile = plus.people().get("me").execute();

        Oauth2 oauth2 = new Oauth2.Builder(new NetHttpTransport(), new JacksonFactory(), credential).build();
        Userinfoplus userinfo = oauth2.userinfo().get().execute();

        String email = userinfo.getEmail();
        LOGGER.info("saving user: " + email);

        //Check if user already exists
        User user = ObjectifyService.ofy().load().type(User.class).filter("email", email).first().now();

        if(user == null){
            user = new User(userinfo.getName(), email, profile);
            ObjectifyService.ofy().save().entity(user).now();
            LOGGER.info("saving user: " + email);
        }

        req.getSession().setAttribute("emailUser", user.email);

        Cookie cookie =  createCookie("token", "code");

        resp.addCookie(cookie);

        resp.sendRedirect(REDIRECT_URL);

    }

    @Override
    protected void onError(
            HttpServletRequest req, HttpServletResponse resp, AuthorizationCodeResponseUrl errorResponse)
            throws ServletException, IOException {
        // handle error
    }

    @Override
    protected String getRedirectUri(HttpServletRequest req) throws ServletException, IOException {
        GenericUrl url = new GenericUrl(req.getRequestURL().toString());
        url.setRawPath(URL_MAPPING);
        return url.build();
    }

    @Override
    protected AuthorizationCodeFlow initializeFlow() throws IOException {

        OAuthProperties oAuthProperties = new OAuthProperties();

        return new GoogleAuthorizationCodeFlow.Builder(
                new NetHttpTransport(), JacksonFactory.getDefaultInstance(),
                    oAuthProperties.getClientId(),
                    oAuthProperties.getClientSecret(),
                    PlusScopes.all())
                //.setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline").build();
    }

    @Override
    protected String getUserId(HttpServletRequest req) throws ServletException, IOException {
        // return user ID
        return null;
    }

    private Cookie createCookie(String cookieName, String cookieValue) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setPath("/");
        cookie.setMaxAge(3600);
        return cookie;
    }

}