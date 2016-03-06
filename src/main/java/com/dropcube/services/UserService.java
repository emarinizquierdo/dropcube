package com.dropcube.services;

import com.google.appengine.api.utils.SystemProperty;
import com.dropcube.biz.BizResponse;
import com.dropcube.constants.Params;
import com.dropcube.constants.Rest;
import com.visural.common.IOUtil;
import org.json.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URL;

/**
 * Created by edu on 5/03/16.
 */
@Path(Rest.USER_SERVICE_URL)
public class UserService {


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

    public void authFacebookLogin(String accessToken, int expires) {
        try {
            JSONObject resp = new JSONObject(
                    IOUtil.urlToString(new URL("https://graph.facebook.com/me?access_token=" + accessToken)));
            String id = resp.getString("id");
            String firstName = resp.getString("first_name");
            String lastName = resp.getString("last_name");
            String email = resp.getString("email");

            // ...
            // create and authorise the user in your current system w/ data above
            // ...

        } catch (Throwable ex) {
            throw new RuntimeException("failed login", ex);
        }
    }

}
