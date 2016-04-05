package com.dropcube.services;

import com.dropcube.beans.Device;
import com.dropcube.beans.User;
import com.dropcube.constants.Params;
import com.dropcube.constants.Rest;
import com.google.appengine.repackaged.com.google.gson.Gson;
import com.googlecode.objectify.ObjectifyService;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by edu on 5/04/16.
 */
@Path(Rest.LIGHTS_SERVICE_URL)
public class Lights {

    private static final Gson GSON = new Gson();

    private String URL_GMAPS = "https://maps.googleapis.com/maps/api/timezone/json?location=";
    private String GMAPS_KEY = "&timestamp=1331161200&key=AIzaSyDjlKGoghg7xvbk_B7Ym4OEzl1JPGd2CNk";


    private final static Logger LOGGER = Logger.getLogger(DeviceService.class.getName());

    /**
     * Gets a user information.
     * @return {@link Response} Response in Json with the rating information.
     */
    @GET
    @Path(Rest.DEVICE_GET_URL)
    @Produces(MediaType.APPLICATION_JSON + Params.CHARSET_UTF8)
    public Response getLights(
            @Context HttpServletRequest request,
            @PathParam(Params.PARAM_ID) Long id) {

        LOGGER.info("response");

        // We get the user logged info
        String emailUser = (String) request.getSession().getAttribute("emailUser");

        // We get datastore user info and update language
        User user = ObjectifyService.ofy().load().type(User.class).filter("email", emailUser).first().now();
    LOGGER.info("el usuario es: " + user.id);

        Device device = ObjectifyService.ofy().load().type(Device.class).id(id).now();

        LOGGER.info("el device es : " + device.userId);

        if(device.userId.compareTo(user.id) != 0){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        LOGGER.info("latitude: " + device.lat.toString());
        LOGGER.info("longitude: " + device.lng.toString());
        String data = getJSON(URL_GMAPS + device.lat.toString() + "," + device.lng.toString() + GMAPS_KEY, 60000);

        try {
            JSONObject jObject = new JSONObject(data);
            Integer projecname = (Integer) jObject.get("dstOffset");
            LOGGER.info("el dstoffset es: " + projecname);
        }catch(JSONException ie){

        }

        //BizResponse response = new BizResponse(deviceList);

        //return Response.ok().entity(response.toJsonExcludeFieldsWithoutExposeAnnotation()).build();
        return Response.ok().entity(data).build();
    }

    public String getJSON(String url, int timeout) {
        HttpURLConnection c = null;
        try {
            URL u = new URL(url);
            c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Content-length", "0");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.setConnectTimeout(timeout);
            c.setReadTimeout(timeout);
            c.connect();
            int status = c.getResponseCode();

            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
                    }
                    br.close();
                    return sb.toString();
            }

        } catch (MalformedURLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (c != null) {
                try {
                    c.disconnect();
                } catch (Exception ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }



}
