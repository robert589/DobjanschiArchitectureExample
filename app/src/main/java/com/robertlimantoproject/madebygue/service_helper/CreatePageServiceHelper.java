package com.robertlimantoproject.madebygue.service_helper;

import android.content.Context;
import android.content.Intent;

import com.robertlimantoproject.madebygue.Constants;
import com.robertlimantoproject.madebygue.activity.DistroGuePage;
import com.robertlimantoproject.madebygue.service.CreatePageService;

/**
 * Created by user on 10/8/2015.
 */
public class CreatePageServiceHelper {

    private static CreatePageServiceHelper instance;

    private Context context;

    private CreatePageServiceHelper(Context context){
        this.context = context;
    }

    public synchronized static CreatePageServiceHelper getInstance(Context context){
        if(instance == null){
            instance = new CreatePageServiceHelper(context);
        }

        return instance;

    }

    public void checkUpdate(String email ){
        Intent intent = new Intent(context, CreatePageService.class);
        intent.putExtra(Constants.CreatePage.COMMAND, Constants.CreatePage.CHECK_UPDATE_COMMAND);
        intent.putExtra(Constants.CreatePage.EMAIL, email);
        context.startService(intent);
    }

    public void updateCategory(){

        Intent intent = new Intent(context, CreatePageService.class);
        intent.putExtra(Constants.CreatePage.COMMAND, Constants.CreatePage.UPDATE_CATEGORY_COMMAND);

        context.startService(intent);
    }

    public void updateSubproduct(){
        Intent intent = new Intent(context, CreatePageService.class);
        intent.putExtra(Constants.CreatePage.COMMAND, Constants.CreatePage.UPDATE_SUBCATEGORY);
        context.startService(intent);

    }

    public void updateProductType(){
        Intent intent = new Intent(context, CreatePageService.class);
        intent.putExtra(Constants.CreatePage.COMMAND, Constants.CreatePage.UPDATE_PRODUCT);
        context.startService(intent);
    }

    public void updateSubcategory(){
        Intent intent = new Intent(context, CreatePageService.class);
        intent.putExtra(Constants.CreatePage.COMMAND, Constants.CreatePage.UPDATE_SUBCATEGORY);
        context.startService(intent);
    }
}
