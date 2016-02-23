package com.robertlimantoproject.madebygue.processor;

import android.content.Context;
import android.util.Log;

import com.robertlimantoproject.madebygue.Constants;
import com.robertlimantoproject.madebygue.entity.Response;
import com.robertlimantoproject.madebygue.entity.User;
import com.robertlimantoproject.madebygue.ServerConnection;
import com.robertlimantoproject.madebygue.preference.UserPreference;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 3/8/2015.
 */
public class EditProfileProcessor {

    private final static String LOG = EditProfileProcessor.class.getSimpleName();

    private User user;

    private Context context;

    public EditProfileProcessor(User user, Context context){
        this.context = context;
        this.user = user;
    }

    public Response updateUser(){
        try{
            URL url = new URL(Constants.LINK_EDIT_PROFILE);

            List<NameValuePair> params = new ArrayList<NameValuePair>();


            params.add(new BasicNameValuePair(Constants.EditProfilePage.EMAIL, user.getEmail()));
            params.add(new BasicNameValuePair(Constants.EditProfilePage.NAME, user.getName()));
            params.add(new BasicNameValuePair(Constants.EditProfilePage.ADDRESS, user.getAddress()));
            params.add(new BasicNameValuePair(Constants.EditProfilePage.NO_HP, user.getHandphone()));

            ServerConnection serverConnection = new ServerConnection(url, params);
            Response response = serverConnection.postData();
            Log.d(LOG, "" + response.getStatus());

            if(response.getStatus() == 1){
                updateUserPreference();
            }

            return response;

        }
        catch(MalformedURLException e){
            e.printStackTrace();
            Log.d(LOG, "Failure: " + e.getMessage());
            return null;
        }
    }

    private void updateUserPreference(){
        String password = UserPreference.getUser(context).getPassword();

        //add password to the user
        user.setPassword(password);

        //update it
        UserPreference.setUser(context, user);
    }
}
