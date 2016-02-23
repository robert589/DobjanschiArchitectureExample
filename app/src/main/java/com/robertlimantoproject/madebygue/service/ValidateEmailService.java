package com.robertlimantoproject.madebygue.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.robertlimantoproject.madebygue.Constants;
import com.robertlimantoproject.madebygue.entity.Response;
import com.robertlimantoproject.madebygue.processor.ValidateEmailProcessor;

/**
 * Created by user on 30/7/2015.
 */
public class ValidateEmailService extends IntentService {


    private String LOG = ValidateEmailService.class.getSimpleName();

    public ValidateEmailService(){
        super("ValidateEmailService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.v(LOG, "Validate Email Service is started");

        String email = intent.getStringExtra(Constants.ValidateEmailPage.EMAIL);
        ValidateEmailProcessor processor = new ValidateEmailProcessor(email);

        String command = intent.getStringExtra(Constants.ValidateEmailPage.COMMAND);
        Response response = null;
        if(command.equals(Constants.ValidateEmailPage.RESEND_EMAIL_COMMAND)) {
            Log.v(LOG, "Resend Email is Chosen");
            response = processor.resendEmail();

        }
        else if(command.equals(Constants.ValidateEmailPage.CSV_COMMAND)){
            Log.v(LOG, "CSV is Chosen");
            response = processor.checkStatusValidation();
        }


        sendResult(response, command);

    }

    private void sendResult(Response response, String action){
        Intent intent = new Intent(Constants.ValidateEmailPage.RECEIVER);
        intent.putExtra(Constants.ValidateEmailPage.RESPONSE, response);
        intent.putExtra(Constants.ValidateEmailPage.COMMAND, action);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

    }
}
