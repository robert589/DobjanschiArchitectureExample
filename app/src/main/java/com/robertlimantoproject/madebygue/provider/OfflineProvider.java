package com.robertlimantoproject.madebygue.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.CancellationSignal;
import android.util.Log;

import com.robertlimantoproject.madebygue.database.OfflineDatabase;

/**
 * Created by user on 10/8/2015.
 */
public class OfflineProvider extends ContentProvider {

    public static final int CATEGORY_LIST = 1;

    public static final int CATEGORY_ID = 2;

    public static final int PRODUCT_LIST = 3;

    public static final int PRODUCT_ID = 4;

    public static final int SUBCATEGORY_LIST = 5;

    public  static final int SUBCATEGORY_ID = 6;

    private static final UriMatcher URI_MATCHER;

    private OfflineDatabase helper;

    //prepare URI_MATCHER
    static{
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

        URI_MATCHER.addURI(OfflineContract.AUTHORITY, OfflineContract.CATEGORY.PATH, CATEGORY_LIST);
        URI_MATCHER.addURI(OfflineContract.AUTHORITY, OfflineContract.CATEGORY.PATH + "/*", CATEGORY_ID);
        URI_MATCHER.addURI(OfflineContract.AUTHORITY, OfflineContract.PRODUCT.PATH, PRODUCT_LIST);
        URI_MATCHER.addURI(OfflineContract.AUTHORITY, OfflineContract.PRODUCT.PATH + "/#", PRODUCT_ID);
        URI_MATCHER.addURI(OfflineContract.AUTHORITY, OfflineContract.SUBCATEGORY.PATH , SUBCATEGORY_LIST);
        URI_MATCHER.addURI(OfflineContract.AUTHORITY, OfflineContract.SUBCATEGORY.PATH + "/#", SUBCATEGORY_ID);
    }

    public boolean onCreate(){
        helper = new OfflineDatabase(getContext());
        return true;

    }

    @Override
    public String getType(Uri uri) {
       switch(URI_MATCHER.match(uri)){
           case CATEGORY_LIST:
               return   OfflineContract.CATEGORY.CONTENT_TYPE;
           case CATEGORY_ID:
               return OfflineContract.CATEGORY.CONTENT_ITEM_TYPE;
           default:
               throw new IllegalArgumentException("Unsupported URI: " + uri);
       }
    }


    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = helper.getWritableDatabase();
        Uri returnUri;
        long _id;
        switch(URI_MATCHER.match(uri)){
            case PRODUCT_LIST:
                _id = db.insert(OfflineDatabase.ProductTable.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = ContentUris.withAppendedId(uri,_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;

            case CATEGORY_LIST:
                Log.d("PROVIDER", "Querying category");
                db.beginTransaction();
                _id = db.insert(OfflineDatabase.CategoryTable.TABLE_NAME, null, values);
                if ( _id > 0 ) {
                    returnUri = ContentUris.withAppendedId(uri, _id);
                    db.setTransactionSuccessful();
                }
                else {
                    db.endTransaction();
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                db.endTransaction();
                break;

            case PRODUCT_ID:
                _id = db.insert(OfflineDatabase.ProductTable.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = ContentUris.withAppendedId(uri,_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
        }
        return null;
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = helper.getWritableDatabase();

        int _id = -1;
        switch(URI_MATCHER.match(uri)){
            case CATEGORY_LIST:
                _id = db.delete(OfflineDatabase.CategoryTable.TABLE_NAME, null, null);

                break;

            case SUBCATEGORY_LIST:
                _id = db.delete(OfflineDatabase.CategoryTable.TABLE_NAME, null, null);

                break;

            case PRODUCT_LIST:
                _id = db.delete(OfflineDatabase.ProductTable.TABLE_NAME, null, null);

                break;
        }
        return _id;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db = helper.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        Cursor retCursor;

        switch (URI_MATCHER.match(uri)){
            case CATEGORY_ID:
                retCursor = db.query(OfflineDatabase.CategoryTable.TABLE_NAME,
                        projection,
                        OfflineContract.CATEGORY._ID + " = '" + ContentUris.parseId(uri) + "'",
                        null,
                        null,
                        null,
                        sortOrder
                        );
                break;

            case CATEGORY_LIST:
                retCursor = db.query(OfflineDatabase.CategoryTable.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            case PRODUCT_LIST:
                retCursor = db.query(OfflineDatabase.ProductTable.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            case SUBCATEGORY_LIST:
                retCursor = db.query(OfflineDatabase.SubCategoryTable.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);



        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }




}
