package com.dropcube.biz;

import com.dropcube.beans.Device;
import com.dropcube.beans.User;
import com.dropcube.beans.mocks.Weather;
import com.dropcube.constants.Params;
import com.dropcube.exceptions.DropcubeException;
import com.dropcube.handlers.Requestor;
import com.dropcube.handlers.Timezone;
import com.google.gson.Gson;

import java.util.logging.Logger;

import static com.dropcube.constants.Params.*;

/**
 * Created by edu on 7/01/17.
 */
public class WeatherBiz {

    private User user;

    private final static Logger LOGGER = Logger.getLogger(WeatherBiz.class.getName());

    private Requestor REQUESTOR = null;
    private Timezone TIMEZONE = null;

    private Gson GSON = new Gson();

    public WeatherBiz(User user) {
        this.user = user;
        REQUESTOR = new Requestor(this.user);
        TIMEZONE = new Timezone(this.user);
    }

    public boolean getStorm(Weather weather){

        boolean storm = false;


        storm = (weather.getCurrently().getNearestStormDistance() != null);

        LOGGER.info("There are storms nearly: " + storm);

        return storm;

    }

    public int getRain(Weather weather, Integer offset){

        double intensity;
        double probability;

        offset = (offset == null) ? 0 : offset;
        LOGGER.info("Getting houly data for offset: " + offset);
        intensity = weather.getHourly().getData().get(offset).getPrecipIntensity();
        probability = weather.getHourly().getData().get(offset).getPrecipProbability();

        LOGGER.info("Rain intensity is: " + intensity);
        LOGGER.info("Rain probability is: " + probability);

        if(intensity <= 0){
            return 0;
        }else if(intensity >= 60){
            return Params.BINARY_MAX;
        }else{
            double prob = (intensity * Params.INCHES_TO_MM * Params.RAIN_CONSTANT) * Params.BINARY_MAX_REL;
            prob = (prob  > Params.BINARY_MAX) ? Params.BINARY_MAX: (prob < 0) ? 0 : prob;
            return (int) prob;
        }


    }

    public int getHot(Weather weather, Integer offset){

        double temperature;

        offset = (offset == null) ? 0 : offset;
        LOGGER.info("Getting houly data for offset: " + offset);
        temperature = weather.getHourly().getData().get(offset).getTemperature();

        LOGGER.info("Temperature is: " + temperature);

        if(temperature <= 27){
            return 0;
        }else if(temperature >= 40){
            return Params.BINARY_MAX;
        }else{
            double prob = ((temperature - 27) * Params.HOT_CONSTANT) * Params.BINARY_MAX_REL;
            prob = (prob  > Params.BINARY_MAX) ? Params.BINARY_MAX: (prob < 0) ? 0 : prob;
            return (int) prob;
        }

    }

    public int getWind(Weather weather, Integer offset){

        double speed;

        offset = (offset == null) ? 0 : offset;
        LOGGER.info("Getting houly data for offset: " + offset);
        speed = weather.getHourly().getData().get(offset).getWindSpeed();

        LOGGER.info("Wind speed is: " + speed);

        if(speed <= 27){
            return 0;
        }else if(speed >= 50){
            return Params.BINARY_MAX;
        }else{
            double prob = ((speed - 27) * Params.WIND_CONSTANT) * Params.BINARY_MAX_REL;
            prob = (prob > Params.BINARY_MAX) ? Params.BINARY_MAX : (prob < 0) ? 0 : prob;
            return (int) prob;
        }

    }

    public Weather get(Double lat, Double lng) throws DropcubeException{

        LOGGER.info("Getting weather for coordinates: " + lat + ", " + lng);

        Integer offset = TIMEZONE.getOffset(lat, lng);

        StringBuffer path = new StringBuffer();
        path.append(DARK_SKY_API_URL);
        path.append(DARK_SKY_API_TOKEN);
        path.append(lat.toString());
        path.append(",");
        path.append(lng.toString());
        //path.append(",");
        //path.append(String.valueOf(offset/1000));
        path.append("?units=ca&exclude=daily,flags");

        String response = REQUESTOR.request(path.toString());

        int _trunk = (response.length() > 500) ? 500 : response.length();
        LOGGER.info("Weather request: " + path);
        LOGGER.info("Weather info retrieved: " + response.substring(0, _trunk));

        Weather weather = GSON.fromJson(response, Weather.class);

        return weather;

    }

}
