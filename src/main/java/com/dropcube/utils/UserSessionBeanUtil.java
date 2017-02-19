package com.dropcube.utils;

import com.dropcube.beans.User;
import com.googlecode.objectify.ObjectifyService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by edu on 7/01/17.
 */
public class UserSessionBeanUtil {

    public static User get(HttpServletRequest request){

            // We get the user logged info
            String emailUser = (String) request.getSession().getAttribute("emailUser");
            String screenName = (String) request.getSession().getAttribute("screenName");
            User user = null;

            if(emailUser != null){
                // We get datastore user
                user = ObjectifyService.ofy().load().type(User.class).filter("email", emailUser).first().now();
            }else if(screenName != null){
                // We get datastore user
                user = ObjectifyService.ofy().load().type(User.class).filter("screenName", screenName).first().now();
            }


        return user;

    }

}
