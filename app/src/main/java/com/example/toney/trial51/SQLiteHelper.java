package com.example.toney.trial51;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SQLiteHelper extends SQLiteOpenHelper {

    static String DATABASE_NAME="Trial";

    public static final String KEY_ID="id";

    public static final String TABLE_NAME="trial2";

    public static final String KEY_Title="title";

    public static final String KEY_Place="place";

    public static final String KEY_Created_Time ="createdTime";

    public static final String KEY_Modified_Time="modifiedTime";

    public static final String KEY_Seconds="seconds";

    public static final String KEY_Content="content";



    //public static final String KEY_Content="content";

    public SQLiteHelper(Context context) {

        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        //String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+" ("+KEY_ID+" INTEGER PRIMARY KEY, "+KEY_Title+" VARCHAR, "+KEY_Content+" VARCHAR, "+KEY_Place+" VARCHAR)";
        //database.execSQL(CREATE_TABLE);
        //createdTime var is not in the above commands

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        //onCreate(db);

    }


}