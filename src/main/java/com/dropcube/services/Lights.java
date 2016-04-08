package com.dropcube.services;

import com.dropcube.beans.Device;
import com.dropcube.beans.User;
import com.dropcube.constants.Params;
import com.dropcube.constants.Rest;
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
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by edu on 5/04/16.
 */
@Path(Rest.LIGHTS_SERVICE_URL)
public class Lights {

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
            @PathParam(Params.PARAM_ID) String id) {

        LOGGER.info("response");

        // We get the user logged info
        String emailUser = (String) request.getSession().getAttribute("emailUser");

        // We get datastore user info and update language
        User user = ObjectifyService.ofy().load().type(User.class).filter("email", emailUser).first().now();

        Device device = ObjectifyService.ofy().load().type(Device.class).filter("deviceId", id).first().now();

        if(device.userId.compareTo(user.id) != 0){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        String data = getJSON(Params.URL_GMAPS + device.lat.toString() + "," + device.lng.toString() + Params.GMAPS_KEY, 60000);
        String data2 = "";
        String data3 = "";

        try {

            JSONObject jObject = new JSONObject(data);
            Integer rawOffset = (Integer) jObject.get("rawOffset");

            data2 = getWeather(device.lat.toString(), device.lng.toString(), (toLocalTime(rawOffset) > device.maxHour));

            data3 = parseJson(data2, device);

        }catch(JSONException ie){

            LOGGER.info(ie.getCause().toString());

        }

        return Response.ok().entity(data3).build();
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


    public String getWeather(String lat, String lng, Boolean nextday){

        Date date = new Date();
        long timestamp;

        if(nextday){
            timestamp = date.getTime() + 86400000;
        }else{
            timestamp =date.getTime();
        }

        String path = "https://api.forecast.io/forecast/b290219f260ca3a384400c3a019b21fd/";
        String data = getJSON(path + lat + "," + lng + "," + (timestamp/1000) + "?units=si&exclude=daily,flags", 60000);

        return data;

    }

    public String parseJson(String weather, Device device) throws JSONException{

        JSONObject jObject = new JSONObject(weather);
        Integer time = (Integer) jObject.getJSONObject("currently").get("time");
        JSONObject minHourObject = (JSONObject) jObject.getJSONObject("hourly").getJSONArray("data").get(device.minHour);
        double minProbability = Double.valueOf(minHourObject.get("precipProbability").toString());
        double minIntensity = Double.valueOf(minHourObject.get("precipIntensity").toString());
        String minIcon = minHourObject.get("icon").toString();
        JSONObject maxHourObject = (JSONObject) jObject.getJSONObject("hourly").getJSONArray("data").get(device.maxHour);
        double maxProbability = Double.valueOf(maxHourObject.get("precipProbability").toString());
        double maxIntensity = Double.valueOf(maxHourObject.get("precipIntensity").toString());
        String maxIcon = maxHourObject.get("icon").toString();

        JSONObject jsonWeather = new JSONObject();
        jsonWeather.put("time", time);

        JSONObject minHour = new JSONObject();
        minHour.put("probability", minProbability);
        minHour.put("intensity", minIntensity);
        minHour.put("icon", minIcon);
        jsonWeather.put("minHour", minHour);

        JSONObject maxHour = new JSONObject();
        maxHour.put("probability", maxProbability);
        maxHour.put("intensity", maxIntensity);
        maxHour.put("icon", maxIcon);
        jsonWeather.put("maxHour", maxHour);

        return jsonWeather.toString();
    }

    public Integer toLocalTime(Integer poffset) {

        Date d = new Date();
        Integer offset = ((new Date().getTimezoneOffset() + (poffset / 60)));
        Integer n = new Date(d.getTime() + offset * 60 * 1000).getHours();

        return n;

    };

}
