package com.IOVIS.datavault;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class Mydatabase extends SQLiteOpenHelper {
    private Context context; //Interface which has implementations of DATABASE_NAME,TABLE_NAME,DATABASE_VERSION
    private static final String DATABASE_NAME = "data_info.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "table1";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_DOB = "dob";
    private static final String COLUMN_EMAIL = "email";


    Mydatabase( @Nullable Context context) {
//        Interface which has implementations of DATABASE_NAME,TABLE_NAME
//    public SQLiteOpenHelper(@android.annotation.Nullable Context context, @android.annotation.Nullable String name, @android.annotation.Nullable SQLiteDatabase.CursorFactory factory, int version){
//        }
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //creation of database
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME + " TEXT, " +
                        COLUMN_PHONE + " TEXT, " +
                        COLUMN_DOB + " TEXT, " +
                        COLUMN_EMAIL + " TEXT); " ;
        //CREATE TABLE TABLE_NAME (COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, COLUMN_NAME TEXT, COLUMN_PHONE TEXT, COLUMN_DOB TEXT, COLUMN_EMAIL TEXT);
        db.execSQL(query);
        //Executes Query
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //If we want to upgrade the database(Add or Remove columns) then we make changes here.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    void add_details(String name,String phone,String dob,String email){
        //Add details in side the app
        SQLiteDatabase db = this.getWritableDatabase();
        //getWritableDatabase method is used to open or create the database for writing.
        ContentValues cv = new ContentValues();
        //ContentValues class is used to store a set of values that can be inserted into a database.

        //put(String key, String value)
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_PHONE, phone);
        cv.put(COLUMN_DOB, dob);
        cv.put(COLUMN_EMAIL, email);
        //Add all details to the database as COLUMN_TITLE, data

        long result = db.insert(TABLE_NAME,null,cv);
        //inserts values in ContentValues
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }
    Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME ;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    void updateData(String row_id,String name, String phone, String dob,String email){
//        Updates data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_PHONE, phone);
        cv.put(COLUMN_DOB, dob);
        cv.put(COLUMN_EMAIL, email);

        long result = db.update(TABLE_NAME, cv, "id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }
    void deleteOneRow(String row_id){
//        Deletes data
        SQLiteDatabase db = this.getWritableDatabase();
        long result;
        result = db.delete(TABLE_NAME, "id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }
    void deleteAllData(){
//        deletes all data
        SQLiteDatabase db = this.getWritableDatabase();
        // Step 1: Drop the original table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Step 2: Recreate the original table
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME + " TEXT, " +
                        COLUMN_PHONE + " TEXT, " +
                        COLUMN_DOB + " TEXT, " +
                        COLUMN_EMAIL + " TEXT); " ;
        db.execSQL(query);
    }
}
