package com.robertlimantoproject.madebygue.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.robertlimantoproject.madebygue.Constants;
import com.robertlimantoproject.madebygue.entity.User;
import com.robertlimantoproject.madebygue.R;
import com.robertlimantoproject.madebygue.preference.UserPreference;

/**
 * A placeholder fragment containing a simple view.
 */
public class IdGuePageFragment extends Fragment {

    private TextView tvIGName, tvIGEmail, tvIGHandphone, tvIGAddress, tvIGValidate;


    private Button btnIGChange, btnIGValidate, btnIGFreeTShirt, btnIGDeposit, btnIGDistro;


    public IdGuePageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_id_gue_page, container, false);

        initializeGraphicComponent(view);

        initializeButtonListeners();

        displayProfileTextView();
        return view;
    }

    private void initializeGraphicComponent(View view){

        btnIGChange = (Button) view.findViewById(R.id.btnIGChange);
        btnIGValidate = (Button) view.findViewById(R.id.btnIGValidate);
        btnIGFreeTShirt = (Button) view.findViewById(R.id.btnIGFreeTShirt);
        btnIGDeposit = (Button) view.findViewById(R.id.btnIGDeposit);
        btnIGDistro = (Button) view.findViewById(R.id.btnIGDistro);

        tvIGName = (TextView) view.findViewById(R.id.tvIGName);
        tvIGEmail = (TextView) view.findViewById(R.id.tvIGEmail);
        tvIGAddress = (TextView) view.findViewById(R.id.tvIGAddress);
        tvIGHandphone = (TextView) view.findViewById(R.id.tvIGPhone);
        tvIGValidate = (TextView) view.findViewById(R.id.tvValidated);
    }

    private void initializeButtonListeners(){
        btnIGChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfilePage.class);
                startActivity(intent);
            }
        });

        btnIGDeposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnIGDistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getActivity(), DistroGuePage.class);
                startActivity(intent);
            }
        });

        btnIGFreeTShirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnIGValidate.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), ValidateEmailPage.class);
                startActivity(intent);
            }
        });
    }

    private void displayProfileTextView(){
        User user = UserPreference.getUser(getActivity());

        //Name
        tvIGName.setText(user.getName());

        //Address
        if(user.getAddress() == null || user.getAddress().trim().equals("")){
            tvIGAddress.setText(Constants.IDGuePage.ADD_YOUR_ADDRESS);
        }
        else {
            tvIGAddress.setText(user.getAddress());
        }

        //Email
        tvIGEmail.setText(user.getEmail());

        //Handphone
        if(user.getHandphone() == null || user.getHandphone().trim().equals("")){
            tvIGHandphone.setText(Constants.IDGuePage.ADD_YOUR_HANDPHONE);
        }
        tvIGHandphone.setText(user.getHandphone());

        //Validity
        //if not validated, show button
        if(user.isValidated()){
            tvIGValidate.setText(Constants.IDGuePage.IS_VALIDATED);
            btnIGValidate.setVisibility(View.GONE);
        }
        else{
            tvIGValidate.setText(Constants.IDGuePage.IS_NOT_VALIDATED);
            btnIGValidate.setVisibility(View.VISIBLE);
        }
    }
}
