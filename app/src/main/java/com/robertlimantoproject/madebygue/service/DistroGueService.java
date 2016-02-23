package com.robertlimantoproject.madebygue.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.robertlimantoproject.madebygue.Constants;
import com.robertlimantoproject.madebygue.entity.Response;
import com.robertlimantoproject.madebygue.processor.DistroGueProcessor;
import com.robertlimantoproject.madebygue.processor.ValidateEmailProcessor;

/**
 * Created by user on 8/8/2015.
 */
public class DistroGueService extends IntentService {


    private String LOG = DistroGueService.class.getSimpleName();

    public DistroGueService(){
        super("DistoGueService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.v(LOG, "Distro Gue Service is started");

        String email = intent.getStringExtra(Constants.DistroGuePage.EMAIL);
        DistroGueProcessor processor = new DistroGueProcessor(email, getApplicationContext());

        String command = intent.getStringExtra(Constants.DistroGuePage.COMMAND);
        Response response = null;
        if(command.equals(Constants.DistroGuePage.UPDATE_USER_RELATION_COMMAND)) {
            Log.v(LOG, "update user relation is Chosen");
            response = processor.retrieveUserRelation();

        }


        sendResult(response, command);

    }

    private void sendResult(Response response, String action){
        Intent intent = new Intent(Constants.DistroGuePage.RECEIVER);
        intent.putExtra(Constants.DistroGuePage.RESPONSE, response);
        intent.putExtra(Constants.DistroGuePage.COMMAND, action);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

    }
}
