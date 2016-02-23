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
import android.widget.TextView;
import android.widget.Toast;

import com.robertlimantoproject.madebygue.Constants;
import com.robertlimantoproject.madebygue.entity.Response;
import com.robertlimantoproject.madebygue.R;
import com.robertlimantoproject.madebygue.Utils;
import com.robertlimantoproject.madebygue.preference.UserPreference;
import com.robertlimantoproject.madebygue.service_helper.ValidateEmailServiceHelper;

/**
 * A placeholder fragment containing a simple view.
 */
public class ValidateEmailPageFragment extends Fragment {

    private Button btnVEValLater, btnVEResend, btnVEDone;

    private String email;

    private TextView tvAnnouncement;


    public final static String LOG = ValidateEmailPageFragment.class.getSimpleName();

    public ValidateEmailPageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver((validateEmailReceiver), new IntentFilter(Constants.ValidateEmailPage.RECEIVER));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_validate_email_page, container, false);

        initializeGraphicComponent(view);

        initializeButtonListeners();

        email = UserPreference.getUser(getActivity()).getEmail();
        //TODO
        //Check if email has been sent or not
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver((validateEmailReceiver), new IntentFilter(Constants.ValidateEmailPage.RECEIVER));
    }

    @Override
    public void onResume() {
        super.onResume();
        resendEmail();
    }

    @Override
    public void onStop() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(validateEmailReceiver);
        super.onStop();
    }

    private BroadcastReceiver validateEmailReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(LOG, "Got message");
            handleMessage(intent);
        }
    };

    private void handleMessage(Intent receiveIntent){
        Response response = receiveIntent.getParcelableExtra(Constants.ValidateEmailPage.RESPONSE);
        String command = receiveIntent.getStringExtra(Constants.ValidateEmailPage.COMMAND);
        Log.v(LOG, "Receive: " + command + " command");
        if(command.equals(Constants.ValidateEmailPage.CSV_COMMAND)){
            if(response.getStatus() == 1){
                Intent intent = new Intent(getActivity(), MainPage.class);
                startActivity(intent);
            }
            else{
                Utils.showErrorDialog("Failed", response.getMessage(), getActivity());
            }
        }
        else if(command.equals(Constants.ValidateEmailPage.RESEND_EMAIL_COMMAND)){

            btnVEResend.setText(R.string.ve_resend);
            btnVEResend.setClickable(true);

            if(response.getStatus() == 1) {
                Log.v(LOG, "Resend email command received");

                Toast.makeText(getActivity(), "Email has been sent to your email", Toast.LENGTH_LONG);

                tvAnnouncement.setText("Email sent!");
            }
            else {
                Utils.showErrorDialog("Failed", response.getMessage(), getActivity());
            }
        }
        else{
            Utils.showErrorDialog("Error", "Invalid Command", getActivity());

        }
    }


    private void initializeButtonListeners(){
        btnVEResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendEmail();
            }
        });

        btnVEDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateEmailServiceHelper helper = ValidateEmailServiceHelper.getInstance(getActivity());
                helper.checkStatusValidationEmail(email);
            }
        });

        btnVEValLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainPage.class);
                startActivity(intent);
            }
        });
    }

    private void initializeGraphicComponent(View view){
        btnVEDone = (Button) view.findViewById(R.id.btnVEDone);
        btnVEResend = (Button) view.findViewById(R.id.btnVEResend);
        btnVEValLater = (Button) view.findViewById(R.id.btnVEValLater);
        tvAnnouncement = (TextView) view.findViewById(R.id.tvAnnouncement);
    }

    private void resendEmail(){


        //set the text of button to sending, to indicate the email is sending
        btnVEResend.setText(R.string.ve_sending);
        btnVEResend.setClickable(false);
        tvAnnouncement.setText("We are sending verification link to your email, please wait!");
        //start a helper to start a service
        ValidateEmailServiceHelper helper = ValidateEmailServiceHelper.getInstance(getActivity());
        helper.resendEmail(email);
    }
}
