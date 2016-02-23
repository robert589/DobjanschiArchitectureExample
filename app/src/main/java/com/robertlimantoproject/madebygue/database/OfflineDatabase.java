package com.robertlimantoproject.madebygue.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.facebook.FacebookRequestError;

/**
 * Created by user on 10/8/2015.
 */
public class OfflineDatabase extends SQLiteOpenHelper {

     public class CategoryTable{

        public final static String TABLE_NAME = "category";

        public final static String CATEGORY_ID = "category_id";

        public final static String CATEGORY_NAME = "category_name";

        public final static String CATEGORY_IMAGE = "category_image";

        public final static String CREATE_TABLE_CATEGORIES = "Create table "  + TABLE_NAME +
                "(" + CATEGORY_ID + " integer primary key not null," +
                CATEGORY_NAME + " text not null," +
                CATEGORY_IMAGE + " text null);";
    }

    public class SubCategoryTable{

        public final static String TABLE_NAME = "sub_category";

        public final static String SUBCATEGORY_ID    = "subcategory_id";

        public final static String CATEGORY_ID = "category_id";

        public final static String SUBCATEGORY_NAME = "subcategory_name";

        public final static String CREATE_TABLE = "Create table "  + TABLE_NAME +
                "(" + SUBCATEGORY_ID + " integer  not null ," +
                CATEGORY_ID + " integer  not null," +
                SUBCATEGORY_NAME + " text not null, " +
                "primary key (" + SUBCATEGORY_ID + "," + SUBCATEGORY_NAME+ ") );";
    }


    public    class ProductTable{

        public final static String TABLE_NAME = "product";

        public final static String PRODUCT_ID = "product_id";

        public final static String PRODUCT_DESC  = "product_desc";

        public final static String PRICE =  "price";

        public final static String CREATE_DURATION = "creating_duration";

        public final static String SUBCATEGORY_ID = "subcategory_id";

        public final static String CREATE_TABLE = "Create table "  + TABLE_NAME +
                "(" + PRODUCT_ID + " integer primary key not null ," +
                PRODUCT_DESC + " text not null," +
                PRICE + " integer not null," +
                CREATE_DURATION + " text not null," +
                SUBCATEGORY_ID + " integer not null)";
    }

    private static final String LOG = OfflineDatabase.class.getSimpleName();

    private static final int DB_VERSION = 3;

    private static final String DB_NAME = "Madebygue";

    public OfflineDatabase(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }


    public void onCreate(SQLiteDatabase db){
        db.execSQL(CategoryTable.CREATE_TABLE_CATEGORIES);
        db.execSQL(SubCategoryTable.CREATE_TABLE);

        db.execSQL(ProductTable.CREATE_TABLE);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.w(LOG, "Upgrading database. Existing contents will be lost. ["
                + oldVersion + "]->[" + newVersion + "]");
        db.execSQL("DROP TABLE IF EXISTS " + CategoryTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SubCategoryTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ProductTable.TABLE_NAME);
        onCreate(db);
    }
}
