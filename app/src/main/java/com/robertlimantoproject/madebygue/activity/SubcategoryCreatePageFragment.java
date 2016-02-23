package com.robertlimantoproject.madebygue.activity;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.robertlimantoproject.madebygue.Constants;
import com.robertlimantoproject.madebygue.R;
import com.robertlimantoproject.madebygue.database.OfflineDatabase;
import com.robertlimantoproject.madebygue.entity.Category;
import com.robertlimantoproject.madebygue.entity.Subcategory;
import com.robertlimantoproject.madebygue.provider.OfflineContract;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class SubcategoryCreatePageFragment extends Fragment {

    private int category_id;

    private LinearLayout LLSCP;

    public SubcategoryCreatePageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        category_id = getActivity().getIntent().getIntExtra(Constants.SubcategoryCreatePage.CATEGORY_ID, 0);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_subcategory_create_page, container, false);

        initializeGraphicComponents(view);

       //initializeButtonListeners();


        retrieveComponents();


        return view;
    }

    private void initializeGraphicComponents(View view){
        LLSCP = (LinearLayout) view.findViewById(R.id.LLSCP);
    }

    private void retrieveComponents(){
        Cursor cursor =  getActivity().getContentResolver().query(OfflineContract.SUBCATEGORY.CONTENT_URI,
                OfflineContract.SUBCATEGORY.PROJECTION_ALL,
                null,
                null,
                OfflineContract.SUBCATEGORY.SUBCATEGORY_NAME
        );


        ArrayList<Subcategory> catList = new ArrayList<Subcategory>();
        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    int category_id = cursor.getInt(cursor.getColumnIndex(OfflineDatabase.SubCategoryTable.CATEGORY_ID));
                    String subcategory_name = cursor.getString(cursor.getColumnIndex(OfflineDatabase.SubCategoryTable.SUBCATEGORY_NAME));
                    int subcategory_id = cursor.getInt(cursor.getColumnIndex(OfflineDatabase.SubCategoryTable.SUBCATEGORY_ID));
                    catList.add(new Subcategory(category_id,subcategory_id,subcategory_name));
                }
                while(cursor.moveToNext());
            }
        }
        Log.d("halo", "" + catList.size());
        setButtonLayout(catList);
    }

    private void setButtonLayout(ArrayList<Subcategory> subList){
        for(Subcategory sub : subList){
            Log.d("halo", "igl");
            if(category_id == sub.getCategory_id()) {
                Log.d("halo", "test");
                Button button = (Button) sub.getView(getActivity());
                LLSCP.addView(button);
            }
        }
    }
}
