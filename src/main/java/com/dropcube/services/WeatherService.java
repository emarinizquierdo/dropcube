package com.dropcube.services;

import com.dropcube.beans.Device;
import com.dropcube.beans.User;
import com.dropcube.beans.mocks.Weather;
import com.dropcube.biz.BizResponse;
import com.dropcube.biz.DeviceBiz;
import com.dropcube.biz.WeatherBiz;
import com.dropcube.constants.Params;
import com.dropcube.constants.Rest;
import com.dropcube.exceptions.DropcubeException;
import com.dropcube.utils.UserSessionBeanUtil;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

/**
 * Created by edu on 5/03/16.
 */
@Path(Rest.WEATHER_SERVICE_URL)
public class WeatherService {

    private final static Logger LOGGER = Logger.getLogger(WeatherService.class.getName());

    /**
     * Gets a user information.
     *
     * @return {@link Response} Response in Json with the rating information.
     */
    @GET
    @Path(Rest.GET_ID_URL)
    @Produces(MediaType.APPLICATION_JSON + Params.CHARSET_UTF8)
    public Response get(
            @Context HttpServletRequest request,
            @PathParam(Params.PARAM_ID) Long id) throws DropcubeException{

        // We get datastore user info and update language
        User user = UserSessionBeanUtil.get(request);

        WeatherBiz WEATHER_BIZ = new WeatherBiz(user);
        DeviceBiz DEVICE_BIZ = new DeviceBiz(user);

        Device device = DEVICE_BIZ.get(id);
        Weather weather = WEATHER_BIZ.get(device.getLat(), device.getLng());

        BizResponse response = new BizResponse(weather);
        return Response.ok().entity(response.toJson()).build();

    }

    /**
     * Gets a user information.
     *
     * @return {@link Response} Response in Json with the rating information.
     */
    @GET
    @Path(Rest.WEATHER_SOME_URL + Rest.GET_ID_URL)
    @Produces(MediaType.APPLICATION_JSON + Params.CHARSET_UTF8)
    public Response getSome(
            @Context HttpServletRequest request,
            @PathParam(Params.PARAM_ID) Long id) throws DropcubeException{

        // We get datastore user info and update language
        User user = UserSessionBeanUtil.get(request);

        WeatherBiz WEATHER_BIZ = new WeatherBiz(user);
        DeviceBiz DEVICE_BIZ = new DeviceBiz(user);

        Device device = DEVICE_BIZ.get(id);
        WEATHER_BIZ.getSomeHours(device);

        BizResponse response = new BizResponse();
        return Response.ok().entity(response.toJson()).build();

    }

}