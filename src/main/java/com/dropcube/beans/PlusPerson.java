package com.dropcube.beans;

/**
 * Created by edu on 2/04/16.
 */

import com.google.api.services.plus.model.Person;
import com.sun.org.apache.xerces.internal.impl.dv.xs.BooleanDV;
import com.sun.org.apache.xpath.internal.operations.Bool;

public class PlusPerson {

    public String gender;
    public PlusImage image;
    public String cover;

    public PlusPerson(){

    }

    public PlusPerson( Person person){

        this();

        gender = person.getGender();
        image = new PlusImage(person.getImage().getIsDefault(), person.getImage().getUrl());
        cover = person.getCover().getCoverPhoto().getUrl();
    }

    static public class PlusImage{

        public Boolean isDefault;
        public String url;

        public PlusImage(){

        }

        public PlusImage( Boolean pIsDefault, String pUrl ){
            this();
            isDefault = pIsDefault;
            url = pUrl;
        }

    }


}

