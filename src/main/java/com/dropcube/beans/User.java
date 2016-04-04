package com.dropcube.beans;

import com.google.api.client.json.GenericJson;
import com.google.api.services.oauth2.model.Userinfoplus;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.model.Person;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

import com.google.api.services.oauth2.Oauth2;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.String;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The @Entity tells Objectify about our entity.  We also register it in {@link OfyHelper}
 * Our primary key @Id is set automatically by the Google Datastore for us.
 *
 * We add a @Parent to tell the object about its ancestor. We are doing this to support many
 * guestbooks.  Objectify, unlike the AppEngine library requires that you specify the fields you
 * want to index using @Index.  Only indexing the fields you need can lead to substantial gains in
 * performance -- though if not indexing your data from the start will require indexing it later.
 *
 * NOTE - all the properties are PUBLIC so that can keep the code simple.
 **/
@Entity
public class User {

    @Id public Long id;
    @Index public String email;

    public PlusPerson google;
    public String name;
    public String role;
    public String hashedPassword;
    public String provider;
    public String lang;

    protected static final Logger LOGGER = Logger.getLogger(User.class.getName());

    private static Oauth2 oauth2;

    /**
     * Simple constructor just sets the date
     **/
    public User() {
        role = "user";
        lang = "en_US";
        provider = "google";
    }

    public User(String theName, String theEmail, Person plus){

        this();

        name = theName;
        email = theEmail;

        if( plus != null ) {
            google = new PlusPerson(plus);  // Creating the Ancestor key
        }
    }

    public boolean updateLang(String language)throws IOException {

        if (language == null || language.isEmpty())
            return false;

        LOGGER.log(Level.INFO, "language: " + language);
        lang = language;

        return true;
    }


}
