package com.robertlimantoproject.madebygue.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.robertlimantoproject.madebygue.Constants;
import com.robertlimantoproject.madebygue.entity.Response;
import com.robertlimantoproject.madebygue.entity.User;
import com.robertlimantoproject.madebygue.processor.EditProfileProcessor;

/**
 * Created by user on 3/8/2015.
 */
public class EditProfileService extends IntentService {


    public EditProfileService(){
        super("EditProfileService");
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        User user  = intent.getParcelableExtra(Constants.EditProfilePage.USER);

        EditProfileProcessor processor = new EditProfileProcessor(user, getApplicationContext());
        Response response = processor.updateUser();
        sendResult(response);
    }

    private void sendResult(Response response){
        Intent intent = new Intent(Constants.EditProfilePage.RECEIVER);
        intent.putExtra(Constants.EditProfilePage.RESPONSE, response);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

    }
}
