package com.robertlimantoproject.madebygue.service;

import android.app.IntentService;
import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.os.RemoteException;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.robertlimantoproject.madebygue.Constants;
import com.robertlimantoproject.madebygue.database.OfflineDatabase;
import com.robertlimantoproject.madebygue.entity.Category;
import com.robertlimantoproject.madebygue.entity.Response;
import com.robertlimantoproject.madebygue.processor.CreatePageProcessor;
import com.robertlimantoproject.madebygue.processor.ValidateEmailProcessor;
import com.robertlimantoproject.madebygue.provider.OfflineContract;

import java.util.ArrayList;

/**
 * Created by user on 10/8/2015.
 */
public class CreatePageService extends IntentService{


    private String LOG = CreatePageService.class.getSimpleName();

    public CreatePageService(){
        super("CreatePageService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.v(LOG, "Create page Service is started");

        String email = intent.getStringExtra(Constants.CreatePage.EMAIL);
        CreatePageProcessor processor = new CreatePageProcessor(email, getApplicationContext());

        String command = intent.getStringExtra(Constants.CreatePage.COMMAND);
        Response response = null;
        if(command.equals(Constants.CreatePage.CHECK_UPDATE_COMMAND)) {
            Log.v(LOG, "Check update is Chosen");
            response = processor.checkUpdate();

        }
        else if(command.equals(Constants.CreatePage.UPDATE_CATEGORY_COMMAND)){
            Log.v(LOG, "Update category is Chosen");
            response = processor.updateCategory();

        }

        else if(command.equals(Constants.CreatePage.UPDATE_PRODUCT)){
            Log.v(LOG, "Update Product type is chosen");
            response  = processor.updateProduct();
        }
        else if(command.equals(Constants.CreatePage.UPDATE_SUBCATEGORY)){
            Log.v(LOG, "Update Subcategory is chosen");
            response = processor.updateSubcategory();
        }
        else if(command.equals(Constants.CreatePage.UPDATE_SUBPRODUCT)){
            Log.v(LOG, "Update Subproduct is chosen");
            response = processor.updateSubproduct();
        }


        sendResult(response, command);

    }

    private void sendResult(Response response, String action){
        Intent intent = new Intent(Constants.CreatePage.RECEIVER);
        intent.putExtra(Constants.CreatePage.RESPONSE, response);
        intent.putExtra(Constants.CreatePage.COMMAND, action);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

    }


}
