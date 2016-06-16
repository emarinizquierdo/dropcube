package com.dropcube.services;

import com.dropcube.beans.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.dropcube.biz.BizResponse;
import com.dropcube.constants.Params;
import com.dropcube.constants.Rest;
import com.googlecode.objectify.ObjectifyService;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by edu on 5/03/16.
 */
@Path(Rest.USER_SERVICE_URL)
public class UserService {

    private final static Logger LOGGER = Logger.getLogger(UserService.class.getName());

    /**
     * Gets a user information.
     * @return {@link Response} Response in Json with the rating information.
     */
    @GET
    @Path(Rest.USER_GET_URL)
    @Produces(MediaType.APPLICATION_JSON + Params.CHARSET_UTF8)
    public Response get(
            @Context HttpServletRequest request,
            @PathParam(Params.PARAM_ID) String id) {
        BizResponse response = new BizResponse();
        return Response.ok().entity(response.toJsonExcludeFieldsWithoutExposeAnnotation()).build();

    }

    @GET
    @Path(Rest.USER_GET_ME)
    @Produces(MediaType.APPLICATION_JSON + Params.CHARSET_UTF8)
    public Response getMe(
            @Context HttpServletRequest request,
            @PathParam(Params.PARAM_ID) String id) {

        // We get the user logged info
        User user = getUser(request);

        BizResponse response = new BizResponse(user);

        return Response.ok().entity(response.toJson()).build();

    }

    @PUT
    @Path(Rest.USER_GET_ME)
    @Produces(MediaType.APPLICATION_JSON + Params.CHARSET_UTF8)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putMe(
            @Context HttpServletRequest request,
            LangPOJO json) throws IOException{

        User user = getUser(request);

        user.updateLang( json.lang );
        ObjectifyService.ofy().save().entity(user);

        BizResponse response = new BizResponse(user);
        return Response.ok().entity(response.toJson()).build();

    }

    @XmlRootElement
    static public class LangPOJO {
        public String lang;

        public LangPOJO() {} // constructor is required

    }

    public User getUser(HttpServletRequest request){
        // We get the user logged info
        String emailUser = (String) request.getSession().getAttribute("emailUser");
        String screenName = (String) request.getSession().getAttribute("screenName");
        User user = null;

        if(emailUser != null){
            // We get datastore user info and update language
            user = ObjectifyService.ofy().load().type(User.class).filter("email", emailUser).first().now();
        }else if(screenName != null){
            // We get datastore user info and update language
            user = ObjectifyService.ofy().load().type(User.class).filter("screenName", screenName).first().now();
        }

        return user;
    }

}
