package com.dropcube.handlers.jfirebase;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: reiz
 * Date: 4/21/12
 * Time: 11:43 AM
 */
public class Driver implements IDriver {

    private String channel = "https://dropcube-c11b6.firebaseio.com/";
    private String key = "g8LHGVLv3QJ8yFBatXY1cmzS1tCkwEBY1qT6PIoo";

    public boolean write(String path, Map<String, String> map){

        HttpURLConnection c = null;

        try{

            JSONObject json = new JSONObject();
            for (String key: map.keySet()){
                json.put(key,map.get(key));
            }

            URL u = new URL(getChannelUrl(path));
            c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("POST");
            c.setRequestProperty("X-HTTP-Method-Override", "PATCH");
            c.setRequestProperty("Content-length", "0");
            c.setRequestProperty("Accept", "application/json");
            c.setRequestProperty("Content-type", "application/json");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.setDoOutput(true);
            OutputStream os = c.getOutputStream();
            os.write(json.toString().getBytes("UTF-8"));
            os.close();
            c.connect();
            int status = c.getResponseCode();

            if (status == 200)
                return true;
            else
                return false;
        } catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public Reader read(String uri) {
        try{
            StringBuffer sb = new StringBuffer();
            sb.append(channel);
            sb.append("/");
            sb.append(uri);
            sb.append(".json");
            addKey(sb);
            String url = sb.toString();
            return getResultReader(url);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean delete(String uri) {
        try{
            StringBuffer sb = new StringBuffer();
            sb.append(channel);
            sb.append("/");
            sb.append(uri);
            sb.append(".json");
            addKey(sb);
            String url = sb.toString();
            HttpDelete delete = new HttpDelete(url);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(delete);
            if (response.getStatusLine().getStatusCode() == 200)
                return true;
            else
                return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public String getChannelUrl(String path){
        StringBuffer sb = new StringBuffer();
        sb.append(channel);
        sb.append(path);
        sb.append(".json");
        addKey(sb);
        return sb.toString();
    }

    private void addKey(StringBuffer sb){
        if (key != null) {
            sb.append("?auth=");
            sb.append(key);
        }
    }

    public Reader getResultReader(String resource) throws Exception {
        URL url = new URL(resource);
        URLConnection connection = url.openConnection();
        connection.setConnectTimeout(30000);
        return new InputStreamReader(connection.getInputStream());
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}