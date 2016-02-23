package com.robertlimantoproject.madebygue.entity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.robertlimantoproject.madebygue.Constants;
import com.robertlimantoproject.madebygue.R;
import com.robertlimantoproject.madebygue.activity.CreatePage;
import com.robertlimantoproject.madebygue.activity.SubcategoryCreatePage;
import com.robertlimantoproject.madebygue.provider.OfflineContract;

/**
 * Created by user on 10/8/2015.
 */
public class Category implements  Parcelable {

    private int category_id;

    private String category_name;

    private Bitmap category_image = null;

    public Category(int id, String name){
        this.category_id = id;
        this.category_name = name;
    }

    public Category(int id, String name, Bitmap category_image){
        this.category_id = id;
        this.category_name = name;
        this.category_image = category_image;
    }

    /**
     * Parcelable implementation
     */
    public void writeToParcel(Parcel out, int flags){
        out.writeInt(category_id);
        out.writeString(category_name);
        out.writeParcelable(category_image, flags);



    }

    public int describeContents(){
        return 0;
    }

    private Category(Parcel in){
        this.category_id = in.readInt();
        this.category_name = in.readString();

        this.category_image = in.readParcelable(Parcelable.class.getClassLoader());

    }

    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>(){
        public Category createFromParcel(Parcel in){ return new Category(in);}

        public Category[] newArray(int size){
            return new Category[size];
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

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public Bitmap getCategory_image() {
        return category_image;
    }

    public void setCategory_image(Bitmap category_image) {
        this.category_image = category_image;
    }

    /**
     * CREATE LAYOUT
     */

    public View getView(final Activity activity){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight = 1;
        Button button = new Button(activity);

        button.setText(category_name);

        button.setLayoutParams(params);

        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(activity, SubcategoryCreatePage.class);
                intent.putExtra(Constants.SubcategoryCreatePage.CATEGORY_ID, category_id);
                activity.startActivity(intent);
            }
        });

        return button;
    }
}
