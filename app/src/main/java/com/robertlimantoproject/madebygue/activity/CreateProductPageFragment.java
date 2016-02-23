package com.robertlimantoproject.madebygue.activity;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.robertlimantoproject.madebygue.Constants;
import com.robertlimantoproject.madebygue.R;
import com.robertlimantoproject.madebygue.database.OfflineDatabase;
import com.robertlimantoproject.madebygue.entity.Product;
import com.robertlimantoproject.madebygue.entity.Subcategory;
import com.robertlimantoproject.madebygue.provider.OfflineContract;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class CreateProductPageFragment extends Fragment {

    private String LOG = CreateProductPageFragment.class.getSimpleName();

    private LinearLayout LLCPP;

    private ImageButton ibCPP;

    private int subcategory_id;

    public CreateProductPageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subcategory_id = getActivity().getIntent().getIntExtra(Constants.CreateProductPage.SUBCATEGORY_ID, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_product_page, container, false);

        initializeGraphicComponents(view);

        initializeButtonListeners();

        retrieveComponents();
        return view;
    }

    private void initializeButtonListeners(){
        ibCPP.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){


            }
        });
    }

    private void initializeGraphicComponents(View view){
        ibCPP = (ImageButton) view.findViewById(R.id.ibCPP);

        LLCPP = (LinearLayout) view.findViewById(R.id.LLCPP);
    }



    private void retrieveComponents(){
        Cursor cursor =  getActivity().getContentResolver().query(OfflineContract.PRODUCT.CONTENT_URI,
                OfflineContract.PRODUCT.PROJECTION_ALL,
                null,
                null,
                OfflineContract.PRODUCT.PRODUCT_DESC
        );


        ArrayList<Product> productList = new ArrayList<Product>();

        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    int product_id = cursor.getInt(cursor.getColumnIndex(OfflineDatabase.ProductTable.PRODUCT_ID));
                    String product_desc = cursor.getString(cursor.getColumnIndex(OfflineDatabase.ProductTable.PRODUCT_DESC));
                    int subcategory_id = cursor.getInt(cursor.getColumnIndex(OfflineDatabase.ProductTable.SUBCATEGORY_ID));
                    int price = cursor.getInt(cursor.getColumnIndex(OfflineDatabase.ProductTable.PRICE));
                    String creating_duration  =cursor.getString(cursor.getColumnIndex(OfflineDatabase.ProductTable.CREATE_DURATION));

                    productList.add(new Product(product_id, product_desc, subcategory_id,price, creating_duration));
                }
                while(cursor.moveToNext());
            }
        }
        Log.d(LOG, "Size" + productList.size());

    }

    private void setButtonLayout(ArrayList<Product> prodList){
        for(Product sub : prodList){
            Log.d(LOG, "igl");
            if(subcategory_id == sub.getSubcategory_id()) {
                Log.d(LOG, "something is added");
                Button button = (Button) sub.getView(getActivity());
                LLCPP.addView(button);
            }
        }
    }
}
