package com.robertlimantoproject.madebygue.service_helper;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.robertlimantoproject.madebygue.Constants;
import com.robertlimantoproject.madebygue.entity.User;
import com.robertlimantoproject.madebygue.service.LoginService;
import com.robertlimantoproject.madebygue.service.RegisterService;

/**
 * Created by user on 8/8/2015.
 */
public class LoginServiceHelper {

    private static LoginServiceHelper instance;

    private Context context;

    private LoginServiceHelper(Context context){
        this.context = context;
    }

    public synchronized static LoginServiceHelper getInstance(Context context){
        if(instance == null){
            instance = new LoginServiceHelper(context);
        }

        return instance;

    }

    public void loginUser(User user){


        Intent intent = new Intent(context, LoginService.class);
        intent.putExtra(Constants.LoginPage.USER, user);
        context.startService(intent);
    }
}
