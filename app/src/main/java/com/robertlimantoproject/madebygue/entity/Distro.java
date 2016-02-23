package com.robertlimantoproject.madebygue.entity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.robertlimantoproject.madebygue.R;

/**
 * Created by user on 8/8/2015.
 */
public class Distro implements Parcelable {

    private Context context;

    private int distro_id;

    private String product_desc;

    private int priceInIDR;

    private String createDuration;

    private String user_name;

    private Bitmap distro_pic;

    private int liked;

    private int sold;

    /**Parcelable implementation**/
    public void writeToParcel(Parcel out, int flags){
        out.writeInt(distro_id);
        out.writeString(product_desc);
        out.writeInt(priceInIDR);
        out.writeString(createDuration);
        out.writeString(user_name);
        out.writeParcelable(distro_pic, flags);
        out.writeInt(liked);
        out.writeInt(sold);


    }

    public int describeContents(){
        return 0;
    }

    private Distro(Parcel in){
        this.distro_id = in.readInt();
        this.product_desc = in.readString();
        this.priceInIDR = in.readInt();
        this.createDuration = in.readString();
        this.user_name = in.readString();
        this.distro_pic = in.readParcelable(Parcelable.class.getClassLoader());
        this.liked = in.readInt();
        this.sold =  in.readInt();
    }

    public static final Parcelable.Creator<Distro> CREATOR = new Parcelable.Creator<Distro>(){
        public Distro createFromParcel(Parcel in){ return new Distro(in);}

        public Distro[] newArray(int size){
            return new Distro[size];
        }
    };


    /**GETTER AND SETTER**/
    public int getDistro_id() {
        return distro_id;
    }

    public void setDistro_id(int distro_id) {
        this.distro_id = distro_id;
    }

    public String getProduct_desc() {
        return product_desc;
    }

    public void setProduct_desc(String product_desc) {
        this.product_desc = product_desc;
    }

    public int getPriceInIDR() {
        return priceInIDR;
    }

    public void setPriceInIDR(int priceInIDR) {
        this.priceInIDR = priceInIDR;
    }

    public String getCreateDuration() {
        return createDuration;
    }

    public void setCreateDuration(String createDuration) {
        this.createDuration = createDuration;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Bitmap getDistro_pic() {
        return distro_pic;
    }

    public void setDistro_pic(Bitmap distro_pic) {
        this.distro_pic = distro_pic;
    }

    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public View getView(Activity activity){
        View view = activity.getLayoutInflater().inflate(R.layout.distro_card_layout, null);

        TextView tvDCLiked = (TextView) view.findViewById(R.id.tvDCLiked);
        TextView tvDCSold = (TextView) view.findViewById(R.id.tvDCSold);
        TextView tvDCFullname = (TextView) view.findViewById(R.id.tvDCUserFullname);
        ImageView ivProfilePic = (ImageView) view.findViewById(R.id.ivDCClothPic);

        tvDCLiked.setText("Like: " + liked);
        tvDCSold.setText("Sold: "  + sold);
        tvDCFullname.setText(user_name);

        return view;
    }
}
