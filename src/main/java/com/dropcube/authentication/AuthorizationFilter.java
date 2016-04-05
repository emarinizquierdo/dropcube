package com.dropcube.authentication;

import com.dropcube.beans.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthorizationFilter implements Filter {

    private final static Logger LOGGER = Logger.getLogger(AuthorizationFilter.class.getName());

    private FilterConfig filterConfig = null;

    public void init(FilterConfig filterConfig) throws ServletException {

        LOGGER.setLevel(Level.ALL);
        LOGGER.info("Initializing Authorization Filter");

    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String emailUser = (String) request.getSession().getAttribute("emailUser");

        if (emailUser == null) {
            LOGGER.info("user is null");
            SigninServlet signin = new SigninServlet();
            signin.doGet(request, response);

        }else{
            LOGGER.info("EMAIL DE USUARIO " + emailUser);
            chain.doFilter(request, response);
        }

    }

    public void destroy() {

    }

}