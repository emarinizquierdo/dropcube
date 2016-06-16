package com.dropcube.authentication;

import com.googlecode.objectify.ObjectifyService;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by edu on 16/04/16.
 */
public class TwitterOauthCodeCallback extends HttpServlet {

    private final static java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(TwitterSigninServlet.class.getName());

    /** The URL to redirect the user to after handling the callback. Consider
     * saving this in a cookie before redirecting users to the Google
     * authorization URL if you have multiple possible URL to redirect people to. */
    public static final String REDIRECT_URL = "/";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Twitter twitter = (Twitter) request.getSession().getAttribute("twitter");
        RequestToken requestToken = (RequestToken) request.getSession().getAttribute("requestToken");
        String verifier = request.getParameter("oauth_verifier");

        try {
            twitter.getOAuthAccessToken(requestToken, verifier);
            request.getSession().removeAttribute("requestToken");
            User userTwitter = twitter.showUser(twitter.getId());
            LOGGER.info("user es: " + userTwitter.getScreenName());

            //Check if user already exists
            com.dropcube.beans.User user = ObjectifyService.ofy().load().type(com.dropcube.beans.User.class).filter("screenName", userTwitter.getScreenName()).first().now();

            if(user == null){
                user = new com.dropcube.beans.User(userTwitter.getName(), userTwitter.getScreenName(), "twitter");
                user.setProfileCover(userTwitter.getBiggerProfileImageURL());
                user.setBackgroundCover(userTwitter.getProfileBackgroundImageURL());
                ObjectifyService.ofy().save().entity(user).now();
                LOGGER.info("saving user: " +  userTwitter.getScreenName());
            }

            request.getSession().setAttribute("screenName", user.screenName);

            Cookie cookie =  createCookie("token", "code");

            response.addCookie(cookie);


        } catch (TwitterException e) {
            throw new ServletException(e);
        }

        response.sendRedirect(REDIRECT_URL);
    }

    private Cookie createCookie(String cookieName, String cookieValue) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setPath("/");
        cookie.setMaxAge(3600);
        return cookie;
    }

}
