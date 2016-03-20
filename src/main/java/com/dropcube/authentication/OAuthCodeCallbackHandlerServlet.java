package com.dropcube.authentication;

import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by edu on 20/03/16.
 */
public class OAuthCodeCallbackHandlerServlet extends HttpServlet {
        private static final long serialVersionUID = 1L;
        private final static Logger LOGGER = Logger.getLogger(OAuthCodeCallbackHandlerServlet.class.getName());

        public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

            StringBuffer fullUrlBuf = request.getRequestURL();
            Credential credential = null;

            if (request.getQueryString() != null) {
                fullUrlBuf.append('?').append(request.getQueryString());
            }

            LOGGER.info("requestURL is: " + fullUrlBuf);

            AuthorizationCodeResponseUrl authResponse = new AuthorizationCodeResponseUrl(fullUrlBuf.toString());

            // check for user-denied error
            if (authResponse.getError() != null) {
                LOGGER.info("User-denied access");
            } else {
                LOGGER.info("User granted oauth access");

                String authCode = authResponse.getCode();

                Cookie cookie =  createCookie("token", authCode);

                response.addCookie(cookie);

                request.getSession().setAttribute("code", authCode);
                LOGGER.info("User granted oauth access" + authCode);
                response.sendRedirect(authResponse.getState());

            }
        }

        @Override
        public void init() throws ServletException {
            super.init();

            //LOGGER.setLevel(Constant.LOG_LEVEL);

            //LOGGER.info("Initializing FavoritesServlet Servlet");

        }

    private Cookie createCookie(String cookieName, String cookieValue) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setPath("/");
        cookie.setMaxAge(3600);
        cookie.setSecure(true);
        return cookie;
    }

}
