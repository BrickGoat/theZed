package com.example.brick.thezed;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

/**
 * Database currently holding unsorted input from addActivityPage
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Entries.db";
    public static final String TABLE_RAW = "unsorted_entries";
    public static final String TABLE_SORT = "sorted_entries";
    //public static final String COL0 = "ID";
    public static final String COL1 = "NAME";
    public static final String COL2 = "DESCRIPTION";
    public static final String COL3 = "SUBJECT";
    public static final String COL4 = "INITIALDATE";
    public static final String COL5 = "DUEDATE";
    public static final String COL6 = "TIME";
    //public static final String COL7 = "ENJOYMENT";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String unsortedEntries = "CREATE TABLE " + TABLE_RAW
                + " (ID INTEGER PRIMARY KEY AUTOINCREMENT , " +
                "NAME TEXT, DESCRIPTION TEXT, SUBJECT TEXT, INITIALDATE TEXT, DUEDATE TEXT, TIME TEXT)";
        //String sortedEntries = "CREATE TABLE " + TABLE_SORT
        //         + " (ID INTEGER PRIMARY KEY AUTOINCREMENT , " +
        //         "NAME TEXT, DESCRIPTION TEXT, SUBJECT TEXT, INITIALDATE TEXT, DUEDATE TEXT, TIME INTEGER, ENJOYMENT INTEGER)";
        db.execSQL(unsortedEntries);
        // db.execSQL(sortedEntries);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RAW);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_SORT);
        onCreate(db);
    }

    public boolean addData(String name, String description, String subject, String initialDate
            , String dueDate, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put(COL1, id);
        contentValues.put(COL1, name);
        contentValues.put(COL2, description);
        contentValues.put(COL3, subject);
        contentValues.put(COL4, initialDate);
        contentValues.put(COL5, dueDate);
        contentValues.put(COL6, time);
        //contentValues.put(COL7, enjoyment);
        long result = db.insert(TABLE_RAW, null, contentValues);
        db.close();
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getListContents() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_RAW, null);
        return data;
    }
    public void deleteinformation(String name,SQLiteDatabase sqLiteDatabase){
        String selection = COL1+" LIKE ?";
        String[] selection_args={name};
        sqLiteDatabase.delete(TABLE_RAW,selection,selection_args);

    }
}
