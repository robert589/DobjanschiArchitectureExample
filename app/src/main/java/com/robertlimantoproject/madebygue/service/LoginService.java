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
import com.robertlimantoproject.madebygue.processor.LoginProcessor;

public class LoginService extends IntentService {




    private Response response = null;

    public LoginService(){
        super("LoginService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        User user = intent.getParcelableExtra(Constants.LoginPage.USER);
        LoginProcessor processor = new LoginProcessor(user, getApplicationContext());
        Response response = processor.loginUser();
    }

    private void sendResult(Response response){
        Intent intent = new Intent(Constants.LoginPage.RECEIVER);
        intent.putExtra(Constants.LoginPage.RESPONSE, response);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

    }




}
