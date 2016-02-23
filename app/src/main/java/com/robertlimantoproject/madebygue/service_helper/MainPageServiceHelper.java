package com.robertlimantoproject.madebygue.service_helper;

import android.content.Context;
import android.content.Intent;

import com.robertlimantoproject.madebygue.Constants;
import com.robertlimantoproject.madebygue.entity.User;
import com.robertlimantoproject.madebygue.service.MainPageService;
import com.robertlimantoproject.madebygue.service.RegisterService;

/**
 * Created by user on 8/8/2015.
 */
public class MainPageServiceHelper {
    private static MainPageServiceHelper instance;

    private Context context;

    private MainPageServiceHelper(Context context){
        this.context = context;
    }

    public synchronized static MainPageServiceHelper getInstance(Context context){
        if(instance == null){
            instance = new MainPageServiceHelper(context);
        }

        return instance;

    }

    public void retrieveUserRelation(String email ){
        Intent intent = new Intent(context, MainPageService.class);
        intent.putExtra(Constants.MainPage.EMAIL, email);
        context.startService(intent);
    }
}
