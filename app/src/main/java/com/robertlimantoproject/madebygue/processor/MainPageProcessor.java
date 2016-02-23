package com.robertlimantoproject.madebygue.processor;

import android.content.Context;
import android.util.Log;

import com.robertlimantoproject.madebygue.Constants;
import com.robertlimantoproject.madebygue.ServerConnection;
import com.robertlimantoproject.madebygue.entity.Response;
import com.robertlimantoproject.madebygue.entity.User;
import com.robertlimantoproject.madebygue.preference.UserPreference;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *  Created by user on 8/8/2015.


 /**
 * The background process for retrieving user_relation will be removed
 */
public class MainPageProcessor {

    private String LOG = MainPageProcessor.class.getSimpleName();

    private Context context;

    private String email;


    public MainPageProcessor(String email, Context context){
        this.email = email;
        this.context = context;
    }

    public Response retrieveUserRelation(String email){
        try{
            URL link = new URL(Constants.LINK_RETRIEVE_USER_RELATION);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(Constants.RegisterPage.EMAIL, email));
            ServerConnection serverConnection = new ServerConnection(link, params);
            Response response = serverConnection.getData();

            if(response.getStatus() == 1){
                updateUserPreference(response);
            }


            return response;
        }
        catch(MalformedURLException e ){
            e.printStackTrace();
            Log.d(LOG, "Failure: " + e.getMessage());
            return null;

        }
    }

    private void updateUserPreference(Response response){
            //Update the user relation

        User userRelation = (User) response.getObject();
        UserPreference.setFollower(context, userRelation.getFollower());
        UserPreference.setRating(context, userRelation.getRating());
        UserPreference.setFollowing(context, userRelation.getFollowing());

    }
}
