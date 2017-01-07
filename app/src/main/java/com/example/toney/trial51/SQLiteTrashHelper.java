package com.example.toney.trial51;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SQLiteTrashHelper extends SQLiteOpenHelper {

    static String DATABASE_NAME="Trial";

    public static final String Trash_ID="id";

    public static final String Trash_Table="trash1";

    public static final String Trash_Title="title";

    public static final String Trash_Place="place";

    public static final String Trash_Created_Time ="createdTime";

    public static final String Trash_Modified_Time ="modifiedTime";

    public static final String Trash_Seconds="seconds";

    public static final String Trash_Content="content";



    //public static final String KEY_Content="content";

    public SQLiteTrashHelper(Context context) {

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