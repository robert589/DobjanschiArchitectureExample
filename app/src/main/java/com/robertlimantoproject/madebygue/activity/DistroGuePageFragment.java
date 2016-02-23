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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.robertlimantoproject.madebygue.Constants;
import com.robertlimantoproject.madebygue.R;
import com.robertlimantoproject.madebygue.entity.Response;
import com.robertlimantoproject.madebygue.entity.User;
import com.robertlimantoproject.madebygue.preference.UserPreference;
import com.robertlimantoproject.madebygue.service_helper.DistroGueServiceHelper;
import com.robertlimantoproject.madebygue.service_helper.MainPageServiceHelper;

/**
 * A placeholder fragment containing a simple view.
 */
public class DistroGuePageFragment extends Fragment {

    private ImageView ivProfilePic;

    private TextView tvDGNumFollower, tvDGNUMFollowing;

    private RatingBar rbDG;

    private LinearLayout LLDG;


    public DistroGuePageFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //update user profile to preference
        String email = UserPreference.getUser(getActivity()).getEmail();
        retrieveUserRelation(email);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver((distroGueReceiver), new IntentFilter(Constants.DistroGuePage.RECEIVER));


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_distro_gue_page, container, false);

        initializeGraphicComponents(view);

        initializeGraphicValue();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver((distroGueReceiver), new IntentFilter(Constants.DistroGuePage.RECEIVER));
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStop() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(distroGueReceiver);
        super.onStop();
    }

    private BroadcastReceiver distroGueReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("KBR", "Got message");
            handleMessage(intent);
        }
    };
    private void initializeGraphicComponents(View view){
        //initialize Image View
        ivProfilePic = (ImageView) view.findViewById(R.id.ivProfilePic);

        //initialize Textview
        tvDGNumFollower = (TextView) view.findViewById(R.id.tvDGNumFollowers);
        tvDGNUMFollowing = (TextView) view.findViewById(R.id.tvDGNumFollowings);

        //initialize rating bar
        rbDG = (RatingBar) view.findViewById(R.id.rbDG);

        //INITIalize linearlayout
        LLDG = (LinearLayout) view.findViewById(R.id.LLDG);
    }

    private void initializeGraphicValue(){
        User user = UserPreference.getUser(getActivity());

        int follower = user.getFollower();
        int following = user.getFollowing();
        float rating = rbDG.getRating();


        tvDGNumFollower.setText("" + follower);

        tvDGNUMFollowing.setText("" + following);

        rbDG.setRating(rating);

    }

    private void retrieveUserRelation(String email){
        DistroGueServiceHelper helper = DistroGueServiceHelper.getInstance(getActivity());
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
