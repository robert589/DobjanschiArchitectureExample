package com.robertlimantoproject.madebygue.provider;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by user on 10/8/2015.
 */
public class OfflineContract {

    public static final String AUTHORITY = "com.robertlimantoproject.madebygue.app.data.OfflineProvider";

    public  static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);


    /**
     * Constants for product_type table
     */

    public static final class PRODUCT implements BaseColumns{

        /**
         * Path for this table
         */

        public static final String PATH = "product";
        /**
         * The content uri for this table
         */

        public static final Uri CONTENT_URI = Uri.withAppendedPath(OfflineContract.CONTENT_URI, PRODUCT.PATH );

        public static final String PRODUCT_ID = "product_id";

        public static final String PRODUCT_DESC = "product_desc";

        public static final String PRICE = "price";

        public static final String CREATING_DURATION = "creating_duration";

        public static final String SUBCATEGORY_ID = "subcategory_id";
        /**
         * A projection of all columns in the this table.
         */
        public static final String[] PROJECTION_ALL = { SUBCATEGORY_ID, PRODUCT_ID, PRODUCT_DESC, PRICE, CREATING_DURATION};

    }

    /**
    * Constants for category_product table
    */

    public static class SUBCATEGORY implements  BaseColumns {

        /**
         * Path for this table
         */

        public static final String PATH = "subcategory";
        /**
         * The content uri for this table
         */

        public static final Uri CONTENT_URI = Uri.withAppendedPath(OfflineContract.CONTENT_URI, SUBCATEGORY.PATH );

        public static final String SUBCATEGORY_ID = "subcategory_id";

        public static final String CATEGORY_ID = "category_id";

        public static final String SUBCATEGORY_NAME = "subcategory_name";

        public static final String[] PROJECTION_ALL = { SUBCATEGORY_NAME, SUBCATEGORY_ID, CATEGORY_ID};

    }

    public static class CATEGORY implements BaseColumns {

        /**
         * Path for this table
         */

        public static final String PATH = "category";
        /**
         * The content uri for this table
         */

        public static final Uri CONTENT_URI = Uri.withAppendedPath(OfflineContract.CONTENT_URI, CATEGORY.PATH );

        public static final String CATEGORY_NAME = "category_name";

        public static final String CATEGORY_IMAGE = "category_image";

        public static final String CATEGORY_ID = "category_id";

        public static final String[] PROJECTION_ALL = {CATEGORY_ID, CATEGORY_NAME, CATEGORY_IMAGE};


        /**
         * The mime type of a directory of items.
         */
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.com.robertlimantoproject.madebygue.cat_image";
        /**
         * The mime type of a single item.
         */
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.com.robertlimantoproject.madebygue.cat_image";
    }
}

