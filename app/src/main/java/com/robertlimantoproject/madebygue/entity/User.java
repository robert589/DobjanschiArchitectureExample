package com.robertlimantoproject.madebygue.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

/**
 * Created by user on 23/7/2015.
 */
public class User implements Parcelable {

    private String email;

    private String password = "";

    private String name = "";

    private String address = "";

    private String handphone = "";

    private int follower = 0;

    private int following = 0;

    private int rating = 0;

    private boolean validated = false;

    private ImageView profile_pic;

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

    //Constructor for edit profile
    public User(String name, String handphone, String address, String email){
        this.name  = name;
        this.address = address;
        this.handphone = handphone;
        this.email = email;
    }

    public User(String name, String handphone, String address, String email, String password){
        this.email = email;
        this.password = password;
        this.name  = name;
        this.address = address;
        this.handphone = handphone;
    }

    public User(String name, String handphone, String address, String email, String password, boolean validated){
        this.email = email;
        this.password = password;
        this.name  = name;
        this.address = address;
        this.handphone = handphone;
        this.validated = validated;
    }

    public User(String name, String handphone, String address, String email, String password, boolean validated, int follower, int following, int rating){
        this.email = email;
        this.password = password;
        this.name  = name;
        this.address = address;
        this.handphone = handphone;
        this.validated = validated;
        this.follower = follower;
        this.following = following;
        this.rating = rating;
    }

    public User(int follower, int following, int rating){
        this.follower = follower;
        this.following = following;
        this.rating =rating;
    }
    /*
    //    PARCELABLE IMPLEMENTATION
    */
    public void writeToParcel(Parcel out, int flags){
        out.writeString(email);
        out.writeString(password);
        out.writeString(name);
        out.writeString(address);
        out.writeString(handphone);
        out.writeByte((byte) (validated ? 1 : 0));
        out.writeInt(follower);
        out.writeInt(following);
        out.writeInt(rating);


    }

    public int describeContents(){
        return 0;
    }

    private User(Parcel in){
        this.email = in.readString();
        this.password = in.readString();
        this.name = in.readString();
        this.address = in.readString();
        this.handphone = in.readString();
        this.validated = (in.readByte() != 0);
        this.follower = in.readInt();
        this.following =  in.readInt();
        this.rating = in.readInt();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>(){
        public User createFromParcel(Parcel in){ return new User(in);}

        public User[] newArray(int size){
            return new User[size];
        }
    };

    /*
     *GETTER AND SETTER
     */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHandphone() {
        return handphone;
    }

    public void setHandphone(String handphone) {
        this.handphone = handphone;
    }

    public boolean isValidated() {
        return validated;
    }

    public ImageView getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(ImageView profile_pic) {
        this.profile_pic = profile_pic;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public int getFollower() {
        return follower;
    }

    public void setFollower(int follower) {
        this.follower = follower;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
