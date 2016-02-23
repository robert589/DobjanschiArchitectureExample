package com.robertlimantoproject.madebygue.activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.robertlimantoproject.madebygue.Constants;
import com.robertlimantoproject.madebygue.R;
import com.robertlimantoproject.madebygue.database.OfflineDatabase;
import com.robertlimantoproject.madebygue.entity.Category;
import com.robertlimantoproject.madebygue.entity.Response;
import com.robertlimantoproject.madebygue.preference.UserPreference;
import com.robertlimantoproject.madebygue.provider.OfflineContract;
import com.robertlimantoproject.madebygue.provider.OfflineProvider;
import com.robertlimantoproject.madebygue.service_helper.CreatePageServiceHelper;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class CreatePageFragment extends Fragment {

    private String LOG = CreatePageFragment.class.getSimpleName();

    private Button btnCPUpdate;

    private LinearLayout LLCP;

    public CreatePageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver((createPageReceiver), new IntentFilter(Constants.CreatePage.RECEIVER));

        //Retrieve email from userpreference
        String email = UserPreference.getUser(getActivity()).getEmail();

        //
        CreatePageServiceHelper helper = CreatePageServiceHelper.getInstance(getActivity());
        helper.checkUpdate(email);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_create_page, container, false);

        initializeGraphicComponents(view);

        initializeButtonListeners();


        retrieveComponents();



        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver((createPageReceiver), new IntentFilter(Constants.CreatePage.RECEIVER));
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStop() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(createPageReceiver);
        super.onStop();
    }

    private BroadcastReceiver createPageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("KBR", "Got message");
            handleMessage(intent);
        }
    };

    private void handleMessage(Intent receivedIntent){
        Response response = receivedIntent.getParcelableExtra(Constants.CreatePage.RESPONSE);
        String command = receivedIntent.getStringExtra(Constants.CreatePage.COMMAND);
        Log.v(LOG, "Receive: " + command + " command");

        int code = response.getStatus();
        if(command.equals(Constants.CreatePage.CHECK_UPDATE_COMMAND)){

            if(code >= 1000){
                code -= 1000;

                CreatePageServiceHelper helper = new CreatePageServiceHelper.getInstance(getActivity());
                helper.updateSubproduct();
            }

            if(code >= 100){
                code -= 100;

                CreatePageServiceHelper helper = CreatePageServiceHelper.getInstance(getActivity());
                helper.updateSubcategory();
            }

            if(code >= 10){
                code -= 10;

                CreatePageServiceHelper helper = CreatePageServiceHelper.getInstance(getActivity());
                helper.updateCategory();
            }
            if(code == 1){
                //update product_type
                CreatePageServiceHelper helper = CreatePageServiceHelper.getInstance(getActivity());
                helper.updateProductType();
                //// TODO: 10/8/2015

            }

        }
        else if(command.equals(Constants.CreatePage.UPDATE_CATEGORY_COMMAND)){
            Toast.makeText(getActivity(), "Click Button Update to update the category", Toast.LENGTH_LONG).show();


            btnCPUpdate.setVisibility(View.VISIBLE);
        }
        else if(command.equals(Constants.CreatePage.UPDATE_SUBCATEGORY)){
            Toast.makeText(getActivity(), "Subcategory updated", Toast.LENGTH_LONG).show();

        }
        else if(command.equals(Constants.CreatePage.UPDATE_PRODUCT)){
            Toast.makeText(getActivity(), "Product updated", Toast.LENGTH_LONG).show();
        }
    }

    private void initializeGraphicComponents(View view){
        LLCP = (LinearLayout) view.findViewById(R.id.LLCP);

        btnCPUpdate = (Button) view.findViewById(R.id.btnCPUpdate);
    }

    private void initializeButtonListeners(){
        btnCPUpdate.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Log.d(LOG, "btnCPUpdate fired");
                ProgressDialog pdUploadButtonWait  = ProgressDialog.show(getActivity(), "Waiting", "Retrieving Components...", true, false);

                retrieveComponents();

                pdUploadButtonWait.dismiss();
                Log.d(LOG, "It is trigerred");
            }
        });
    }

    private void retrieveComponents() {


       Cursor cursor =  getActivity().getContentResolver().query(OfflineContract.CATEGORY.CONTENT_URI,
                OfflineContract.CATEGORY.PROJECTION_ALL,
                null,
                null,
                    OfflineContract.CATEGORY.CATEGORY_NAME
                );


        ArrayList<Category> catList = new ArrayList<Category>();
        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    int category_id = cursor.getInt(cursor.getColumnIndex(OfflineDatabase.CategoryTable.CATEGORY_ID));
                    String category_name = cursor.getString(cursor.getColumnIndex(OfflineDatabase.CategoryTable.CATEGORY_NAME));
                    Log.d(LOG, "Fra: " + category_name + " " + category_id);
                    catList.add(new Category(category_id,category_name));
                }
                while(cursor.moveToNext());
            }
        }

        setButtonLayout(catList);
    }

    private void setButtonLayout(ArrayList<Category> catList){

        Log.d(LOG, "Size: " + catList.size());
        ArrayList<LinearLayout> linearLayoutLists = new ArrayList<LinearLayout>();

        for(int i = 0; i < catList.size(); i++){

            Category cat = catList.get(i);
             Button view = (Button) cat.getView(getActivity());

            LinearLayout tempLayout = new LinearLayout(getActivity());
            tempLayout.setOrientation(LinearLayout.HORIZONTAL);
            tempLayout.setMinimumWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            tempLayout.setMinimumHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            tempLayout.addView(view);
            if( (i + 1) < catList.size() ){
                i++;
                 cat = catList.get(i);
                view = (Button) cat.getView(getActivity());
                tempLayout.addView(view);

            }

            linearLayoutLists.add(tempLayout);

        }

        for(LinearLayout linearLayout : linearLayoutLists){
            LLCP.addView(linearLayout);
        }

    }
}
