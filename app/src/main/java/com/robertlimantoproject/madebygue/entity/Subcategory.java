package com.robertlimantoproject.madebygue.entity;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.robertlimantoproject.madebygue.Constants;
import com.robertlimantoproject.madebygue.activity.CreatePage;
import com.robertlimantoproject.madebygue.activity.CreateProductPage;

/**
 * Created by user on 11/8/2015.
 */
public class Subcategory implements Parcelable {

    private int category_id;

    private int subcategory_id;

    private String subcategory_name;

    public Subcategory(int category_id, int subcategory_id, String subcategory_name) {
        this.category_id = category_id;
        this.subcategory_id = subcategory_id;
        this.subcategory_name = subcategory_name;
    }

    /**
     * Parcelable implementation
     */

    public void writeToParcel(Parcel out, int flags){
        out.writeInt(category_id);
        out.writeString(subcategory_name);
        out.writeInt(subcategory_id);



    }

    public int describeContents(){
        return 0;
    }

    private Subcategory(Parcel in){
        this.category_id = in.readInt();
        this.subcategory_name = in.readString();

        this.subcategory_id = in.readInt();

    }

    public static final Parcelable.Creator<Subcategory> CREATOR = new Parcelable.Creator<Subcategory>(){
        public Subcategory createFromParcel(Parcel in){ return new Subcategory(in);}

        public Subcategory[] newArray(int size){
            return new Subcategory[size];
        }
    };


    /**
     * GETTER AND SETTER
     */

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getSubcategory_id() {
        return subcategory_id;
    }

    public void setSubcategory_id(int subcategory_id) {
        this.subcategory_id = subcategory_id;
    }

    public String getSubcategory_name() {
        return subcategory_name;
    }

    public void setSubcategory_name(String subcategory_name) {
        this.subcategory_name = subcategory_name;
    }

    /**
     * CREATE LAYOUT
     */

    public View getView(final Activity activity){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight = 1;
        Button button = new Button(activity);

        button.setText(subcategory_name);

        button.setLayoutParams(params);

        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(activity, CreateProductPage.class);
                intent.putExtra(Constants.CreateProductPage.SUBCATEGORY_ID, subcategory_id);
                activity.startActivity(intent);
            }
        });

        return button;
    }
}
