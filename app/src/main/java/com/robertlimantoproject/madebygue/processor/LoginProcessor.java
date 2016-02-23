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
 * Created by user on 23/7/2015.
 */
public class LoginProcessor {

    private    User user ;

    private Context context;

    private final static String LOG = LoginProcessor.class.getSimpleName();

    public  LoginProcessor(User user, Context context){
        this.user = user;
        this.context = context;
    }

    public Response loginUser(){
        try {
            URL url = new URL(Constants.LINK_LOGIN_USER);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(Constants.RegisterPage.EMAIL, user.getEmail()));
            params.add(new BasicNameValuePair(Constants.RegisterPage.PASSWORD, user.getPassword()));
            params.add(new BasicNameValuePair(Constants.RegisterPage.NAME, user.getName()));
            params.add(new BasicNameValuePair(Constants.RegisterPage.ADDRESS, user.getAddress()));
            params.add(new BasicNameValuePair(Constants.RegisterPage.NO_HP, user.getHandphone()));

            ServerConnection serverConnection = new ServerConnection(url, params);
            Response response = serverConnection.postData();
            Log.d(LOG, "" + response.getStatus());

            if(response.getStatus() == 1) {
                updateUserPreference(response);
            }
            return response;


        }
        catch(MalformedURLException e){
            e.printStackTrace();
            Log.d(LOG, "Failure: " + e.getMessage());
            return null;

        }


    }


    private void updateUserPreference(Response response ){
         //update shared preference, so the app will remember who is it the email
        if(response.getObject() instanceof  User ){
            User user = (User) response.getObject();
            Log.d(LOG, "" + user.getName());
            UserPreference.setUser(context, user);
        }
    }
}
