package com.robertlimantoproject.madebygue.processor;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.os.RemoteException;
import android.util.Log;

import com.robertlimantoproject.madebygue.Constants;
import com.robertlimantoproject.madebygue.ServerConnection;
import com.robertlimantoproject.madebygue.database.OfflineDatabase;
import com.robertlimantoproject.madebygue.entity.Category;
import com.robertlimantoproject.madebygue.entity.Product;
import com.robertlimantoproject.madebygue.entity.Response;
import com.robertlimantoproject.madebygue.entity.Subcategory;
import com.robertlimantoproject.madebygue.entity.User;
import com.robertlimantoproject.madebygue.preference.UserPreference;
import com.robertlimantoproject.madebygue.provider.OfflineContract;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 10/8/2015.
 */
public class CreatePageProcessor {
    private String LOG = CreatePageProcessor.class.getSimpleName();

    private Context context;

    private String email;


    public CreatePageProcessor(String email, Context context){
        this.email = email;
        this.context = context;
    }

    public Response checkUpdate(){
        try{
            URL link = new URL(Constants.LINK_CHECK_UPDATE);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(Constants.CreatePage.EMAIL, email));
            ServerConnection serverConnection = new ServerConnection(link, params);
            Response response = serverConnection.postData();


            return response;
        }
        catch(MalformedURLException e ){
            e.printStackTrace();
            Log.d(LOG, "Failure: " + e.getMessage());
            return null;

        }
    }

    public Response updateProduct(){

        try{
            URL link = new URL(Constants.LINK_UPDATE_PRODUCT);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            ServerConnection serverConnection = new ServerConnection(link, params);
            Response response = serverConnection.postData();

            updateProductInDB((Product[]) response.getObjectList());
            return response;
        }
        catch(MalformedURLException e ){
            e.printStackTrace();
            Log.d(LOG, "Failure: " + e.getMessage());
            return null;

        }
    }

    public Response updateSubcategory(){


        try{
            URL link = new URL(Constants.LINK_UPDATE_SUBCATEGORY);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            ServerConnection serverConnection = new ServerConnection(link, params);
            Response response = serverConnection.postData();

            updateSubcategoryInDB((Subcategory[]) response.getObjectList());
            return response;
        }
        catch(MalformedURLException e ){
            e.printStackTrace();
            Log.d(LOG, "Failure: " + e.getMessage());
            return null;

        }    }
    public Response updateCategory(){
        try{
            URL link = new URL(Constants.LINK_UPDATE_CATEGORY);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            ServerConnection serverConnection = new ServerConnection(link, params);
            Response response = serverConnection.getData();

            updateCategoryInDB((Category[]) response.getObjectList());

            return response;
        }
        catch(MalformedURLException e){
            e.printStackTrace();
            Log.d(LOG, "Failure: " + e.getMessage());
            return null;
        }
    }

    public Response updateSubproduct(){
        try{
            URL link = new URL(Constants.LINK_UPDATE_SUBPRODUCT);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            ServerConnection serverConnection = new ServerConnection(link, params);
            Response response = serverConnection.getData();

            updateSubproductInDB((Category[])response.getObjectList());

            return response;
        }
        catch(MalformedURLException e){
            e.printStackTrace();
            Log.d(LOG, "Failure: " + e.getMessage());
            return null;
        }
    }


    private void updateCategoryInDB(Category[] catList){
        Log.d(LOG, "CatList length: " + catList.length);

        ContentValues[] values= new ContentValues[catList.length];

        for(int j = 0; j < catList.length; j++){
            //ContentValues values= new ContentValues();

            //added code
            values[j]  = new ContentValues();


            int id = catList[j].getCategory_id();
            String name = catList[j].getCategory_name();

            //LOG FOR DEBUGGING
            Log.d(LOG, "Name and id: " + name + " " + id);

            values[j].put(OfflineDatabase.CategoryTable.CATEGORY_ID, id);
            values[j].put(OfflineDatabase.CategoryTable.CATEGORY_NAME, name);

            //operations.add(ContentProviderOperation.newInsert(OfflineContract.CATEGORY.CONTENT_URI).withValues(values).build());
        }

        context.getContentResolver().delete(OfflineContract.CATEGORY.CONTENT_URI, null, null );

        context.getContentResolver().bulkInsert(OfflineContract.CATEGORY.CONTENT_URI,values);

    }

    private void updateSubcategoryInDB(Subcategory[] subcatList){
        Log.d(LOG, "CatList length: " + subcatList.length);

        ContentValues[] values= new ContentValues[subcatList.length];

        for(int j = 0; j < subcatList.length; j++){
            //ContentValues values= new ContentValues();

            //added code
            values[j]  = new ContentValues();


            int id = subcatList[j].getCategory_id();
            int cat_id = subcatList[j].getSubcategory_id();
            String name = subcatList[j].getSubcategory_name();
            //LOG FOR DEBUGGING
            Log.d(LOG, "Name and id: " + name + " " + id);

            values[j].put(OfflineDatabase.SubCategoryTable.SUBCATEGORY_ID, id);
            values[j].put(OfflineDatabase.SubCategoryTable.SUBCATEGORY_NAME, name);
            values[j].put(OfflineDatabase.SubCategoryTable.CATEGORY_ID, cat_id);
            //operations.add(ContentProviderOperation.newInsert(OfflineContract.CATEGORY.CONTENT_URI).withValues(values).build());
        }

        context.getContentResolver().delete(OfflineContract.SUBCATEGORY.CONTENT_URI, null, null);

        context.getContentResolver().bulkInsert(OfflineContract.SUBCATEGORY.CONTENT_URI, values);

    }

    private void updateProductInDB(Product[] prodList){
        Log.d(LOG, "Product length: " + prodList.length);


        ContentValues[] values= new ContentValues[prodList.length];

        for(int j = 0; j < prodList.length; j++){
            //ContentValues values= new ContentValues();

            //added code
            values[j]  = new ContentValues();


            int product_id = prodList[j].getProduct_id();
            String product_desc = prodList[j].getProduct_desc();
            String creating_duration = prodList[j].getCreating_duration();
            int price = prodList[j].getPrice();
            int subcategory_id = prodList[j].getSubcategory_id();

            //LOG FOR DEBUGGING
            Log.d(LOG, "product id: " + product_id + " " + subcategory_id);

            values[j].put(OfflineDatabase.ProductTable.PRODUCT_ID, product_id);
            values[j].put(OfflineDatabase.ProductTable.PRODUCT_DESC, product_desc);
            values[j].put(OfflineDatabase.ProductTable.PRICE, price);
            values[j].put(OfflineDatabase.ProductTable.CREATE_DURATION, creating_duration);
            values[j].put(OfflineDatabase.ProductTable.SUBCATEGORY_ID, subcategory_id);

            //operations.add(ContentProviderOperation.newInsert(OfflineContract.CATEGORY.CONTENT_URI).withValues(values).build());
        }

        context.getContentResolver().delete(OfflineContract.PRODUCT.CONTENT_URI, null, null );

        context.getContentResolver().bulkInsert(OfflineContract.PRODUCT.CONTENT_URI,values);

    }
}
