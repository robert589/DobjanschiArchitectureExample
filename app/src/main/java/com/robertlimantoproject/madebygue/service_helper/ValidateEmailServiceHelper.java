package com.robertlimantoproject.madebygue.service_helper;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.robertlimantoproject.madebygue.Constants;
import com.robertlimantoproject.madebygue.service.ValidateEmailService;

/**
 * Created by user on 8/8/2015.
 */
public class ValidateEmailServiceHelper {

    private String LOG = ValidateEmailServiceHelper.class.getSimpleName();

    private static ValidateEmailServiceHelper instance;

    private Context context;

    private ValidateEmailServiceHelper (Context context){
        this.context = context;
    }

    public synchronized static ValidateEmailServiceHelper getInstance(Context context){
        if(instance == null){
            instance = new ValidateEmailServiceHelper(context);
        }

        return instance;

    }

    public void resendEmail(String email){
        Log.v(LOG, "Starting Service");
        Intent intent = new Intent(context, ValidateEmailService.class);
        intent.putExtra(Constants.ValidateEmailPage.EMAIL, email);
        intent.putExtra(Constants.ValidateEmailPage.COMMAND, Constants.ValidateEmailPage.RESEND_EMAIL_COMMAND);
        context.startService(intent);
    }

    public void checkStatusValidationEmail(String email){
        Intent intent = new Intent(context, ValidateEmailService.class);
        intent.putExtra(Constants.ValidateEmailPage.EMAIL, email);
        intent.putExtra(Constants.ValidateEmailPage.COMMAND, Constants.ValidateEmailPage.CSV_COMMAND);
        context.startService(intent);
    }
}
