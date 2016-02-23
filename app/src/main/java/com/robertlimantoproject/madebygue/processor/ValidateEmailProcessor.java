package com.robertlimantoproject.madebygue.processor;

import android.util.Log;

import com.robertlimantoproject.madebygue.Constants;
import com.robertlimantoproject.madebygue.entity.Response;
import com.robertlimantoproject.madebygue.ServerConnection;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 30/7/2015.
 */
public class ValidateEmailProcessor {

    private final static String LOG = ValidateEmailProcessor.class.getSimpleName();

    private String email;

    public ValidateEmailProcessor(String email){
        this.email = email;
    }

    public Response resendEmail(){
        try {
            URL link = new URL(Constants.LINK_VALIDATION_EMAIL);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(Constants.ValidateEmailPage.EMAIL, email));

            ServerConnection serverConnection = new ServerConnection(link, params);
            Response response = serverConnection.postData();
            return response;


        }
        catch(MalformedURLException e){
            e.printStackTrace();
            Log.d(LOG, "Failure: " + e.getMessage());
            return null;

        }
    }

    public Response checkStatusValidation(){
        try{
            URL link = new URL(Constants.LINK_CSV);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(Constants.ValidateEmailPage.EMAIL, email));

            ServerConnection connectionCSV = new ServerConnection(link, params);
            Response response = connectionCSV.postData();
            return response;
        }

        catch(MalformedURLException e){
            e.printStackTrace();
            Log.d(LOG, "Failure: " + e.getMessage());
            return null;
        }
    }
}
