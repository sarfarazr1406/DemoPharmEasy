package com.android.demoapp.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Sarfaraz on 1/30/2016.
 */
public class MedicineData extends ContentProvider {

    public static final String PROVIDER_NAME = "com.example.pharmeasy.PHARMA";
    public static final String URL = "content://" + PROVIDER_NAME + "/medicine";
    public static final Uri CONTENT_URI = Uri.parse(URL);

    public static final String _ID = "_id";
    public static final String _ID_PRIME = "_id_prime";
    public static final String NAME = "name";
    public static final String PACKFORM = "packform";
    public static final String MANUFACTURER = "manufacturer";
    public static final String LABEL = "label";
    public static final String MFID = "mfid";
    public static final String AVAILABLE = "grade";
    public static final String FORM = "form";
    public static final String PFORM = "PFORM";
    public static final String DISCPRICE = "DISCPRICE";
    public static final String SU = "SU";
    public static final String PRODUCTSFORBRAND = "productsforbrand";
    public static final String OPRICE = "oprice";
    public static final String PACKSIZE = "packsize";
    public static final String MRP = "mrp";
    public static final String GENERICS = "generics";
    public static final String IMGURL = "imgUrl";
    public static final String HKPDRUGCODE = "hkpdrugcode";
    public static final String UIP = "uip";
    public static final String TYPE = "type";
    public static final String UPRICE = "uprice";
    public static final String SLUG = "slug";

    private static SQLiteDatabase db;
    public static final String DATABASE_NAME = "Pharma";
    public static final String MEDICINE_TABLE_NAME = "medicine";
    public static final int DATABASE_VERSION = 5;
    static final String CREATE_DB_TABLE =
            " CREATE TABLE " + MEDICINE_TABLE_NAME +
                    " ("+_ID_PRIME+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    _ID+" INTEGER, " +
                    NAME +" TEXT NOT NULL, " +
                    MANUFACTURER+" TEXT NOT NULL, " +
                    LABEL+" TEXT, " +
                    PACKFORM+ " TEXT, " +
                    MFID+" INTEGER, " +
                    AVAILABLE+" TEXT, " +
                    FORM+ " TEXT, " +
                    PFORM+ " TEXT, " +
                    DISCPRICE+ " REAL, " +
                    SU+ " INTEGER, " +
                    PRODUCTSFORBRAND+ " TEXT, " +
                    OPRICE+ " REAL, " +
                    PACKSIZE+ " TEXT, " +
                    MRP+ " REAL, " +
                    GENERICS+ " TEXT, " +
                    IMGURL+ " TEXT, " +
                    HKPDRUGCODE+ " INTEGER, " +
                    UIP+ " INTEGER, " +
                    TYPE+ " TEXT, " +
                    UPRICE+ " REAL, " +
                    SLUG+ " TEXT);";


    static class DataBaseHelper extends SQLiteOpenHelper {


        public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i("saz", "table created");
            try {
                db.execSQL(CREATE_DB_TABLE);
            } catch (android.database.SQLException e) {
                Log.i("saz",""+e.toString());
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i("saz", "table updated");
            db.execSQL("DROP TABLE IF EXISTS " +  MEDICINE_TABLE_NAME);
            onCreate(db);
        }
    }


    @Override
    public boolean onCreate() {
        Context context = getContext();
        DataBaseHelper dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);

        /**
         * Create a write able database which will trigger its
         * creation if it doesn't already exist.
         */
        db = dbHelper.getWritableDatabase();
        return true;
    }

    public Cursor getTaskCount() {
        if (db != null) {
            Cursor cursor = db.rawQuery("SELECT * FROM " + MEDICINE_TABLE_NAME, null);
            Log.i("saz", cursor.getCount() + " no of rows");
            if (cursor != null && cursor.getCount() != 0)
                cursor.moveToNext();
            return cursor;
        }
        return null;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }


    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowID = db.insert(	MEDICINE_TABLE_NAME, "", values);
        Log.i("saz",""+rowID);
        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }




}
