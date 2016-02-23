package com.robertlimantoproject.madebygue.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.robertlimantoproject.madebygue.Constants;
import com.robertlimantoproject.madebygue.Utils;
import com.robertlimantoproject.madebygue.entity.Response;
import com.robertlimantoproject.madebygue.entity.User;
import com.robertlimantoproject.madebygue.R;
import com.robertlimantoproject.madebygue.service.LoginService;
import com.robertlimantoproject.madebygue.service_helper.LoginServiceHelper;


/**
 * A placeholder fragment containing a simple view.
 */
public class LoginPageFragment extends Fragment {

    private final static  String LOG = LoginPageFragment.class.getSimpleName();

    private Button btnForgotPassword, btnRegister, btnLogin;

    private EditText etLgnEmail, etLgnPassword;

    private LoginService mService;

    private TextView tvEmailErrMsg, tvPswdErrMsg;

    private ProgressDialog pdLoginWait;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver((loginReceiver), new IntentFilter(Constants.LoginPage.RECEIVER));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_login_page, container, false);

        //Initialize all graphic components
        initializeGraphicComponent(view);

        //Initialize button listeners
        initializeButtonListeners();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver((loginReceiver), new IntentFilter(Constants.LoginPage.RECEIVER));
    }

    @Override
    public void onStop() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(loginReceiver);
        super.onStop();
    }

    private BroadcastReceiver loginReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(LOG, "Got message");
            handleMessage(intent);
        }
    };

    public void initializeButtonListeners(){

        //Button register listener
        btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), RegisterPage.class);
                startActivity(intent);
            }
        });

        //initialize button login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean valid = checkRequiredField();

                if (valid) {
                    User user = retrieveUserInfo();
                    LoginServiceHelper helper = LoginServiceHelper.getInstance(getActivity());
                    helper.loginUser(user);
                }

            }
        });
    }

    private boolean checkRequiredField(){

        boolean valid  = true;
        if(etLgnEmail.getText().toString().trim().equals("")){
            tvEmailErrMsg.setText(Constants.LoginPage.EMAIL_REQUIRED_FIELD);
            return false;

        }
        if(etLgnPassword.getText().toString().trim().equals("")){
            tvPswdErrMsg.setText(Constants.LoginPage.PASSWORD_REQUIRED_FIELD);
            return false;
        }

        return valid;
    }

    private User retrieveUserInfo(){
        String email = etLgnEmail.getText().toString();

        String password = etLgnPassword.getText().toString();

        User user = new User(email, password);

        return user;
    }

    private void initializeGraphicComponent(View view){
        btnForgotPassword = (Button) view.findViewById(R.id.btnForgotPassword);
        btnRegister = (Button) view.findViewById(R.id.btnRegister);
        btnLogin = (Button) view.findViewById(R.id.btnLogin);
        etLgnEmail = (EditText) view.findViewById(R.id.etLgnEmail);
        etLgnPassword = (EditText) view.findViewById(R.id.etLgnPassword);
        tvEmailErrMsg = (TextView) view.findViewById(R.id.tvEmailErrMsg);
        tvPswdErrMsg = (TextView) view.findViewById(R.id.tvPswdErrMsg);
    }



    private void handleMessage(Intent intent){
        Response response = intent.getParcelableExtra(Constants.LoginPage.RESPONSE);

        if (pdLoginWait != null)
            pdLoginWait.dismiss();

        if (response.getStatus() == 1) {
            Intent newIntent = new Intent(getActivity(), MainPage.class);
            startActivity(newIntent);
        } else {
            Utils.showErrorDialog("LOGIN FAILED", "Failure: " + response.getMessage(), getActivity());
        }
    }


}
