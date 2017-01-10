package com.dropcube.beans.mocks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DWeather {

    @SerializedName("rain")
    @Expose
    private Integer rain;
    @SerializedName("hot")
    @Expose
    private Integer hot;
    @SerializedName("snow")
    @Expose
    private Integer snow;
    @SerializedName("wind")
    @Expose
    private Integer wind;
    @SerializedName("storm")
    @Expose
    private Boolean storm;

    public Integer getRain() {
        return rain;
    }

    public void setRain(Integer rain) {
        this.rain = rain;
    }

    public Integer getHot() {
        return hot;
    }

    public void setHot(Integer hot) {
        this.hot = hot;
    }

    public Integer getSnow() {
        return snow;
    }

    public void setSnow(Integer snow) {
        this.snow = snow;
    }

    public Integer getWind() {
        return wind;
    }

    public void setWind(Integer wind) {
        this.wind = wind;
    }

    public Boolean getStorm() {
        return storm;
    }

    public void setStorm(Boolean storm) {
        this.storm = storm;
    }

}