package com.dropcube.handlers;

import com.dropcube.beans.User;

import static com.dropcube.constants.Params.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by edu on 7/01/17.
 */
public class Requestor {

    private User user;

    private final static Logger LOGGER = Logger.getLogger(Requestor.class.getName());

    public Requestor(User user) {
        this.user = user;
    }

    public String request(String url){

        HttpURLConnection req = null;

        try {

            URL u = new URL(url);
            req = (HttpURLConnection) u.openConnection();
            req.setRequestMethod("GET");
            req.setRequestProperty("Content-length", "0");
            req.setUseCaches(false);
            req.setAllowUserInteraction(false);
            req.setConnectTimeout(REQ_TIMEOUT);
            req.setReadTimeout(REQ_RETRIES);
            req.connect();
            int status = req.getResponseCode();

            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
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
            if (req != null) {
                try {
                    req.disconnect();
                } catch (Exception ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return null;

    }

}
