package com.dropcube.authentication;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by edu on 20/03/16.
 */
public class SigninServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException {

        // redirect to google for authorization
        StringBuilder oauthUrl = new StringBuilder().append("https://accounts.google.com/o/oauth2/auth")
                .append("?client_id=").append("1014736735296-h96jbcfu84kbm6v4kkohu88it2n2pc1d.apps.googleusercontent.com") // the client id from the api console registration
                .append("&response_type=code")
                .append("&scope=openid%20email") // scope is the api permissions we are requesting
                .append("&redirect_uri=http://localhost:8080/oauthcallback") // the servlet that google redirects to after authorization
                .append("&state=/")
                .append("&access_type=offline") // here we are asking to access to user's data while they are not signed in
                .append("&approval_prompt=force"); // this requires them to verify which account to use, if they are already signed in

        resp.sendRedirect(oauthUrl.toString());
    }
}