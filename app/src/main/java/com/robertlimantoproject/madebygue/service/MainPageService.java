package com.robertlimantoproject.madebygue.service;

import android.app.IntentService;
import android.content.Intent;

import com.robertlimantoproject.madebygue.Constants;

/**
 * Created by user on 4/8/2015.
 */
public class MainPageService extends IntentService {

    public MainPageService(){
        super("MainPageService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String email = intent.getStringExtra(Constants.MainPage.EMAIL);

    }
}
