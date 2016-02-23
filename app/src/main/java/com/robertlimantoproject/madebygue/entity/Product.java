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
import com.robertlimantoproject.madebygue.activity.SubcategoryCreatePage;

/**
 * Created by user on 11/8/2015.
 */
public class Product implements  Parcelable {

    public int product_id;

    public String product_desc;

    public int subcategory_id;

    public int price;

    public String creating_duration;


    public Product(int product_id, String product_desc, int subcategory_id, int price, String creating_duration) {
        this.product_id = product_id;
        this.product_desc = product_desc;
        this.subcategory_id = subcategory_id;
        this.price = price;
        this.creating_duration = creating_duration;
    }

    /**
     * Parcelable implementation
     */

    public void writeToParcel(Parcel out, int flags){
        out.writeInt(product_id);
        out.writeString(product_desc);
        out.writeInt(subcategory_id);
        out.writeInt(price);
        out.writeString(creating_duration);



    }

    public int describeContents(){
        return 0;
    }

    private Product(Parcel in){
        this.product_id = in.readInt();
        this.product_desc = in.readString();
           this.subcategory_id = in.readInt();
        this.price = in.readInt();
        this.creating_duration = in.readString();
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>(){
        public Product createFromParcel(Parcel in){ return new Product(in);}

        public Product[] newArray(int size){
            return new Product[size];
        }
    };

    /**
     * GETTER AND SETTER
     */
    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_desc() {
        return product_desc;
    }

    public void setProduct_desc(String product_desc) {
        this.product_desc = product_desc;
    }

    public int getSubcategory_id() {
        return subcategory_id;
    }

    public void setSubcategory_id(int subcategory_id) {
        this.subcategory_id = subcategory_id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCreating_duration() {
        return creating_duration;
    }

    public void setCreating_duration(String creating_duration) {
        this.creating_duration = creating_duration;
    }

    /**
     * Layout
     */
    public View getView(final Activity activity){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight = 1;
        Button button = new Button(activity);

        button.setText(product_desc);

        button.setLayoutParams(params);

        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

            }
        });

        return button;
    }
}
