package com.robertlimantoproject.madebygue.service_helper;

import android.content.Context;
import android.content.Intent;

import com.robertlimantoproject.madebygue.Constants;
import com.robertlimantoproject.madebygue.activity.DistroGuePage;
import com.robertlimantoproject.madebygue.entity.User;
import com.robertlimantoproject.madebygue.service.DistroGueService;
import com.robertlimantoproject.madebygue.service.EditProfileService;
import com.robertlimantoproject.madebygue.service.MainPageService;

/**
 * Created by user on 8/8/2015.
 */
public class DistroGueServiceHelper {
    private static DistroGueServiceHelper instance;

    private Context context;

    private DistroGueServiceHelper(Context context){
        this.context = context;
    }

    public synchronized static DistroGueServiceHelper getInstance(Context context){
        if(instance == null){
            instance = new DistroGueServiceHelper(context);
        }

        return instance;

    }

    public void retrieveUserRelation(String email ){
        Intent intent = new Intent(context, DistroGueService.class);
        intent.putExtra(Constants.DistroGuePage.COMMAND, Constants.DistroGuePage.UPDATE_USER_RELATION_COMMAND);
        intent.putExtra(Constants.DistroGuePage.EMAIL, email);
        context.startService(intent);
    }
}
