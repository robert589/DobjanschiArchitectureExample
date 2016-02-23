package com.robertlimantoproject.madebygue.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.robertlimantoproject.madebygue.entity.User;

/**
 * Created by user on 31/7/2015.
 */
public class UserPreference  {

    private static UserPreference instance;

    private final static String SHARED_PREFERENCE = "sharedPref";

    private final static String EMAIL = "email";

    private final static String PASSWORD = "password";

    private final static String NAME = "name";

    private final static String ADDRESS = "address";

    private final static String HANDPHONE = "handphone";

    private final static String RATING = "rating";

    private final static String FOLLOWER = "follower";

    private final static String FOLLOWING = "following";

    /**GETTER AND SETTER FOR THE ATTRIBUTE**/
    public static void setRating(Context context, int rating){
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt(RATING, rating);
        editor.commit();

    }

    public static void setFollower(Context context, int follower){
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt(FOLLOWER, follower);
        editor.commit();

    }

    public static void setFollowing(Context context, int following){
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt(FOLLOWING, following);
        editor.commit();

    }


    public static void setUser(Context context, User user){
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(NAME, user.getName());
        editor.putString(ADDRESS, user.getAddress());
        editor.putString(EMAIL, user.getEmail());
        editor.putString(PASSWORD, user.getPassword());
        editor.putString(HANDPHONE, user.getHandphone());


        editor.commit();
    }


    public static void setUserWithRelation(Context context, User user){
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(NAME, user.getName());
        editor.putString(ADDRESS, user.getAddress());
        editor.putString(EMAIL, user.getEmail());
        editor.putString(PASSWORD, user.getPassword());
        editor.putString(HANDPHONE, user.getHandphone());
        editor.putInt(RATING, user.getRating());
        editor.putInt(FOLLOWER, user.getFollower());
        editor.putInt(FOLLOWING, user.getFollowing());

        editor.commit();
    }

    public static User getUser(Context context){

        SharedPreferences pref = context.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);

        String name =pref.getString(NAME, null);

        String password =pref.getString(PASSWORD, null);

        String email =pref.getString(EMAIL, null);

        String handphone =pref.getString(HANDPHONE, null);

        String address =pref.getString(ADDRESS, null);

        return new User(name, handphone, address, email, password);
    }


    public static boolean isNull(Context context){
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);

        return (preferences == null);

    }

    public static void setNull(Context context){
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(SHARED_PREFERENCE);
        editor.commit();
    }
}
