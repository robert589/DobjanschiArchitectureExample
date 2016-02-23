package com.robertlimantoproject.madebygue.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.robertlimantoproject.madebygue.Constants;
import com.robertlimantoproject.madebygue.R;
import com.robertlimantoproject.madebygue.entity.Response;
import com.robertlimantoproject.madebygue.preference.UserPreference;
import com.robertlimantoproject.madebygue.service_helper.MainPageServiceHelper;

/**
 * A placeholder fragment containing a simple view.
 */

/**
 * This fragment only for screen slider
 */
public class MainPageFragment extends Fragment {

    private String LOG = MainPageFragment.class.getSimpleName();

    private Button ibIdGue, ibCreate, ibInfo, ibDistro;

    public MainPageFragment() {
    }


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver((mainPageReceiver), new IntentFilter(Constants.MainPage.RECEIVER));

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_page, container, false);

        initializeGraphicComponents(view);

        initializeButtonListeners();
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver((mainPageReceiver), new IntentFilter(Constants.MainPage.RECEIVER));
    }

    @Override
    public void onResume() {
        super.onResume();

        //update user profile to preference
        //DELETED, WASTE RESOURCES
        /*
        String email = UserPreference.getUser(getActivity()).getEmail();
        retrieveUserRelation(email);
        */
    }

    @Override
    public void onStop() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mainPageReceiver);
        super.onStop();
    }

    private BroadcastReceiver mainPageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(LOG, "Got message");
            handleMessage(intent);
        }
    };


    private void initializeGraphicComponents(View view){
        ibIdGue = (Button) view.findViewById(R.id.ibIDGue);
        ibCreate = (Button) view.findViewById(R.id.ibCreate);
        ibInfo = (Button) view.findViewById(R.id.ibInfo);
        ibDistro = (Button) view.findViewById(R.id.ibDistro);
    }

    private void initializeButtonListeners(){
        ibIdGue.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), IdGuePage.class);
                startActivity(intent);
            }
        });

        ibCreate.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(getActivity(), CreatePage.class);
                startActivity(intent);
            }
        });
    }

    private void retrieveUserRelation(String email){
        MainPageServiceHelper helper = MainPageServiceHelper.getInstance(getActivity());
        helper.retrieveUserRelation(email);
    }

    private void handleMessage(Intent receiveIntent){
        Response response = receiveIntent.getParcelableExtra(Constants.MainPage.RESPONSE);

        if(response.getStatus() == 1){
            Toast.makeText(getActivity(), "User profile updated..", Toast.LENGTH_LONG);
        }
        else{
            Toast.makeText(getActivity(), "failed to update user profile..", Toast.LENGTH_LONG);

        }
    }


}
