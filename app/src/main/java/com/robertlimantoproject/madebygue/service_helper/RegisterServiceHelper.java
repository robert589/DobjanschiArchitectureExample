package com.robertlimantoproject.madebygue.service_helper;

import android.content.Context;
import android.content.Intent;

import com.robertlimantoproject.madebygue.Constants;
import com.robertlimantoproject.madebygue.entity.User;
import com.robertlimantoproject.madebygue.service.RegisterService;

/**
 * Created by user on 8/8/2015.
 */
public class RegisterServiceHelper {
    private static RegisterServiceHelper instance;

    private Context context;

    private RegisterServiceHelper(Context context){
        this.context = context;
    }

    public synchronized static RegisterServiceHelper getInstance(Context context){
        if(instance == null){
            instance = new RegisterServiceHelper(context);
        }

        return instance;

    }

    public void registerUser(User user){
        Intent intent = new Intent(context, RegisterService.class);
        intent.putExtra(Constants.RegisterPage.USER, user);
        context.startService(intent);
    }
}
