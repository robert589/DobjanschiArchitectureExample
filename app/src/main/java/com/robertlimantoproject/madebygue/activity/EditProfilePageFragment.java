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
import android.widget.EditText;
import android.widget.Toast;

import com.robertlimantoproject.madebygue.Constants;
import com.robertlimantoproject.madebygue.entity.Response;
import com.robertlimantoproject.madebygue.entity.User;
import com.robertlimantoproject.madebygue.R;
import com.robertlimantoproject.madebygue.Utils;
import com.robertlimantoproject.madebygue.preference.UserPreference;
import com.robertlimantoproject.madebygue.service.EditProfileService;
import com.robertlimantoproject.madebygue.service_helper.EditProfileServiceHelper;

/**
 * A placeholder fragment containing a simple view.
 */
public class EditProfilePageFragment extends Fragment{

    private EditText etEPName, etEPAddress, etEPPhone;

    private Button btnEPSave;

    public EditProfilePageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver((editProfileReceiver), new IntentFilter(Constants.EditProfilePage.RECEIVER));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_edit_profile_page, container, false);

        initializeGraphicComponents(view);

        initializeButtonListeners();

        initializeTextViewWord();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver((editProfileReceiver), new IntentFilter(Constants.EditProfilePage.RECEIVER));
    }

    @Override
    public void onStop() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(editProfileReceiver);
        super.onStop();
    }

    private BroadcastReceiver editProfileReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("KBR", "Got message");
            handleMessage(intent);
        }
    };

    private void initializeGraphicComponents(View view){
        etEPName = (EditText) view.findViewById(R.id.etEPName);
        etEPAddress = (EditText) view.findViewById(R.id.etEPAddress);
        etEPPhone = (EditText) view.findViewById(R.id.etEPHandphone);

        btnEPSave = (Button) view.findViewById(R.id.btnEPSave);
    }

    private void initializeButtonListeners(){
        btnEPSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Set button save to indicate updating process

                if (!etEPName.getText().toString().trim().equals("")) {
                    btnEPSave.setText("Updating...");
                    btnEPSave.setClickable(false);

                    User user = retrieveUserInfo();

                    EditProfileServiceHelper helper = EditProfileServiceHelper.getInstance(getActivity());
                    helper.editProfileUser(user);
                } else {
                    Utils.showErrorDialog("Required Field is missing", "Name field cannot be empty", getActivity());
                }

            }
        });
    }

    private void initializeTextViewWord(){
        User user = UserPreference.getUser(getActivity());
        etEPPhone.setText(user.getHandphone());
        etEPAddress.setText(user.getAddress());
        etEPName.setText(user.getName());
    }

    private User retrieveUserInfo(){
        String name = etEPName.getText().toString();
        String address = etEPAddress.getText().toString();
        String phone = etEPPhone.getText().toString();

        User user = UserPreference.getUser(getActivity());
        String email = user.getEmail();
        return new User(name, phone, address, email);
    }

    private void handleMessage(Intent receiveIntent)
    {
        Response response = receiveIntent.getParcelableExtra(Constants.EditProfilePage.RESPONSE);
        btnEPSave.setClickable(true);
        if(response.getStatus() == 1){
            btnEPSave.setText(R.string.btn_ep_save);
            Toast.makeText(getActivity(), "Profile is successfully updated", Toast.LENGTH_LONG).show();
        }
        else{
            btnEPSave.setText(R.string.btn_ep_save);
            Toast.makeText(getActivity(), "Error in updating the profile status", Toast.LENGTH_LONG).show();
        }
    }

}
