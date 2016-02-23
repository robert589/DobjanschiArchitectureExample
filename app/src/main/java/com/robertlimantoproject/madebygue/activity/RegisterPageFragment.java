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
import com.robertlimantoproject.madebygue.entity.Response;
import com.robertlimantoproject.madebygue.entity.User;
import com.robertlimantoproject.madebygue.R;
import com.robertlimantoproject.madebygue.Utils;
import com.robertlimantoproject.madebygue.service.RegisterService;
import com.robertlimantoproject.madebygue.service_helper.RegisterServiceHelper;

/**
 * A placeholder fragment containing a simple view.
 */
public class RegisterPageFragment extends Fragment {

    private final static String LOG = RegisterPageFragment.class.getSimpleName();

    private Button btnRegRegister;

    private EditText etRegName, etRegHandphone, etRegAddress, etRegEmail, etRegPassword, etRegCfmPswd;

    private TextView tvRegEmailErr, tvRegNameErr, tvRegPswdErr, tvRegCfmErr;

    private RegisterService mService;

    private ProgressDialog pdRegisterWait;

    //NOT GOOD PRACTICE
    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(registerReceiver,
                new IntentFilter(Constants.RegisterPage.RECEIVER));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
                View view = inflater.inflate(R.layout.fragment_register_page, container, false);

            initializeGraphicComponent(view);

            initializeButtonListeners();

            //DELETE THIS
            forTesting();
            return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver((registerReceiver), new IntentFilter(Constants.RegisterPage.RECEIVER));
    }

    @Override
    public void onStop() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(registerReceiver);
        super.onStop();
    }

    private BroadcastReceiver registerReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("KBR", "Got message");
            handleMessage(intent);
        }
    };

    private boolean checkRequiredField(){

        boolean valid  = true;

        String password = etRegPassword.getText().toString();

        String cnfmPassword = etRegCfmPswd.getText().toString();

        String email = etRegEmail.getText().toString();

        if(etRegName.getText().toString().trim().equals("")){
            tvRegNameErr.setText(Constants.RegisterPage.NAME_REQUIRED_FIELD);
            valid =  false;
        }

        if(password.equals("")){
            tvRegPswdErr.setText(Constants.RegisterPage.PASSWORD_REQUIRED_FIELD);
            valid =  false;
        }
        else if(password.length() < 6){
            tvRegPswdErr.setText(Constants.RegisterPage.PASSWORD_LENGTH);
            valid =  false;
        }

        if(cnfmPassword.equals("")){
            tvRegCfmErr.setText(Constants.RegisterPage.CFRM_PSWN_REQ_FIELD);
            valid =  false;
        }else if(!cnfmPassword.equals(password)){
            tvRegCfmErr.setText(Constants.RegisterPage.PSWN_DOES_NOT_MATCH);
            valid =  false;
        }

        if(email.equals("")){
            tvRegEmailErr.setText(Constants.RegisterPage.EMAIL_REQUIRED_FIELD);
            valid =  false;
        }
        else if(!Utils.isEmailValid(email)){
            tvRegEmailErr.setText(Constants.RegisterPage.EMAIL_NOT_VALID);
            valid =  false;
        }


        return valid;
    }

    private void emptyAllErrorText(){
        tvRegCfmErr.setText("");
        tvRegPswdErr.setText("");
        tvRegEmailErr.setText("");
        tvRegNameErr.setText("");
    }

    private void initializeButtonListeners(){
        btnRegRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                emptyAllErrorText();
                boolean valid = checkRequiredField();
                if (valid) {

                    User user = retrieveUserInfo();

                    if (pdRegisterWait == null)
                        pdRegisterWait = ProgressDialog.show(getActivity(), "Registration", "Registration...", true, false);
                    else
                        pdRegisterWait.setMessage("Registration...");

                    RegisterServiceHelper helper = RegisterServiceHelper.getInstance(getActivity());
                    helper.registerUser(user);
                }
                }


        });
    }

    private void initializeGraphicComponent(View v){
        btnRegRegister = (Button) v.findViewById(R.id.btnRegRegister);
        etRegName = (EditText) v.findViewById(R.id.etRegName);
        etRegHandphone = (EditText) v.findViewById(R.id.etRegHP);
        etRegAddress = (EditText) v.findViewById(R.id.etRegAddress);
        etRegEmail = (EditText) v.findViewById(R.id.etRegEmail);
        etRegPassword = (EditText) v.findViewById(R.id.etRegPassword);
        etRegCfmPswd = (EditText) v.findViewById(R.id.etRefCfmPswd);

        tvRegNameErr = (TextView) v.findViewById(R.id.tvRegEmailErr);
        tvRegEmailErr = (TextView) v.findViewById(R.id.tvRegEmailErr);
        tvRegCfmErr = (TextView) v.findViewById(R.id.tvRegCfmErr);
        tvRegPswdErr = (TextView) v.findViewById(R.id.tvRegPswdErr);
    }

    private User retrieveUserInfo(){
        String name = etRegName.getText().toString().trim();
        String password = etRegPassword.getText().toString();
        String address = etRegAddress.getText().toString();
        if(address == null){
            address = "";
        }
        String handphone = etRegHandphone.getText().toString();
        if(handphone == null){
            handphone = "";
        }
        String email = etRegEmail.getText().toString();

        User user = new User(name, handphone, address,email,password);

        return user;
    }



    private void handleMessage(Intent intent){

        Response response = intent.getParcelableExtra(Constants.RegisterPage.RESPONSE);

        if(pdRegisterWait != null)
            pdRegisterWait.dismiss();

        if(response.getStatus() == 1){
            Intent newIntent = new Intent(getActivity(), ValidateEmailPage.class);

            startActivity(newIntent);

        }
        else{
            Utils.showErrorDialog("LOGIN FAILED", "Failure: " + response.getMessage(), getActivity());
        }
    }



    /**Testing**/
    private void forTesting(){
        etRegName.setText("Robert Limanto");
        etRegEmail.setText("r_limanto@hotmail.com");
        etRegPassword.setText("lifeee");
        etRegCfmPswd.setText("lifeee");
    }
}
