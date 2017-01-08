package com.dropcube.services;

import com.dropcube.beans.Device;
import com.dropcube.beans.User;
import com.dropcube.biz.DeviceBiz;
import com.dropcube.biz.BizResponse;
import com.dropcube.constants.Params;
import com.dropcube.constants.Rest;
import com.dropcube.exceptions.DropcubeException;
import com.dropcube.utils.UserSessionBeanUtil;
import com.googlecode.objectify.ObjectifyService;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by edu on 5/03/16.
 */
@Path(Rest.DEVICE_SERVICE_URL)
public class DeviceService {

    private final static Logger LOGGER = Logger.getLogger(DeviceService.class.getName());

    /**
     * Gets a user information.
     * @return {@link Response} Response in Json with the rating information.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON + Params.CHARSET_UTF8)
    public Response getAll(
            @Context HttpServletRequest request) {

        LOGGER.info("response");

        // We get datastore user info and update language
        User user = UserSessionBeanUtil.get(request);
        List<Device> deviceList = ObjectifyService.ofy().load().type(Device.class).filter("userId", user.id).list();

        BizResponse response = new BizResponse(deviceList);

        //return Response.ok().entity(response.toJsonExcludeFieldsWithoutExposeAnnotation()).build();
        return Response.ok().entity(response.toJson()).build();
    }

    /**
     * Gets a user information.
     * @return {@link Response} Response in Json with the rating information.
     */
    @GET
    @Path(Rest.DEVICE_GET_URL)
    @Produces(MediaType.APPLICATION_JSON + Params.CHARSET_UTF8)
    public Response get(
            @Context HttpServletRequest request,
            @PathParam(Params.PARAM_ID) Long id) {

        // We get datastore user info and update language
        User user = UserSessionBeanUtil.get(request);
        Device device = ObjectifyService.ofy().load().type(Device.class).id(id).now();

        if(device.userId.compareTo(user.id) != 0){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        BizResponse response = new BizResponse(device);
        return Response.ok().entity(response.toJson()).build();

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON + Params.CHARSET_UTF8)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postDevice(
            @Context HttpServletRequest request,
            DevicesPOJO json) throws IOException {

        // We get datastore user info and update language
        User user = UserSessionBeanUtil.get(request);
        Device device = new Device(json.deviceId, json.name, user.id);

        ObjectifyService.ofy().save().entity(device).now();

        BizResponse response = new BizResponse(device);
        return Response.ok().entity(response.toJson()).build();

    }

    @PUT
    @Path(Rest.DEVICE_GET_URL)
    @Produces(MediaType.APPLICATION_JSON + Params.CHARSET_UTF8)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putDevice(
            @Context HttpServletRequest request,
            @PathParam(Params.PARAM_ID) Long id,
            DevicesPOJO json) throws IOException, DropcubeException{

        // We get datastore user info and update language
        User user = UserSessionBeanUtil.get(request);
        DeviceBiz DEVICE_BIZ = new DeviceBiz(user);

        Device device = ObjectifyService.ofy().load().type(Device.class).id(id).now();

        if(device.userId.compareTo(user.id) != 0){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        device.setDescription(json.description);
        device.setMode(json.mode);
        device.setLat(json.lat);
        device.setLng(json.lng);
        device.setMinHour(json.minHour);
        device.setMaxHour(json.maxHour);

        ObjectifyService.ofy().save().entity(device);

        DEVICE_BIZ.refreshDevice(id);

        BizResponse response = new BizResponse(device);
        return Response.ok().entity(response.toJson()).build();

    }

    @GET
    @Path(Rest.DEVICE_REFRESH_URL + Rest.DEVICE_GET_URL)
    @Produces(MediaType.APPLICATION_JSON + Params.CHARSET_UTF8)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response refreshDevice(
            @Context HttpServletRequest request,
            @PathParam(Params.PARAM_ID) Long deviceId) throws IOException, DropcubeException{

        // We get datastore user info and update language
        User user = UserSessionBeanUtil.get(request);
        DeviceBiz DEVICE_BIZ = new DeviceBiz(user);

        BizResponse response = new BizResponse(DEVICE_BIZ.refreshDevice(deviceId));
        return Response.ok().entity(response.toJson()).build();

    }

    @XmlRootElement
    static public class DevicesPOJO {

        public String deviceId;
        public String name;
        public String description;
        public Double lat;
        public Double lng;
        public String mode;
        public Integer minHour;
        public Integer maxHour;

        public DevicesPOJO() {} // constructor is required

    }

    /**
     * Gets a user information.
     * @return {@link Response} Response in Json with the rating information.
     */
    @DELETE
    @Path(Rest.DEVICE_GET_URL)
    @Produces(MediaType.APPLICATION_JSON + Params.CHARSET_UTF8)
    public Response deleteDevice(
            @Context HttpServletRequest request,
            @PathParam(Params.PARAM_ID) Long id) {

        // We get datastore user info and update language
        User user = UserSessionBeanUtil.get(request);
        Device device = ObjectifyService.ofy().load().type(Device.class).id(id).now();

        if(device.userId.compareTo(user.id) != 0){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        ObjectifyService.ofy().delete().entity(device).now();

        BizResponse response = new BizResponse(device);
        return Response.ok().entity(response.toJson()).build();

    }

}
