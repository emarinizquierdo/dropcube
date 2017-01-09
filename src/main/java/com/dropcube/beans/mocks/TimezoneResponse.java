package com.dropcube.beans.mocks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimezoneResponse {

    @SerializedName("dstOffset")
    @Expose
    private Long dstOffset;
    @SerializedName("rawOffset")
    @Expose
    private Long rawOffset;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("timeZoneId")
    @Expose
    private String timeZoneId;
    @SerializedName("timeZoneName")
    @Expose
    private String timeZoneName;

    public Long getDstOffset() {
        return dstOffset;
    }

    public void setDstOffset(Long dstOffset) {
        this.dstOffset = dstOffset;
    }

    public Long getRawOffset() {
        return rawOffset;
    }

    public void setRawOffset(Long rawOffset) {
        this.rawOffset = rawOffset;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimeZoneId() {
        return timeZoneId;
    }

    public void setTimeZoneId(String timeZoneId) {
        this.timeZoneId = timeZoneId;
    }

    public String getTimeZoneName() {
        return timeZoneName;
    }

    public void setTimeZoneName(String timeZoneName) {
        this.timeZoneName = timeZoneName;
    }

}