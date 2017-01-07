package com.example.toney.trial51;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.toney.trial51.SettingsActivity.sharedPreferences;


public class ViewTrashEntry extends AppCompatActivity {

    TextView titleTrashEntry, placeTrashEntry, contentTrashEntry;
    String noteID;
    SQLiteDatabase runSQLQuery;
    Cursor cursor;
    int ID;
    String title, place, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPreferences = this.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String theme = sharedPreferences.getString("Theme", "");
        switch (theme){
            case "Light":
                setTheme(R.style.AppTheme);
                break;
            case "Dark":
                setTheme(R.style.AppThemeDark);
                break;
            default:
                break;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trash_entry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        titleTrashEntry = (TextView) findViewById(R.id.titleTrashEntry);
        placeTrashEntry = (TextView) findViewById(R.id.placeTrashEntry);
        contentTrashEntry = (TextView) findViewById(R.id.contentTrashEntry);
        Intent i = getIntent();
        noteID = i.getStringExtra("noteID");
        ID = Integer.parseInt(noteID);



        try {

            runSQLQuery = openOrCreateDatabase(SQLiteTrashHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);

            cursor = runSQLQuery.rawQuery("SELECT * FROM trash1 WHERE id = " + ID, null);

            cursor.moveToFirst();
            titleTrashEntry.setText(cursor.getString(cursor.getColumnIndex(SQLiteTrashHelper.Trash_Title)));
            contentTrashEntry.setText(cursor.getString(cursor.getColumnIndex(SQLiteTrashHelper.Trash_Content)));
            placeTrashEntry.setText(cursor.getString(cursor.getColumnIndex(SQLiteTrashHelper.Trash_Place)));

            title = cursor.getString(cursor.getColumnIndex(SQLiteTrashHelper.Trash_Title));
            place = cursor.getString(cursor.getColumnIndex(SQLiteTrashHelper.Trash_Place));
            content = cursor.getString(cursor.getColumnIndex(SQLiteTrashHelper.Trash_Content));
        } catch (Exception e) {

            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Please restart the application.", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent j = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(j);
            return true;
        }
        if (id == R.id.share){
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, title + place + content);
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, "Share using?"));
        }
        if (id == R.id.delete){
            try {

                String deleteEntry = "DELETE FROM trash1 WHERE id = " + noteID;
                runSQLQuery.execSQL(deleteEntry);
                finish();
            }
            catch (Exception e){
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Unable to delete the note. Please try later.", Toast.LENGTH_LONG).show();


            }
        }

        return super.onOptionsItemSelected(item);
    }


}
