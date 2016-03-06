package com.dropcube.services;

import com.google.appengine.api.utils.SystemProperty;
import com.dropcube.biz.BizResponse;
import com.dropcube.constants.Params;
import com.dropcube.constants.Rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by edu on 5/03/16.
 */
@Path(Rest.DEVICE_SERVICE_URL)
public class DeviceService {


    /**
     * Gets a user information.
     * @return {@link Response} Response in Json with the rating information.
     */
    @GET
    @Path(Rest.DEVICE_GET_URL)
    @Produces(MediaType.APPLICATION_JSON + Params.CHARSET_UTF8)
    public Response get(
            @Context HttpServletRequest request,
            @PathParam(Params.PARAM_ID) String id) {
        BizResponse response = new BizResponse();
        return Response.ok().entity(response.toJsonExcludeFieldsWithoutExposeAnnotation()).build();

    }


}
