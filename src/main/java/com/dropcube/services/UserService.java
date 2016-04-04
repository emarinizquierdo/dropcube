package com.dropcube.services;

import com.dropcube.beans.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.dropcube.biz.BizResponse;
import com.dropcube.constants.Params;
import com.dropcube.constants.Rest;
import com.google.appengine.repackaged.com.google.api.client.json.Json;
import com.google.appengine.repackaged.com.google.gson.annotations.SerializedName;
import com.googlecode.objectify.ObjectifyService;
import com.google.gson.Gson;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by edu on 5/03/16.
 */
@Path(Rest.USER_SERVICE_URL)
public class UserService {

    private final static Logger LOGGER = Logger.getLogger(UserService.class.getName());
    private static final Gson GSON = new Gson();

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


        // Getting the current user
        // This is using App Engine's User Service but you should replace this to
        // your own user/login implementation
        com.google.appengine.api.users.UserService userService = UserServiceFactory.getUserService();
        String email = userService.getCurrentUser().getEmail();
        LOGGER.info("email es: " + email);
        User user = ObjectifyService.ofy().load().type(User.class).filter("email", email).first().now();
        LOGGER.info("email es: " + user.toString());
        BizResponse response = new BizResponse(user);

        return Response.ok().entity(response.toJson()).build();

    }

    @PUT
    @Path(Rest.USER_GET_ME)
    @Produces(MediaType.APPLICATION_JSON + Params.CHARSET_UTF8)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putMe(
            @Context HttpServletRequest request,
            LangPojo json) throws IOException{

        // We get the user logged info
        com.google.appengine.api.users.UserService userService = UserServiceFactory.getUserService();
        String email = userService.getCurrentUser().getEmail();

        // We get datastore user info and update language
        User user = ObjectifyService.ofy().load().type(User.class).filter("email", email).first().now();
        user.updateLang( json.lang );
        ObjectifyService.ofy().save().entity(user);

        BizResponse response = new BizResponse(user);
        return Response.ok().entity(response.toJson()).build();

    }

    @XmlRootElement
    static public class LangPojo {
        public String lang;

        public MyPojo() {} // constructor is required

    }

}
