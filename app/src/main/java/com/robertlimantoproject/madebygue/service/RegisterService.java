package com.robertlimantoproject.madebygue.service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

import com.robertlimantoproject.madebygue.Constants;
import com.robertlimantoproject.madebygue.entity.Response;
import com.robertlimantoproject.madebygue.entity.User;
import com.robertlimantoproject.madebygue.processor.RegisterProcessor;

/**
 * Created by user on 28/7/2015.
 */
public class RegisterService extends IntentService {
    private User userInfo;

    public RegisterService(){
        super("RegisterService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        User user = intent.getParcelableExtra(Constants.RegisterPage.USER);
        RegisterProcessor processor = new RegisterProcessor(user, getApplicationContext());
        Response response = processor.registerUser();
        sendResult(response);

    }



    private void sendResult(Response response){
        Intent intent = new Intent(Constants.RegisterPage.RECEIVER);
        intent.putExtra(Constants.RegisterPage.RESPONSE, response);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

    }


}
