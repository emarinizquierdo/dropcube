package com.dropcube.authentication;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.utils.SystemProperty;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import com.dropcube.constants.Params;


public class AuthorizationFilter implements Filter {


    private FilterConfig filterConfig = null;

    public AuthorizationFilter() {
    }

    public void init(FilterConfig config) throws ServletException {
        this.filterConfig = config;
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;

        HttpServletResponse response = (HttpServletResponse) res;
//LOGGER.fine('Add user to session');

        HttpSession session = request.getSession();

        //if not present,add credential store to servlet context
        if (session.getServletContext().getAttribute(Params.GOOG_CREDENTIAL_STORE) == null) {
            //LOGGER.fine('Adding credential store to context '+credentialStore);
            //session.getServletContext().setAttribute(Params.GOOG_CREDENTIAL_STORE, credentialStore);
        }

        //if google user isn't in session, add it
        if (session.getAttribute(Params.AUTH_USER_ID) == null) {

            SigninServlet signin = new SigninServlet();

            signin.doGet(request, response);

            UserService userService = UserServiceFactory.getUserService();

            User user = userService.getCurrentUser();
/*
            session.setAttribute(Params.AUTH_USER_ID, user.getUserId());
            session.setAttribute(Params.AUTH_USER_NICKNAME, user.getNickname());

            //if not running on app engine prod,hard-code my email address for testing
            if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
                session.setAttribute(Params.AUTH_USER_EMAIL, user.getEmail());
            } else {
                session.setAttribute(Params.AUTH_USER_EMAIL, "jeffdavisco@gmail.com");
            }
*/
        }
    }

    public void destroy() {
        this.filterConfig = null;
    }

}