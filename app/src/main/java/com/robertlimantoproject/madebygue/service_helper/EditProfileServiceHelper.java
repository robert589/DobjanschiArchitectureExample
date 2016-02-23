package com.robertlimantoproject.madebygue.service_helper;

import android.content.Context;
import android.content.Intent;

import com.robertlimantoproject.madebygue.Constants;
import com.robertlimantoproject.madebygue.entity.User;
import com.robertlimantoproject.madebygue.service.EditProfileService;
import com.robertlimantoproject.madebygue.service.LoginService;

/**
 * Created by user on 8/8/2015.
 */
public class EditProfileServiceHelper {

    private static EditProfileServiceHelper instance;

    private Context context;

    private EditProfileServiceHelper(Context context){
        this.context = context;
    }

    public synchronized static EditProfileServiceHelper getInstance(Context context){
        if(instance == null){
            instance = new EditProfileServiceHelper(context);
        }

        return instance;

    }

    public void editProfileUser(User user){
        Intent intent = new Intent(context, EditProfileService.class);
        intent.putExtra(Constants.EditProfilePage.USER, user);
        context.startService(intent);
    }
}
