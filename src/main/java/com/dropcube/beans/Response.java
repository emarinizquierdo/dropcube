package com.dropcube.beans;

import com.google.appengine.api.datastore.Text;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * @version 2.0
 * @author CIBER 2013
 * */
public class Response {

    private Object data = null;

    private String msg = "";

    private int status = 200;

    public Response() {
        super();
    }

    /***/
    public Response(int code, String message) {
        this.status = code;
        this.msg = message;
    }

    /***/
    public Response(int code, String message, Object data) {
        this.status = code;
        this.msg = message;
        this.data = data;
    }

    /**
     * @param response
     */
    public Response(Object response) {
        super();
        this.data = response;
    }

    public String toString() {
        if (data != null)
            return (String) data;

        return null;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private static JsonSerializer<Date> dateSerialize = new JsonSerializer<Date>() {
        @Override
        public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
            return src == null ? null : new JsonPrimitive(src.getTime());
        }
    };

    private static JsonDeserializer<Date> dateDeserialize = new JsonDeserializer<Date>() {
        @Override
        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return json == null ? null : new Date(json.getAsLong());
        }
    };

    public static JsonSerializer<Text> textSerialize = new JsonSerializer<Text>() {
        @Override
        public JsonElement serialize(Text src, Type typeOfSrc, JsonSerializationContext context) {
            return src == null ? null : new JsonParser().parse(src.getValue());
        }
    };

    public static JsonDeserializer<Text> textDeserialize = new JsonDeserializer<Text>() {
        @Override
        public Text deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return json == null ? null : new Text(json.toString());
        }
    };


    public String toJsonExcludeFieldsWithoutExposeAnnotation() {
        StringBuilder output = new StringBuilder();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, dateSerialize)
                .registerTypeAdapter(Date.class, dateDeserialize)
                .registerTypeAdapter(Text.class, textSerialize)
                .registerTypeAdapter(Text.class, textDeserialize)
                .excludeFieldsWithoutExposeAnnotation().create();

        output.append("{\"status\":\"" + this.status + "\",\"msg\":");
        if (this.msg != null) {
            try {
                output.append(gson.toJson(this.msg));
            } catch (Exception e) {
                output.append("\"" + this.msg + "\"");
            }
        } else {
            output.append("\"" + this.msg + "\"");
        }
        if (!(this.data instanceof Exception)) {
            output.append(",\"data\":");
            if (this.data instanceof String) {
                output.append(this.data);
            } else {
                output.append(gson.toJson(this.data));
            }
        }

        output.append("}");

        return output.toString();
    }

    public String toJson() {
        StringBuilder output = new StringBuilder();

        Gson gson = new GsonBuilder().disableHtmlEscaping().create();

        output.append("{\"status\":\"" + this.status + "\",\"msg\":");
        if (this.msg != null) {
            try {
                output.append(gson.toJson(this.msg));
            } catch (Exception e) {
                output.append("\"" + this.msg + "\"");
            }
        } else {
            output.append("\"" + this.msg + "\"");
        }

        if (!(this.data instanceof Exception)) {
            output.append(",\"data\":");
            if (this.data instanceof String) {
                output.append(this.data);
            } else {
                output.append(gson.toJson(this.data));
            }
        }

        output.append("}");

        return output.toString();
    }
}
/** Consultants in Business Engineering Research, S.L.U. 2013 */
