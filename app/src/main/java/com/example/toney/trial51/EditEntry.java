package com.example.toney.trial51;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.toney.trial51.SettingsActivity.sharedPreferences;

public class EditEntry extends AppCompatActivity {SQLiteDatabase runSQLQuery, liteDatabase;
    String title, content, place;
    int ID;
    String UpdateRecordQuery, contentTextWatcher;
    Cursor cursor;
    String noteID;
    EditText titleEditText;
    EditText contentEditText;
    EditText placeEditText;



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
        setContentView(R.layout.activity_edit_entry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        titleEditText = (EditText) findViewById(R.id.titleEditText);
        contentEditText = (EditText) findViewById(R.id.contentEditText);
        placeEditText = (EditText) findViewById(R.id.placeEditText);

        Intent i = getIntent();
        noteID = i.getStringExtra("noteID");
        ID = Integer.parseInt(noteID);
        //run a sql query to get this record which matches the id. then setText all fields in the query.
        //update the query in the db on click and save.

        try {

            runSQLQuery = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);

            cursor = runSQLQuery.rawQuery("SELECT * FROM trial2 WHERE id = " + ID, null);

            cursor.moveToFirst();
            titleEditText.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_Title)));
            contentEditText.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_Content)));
            placeEditText.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_Place)));
        } catch (Exception e) {

            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Please restart the application.", Toast.LENGTH_LONG).show();

        }



    }

    public void onBackPressed() {
        super.onBackPressed();

        title = titleEditText.getText().toString();
        place = placeEditText.getText().toString();
        content = contentEditText.getText().toString();
        if(title.isEmpty() && content.isEmpty()){
            Toast.makeText(getApplicationContext(),"Cannot save an empty entry", Toast.LENGTH_SHORT).show();
            deleteEmptyEntry();
            finish();
        }
        else {
            try {
                UpdateRecordQuery = "UPDATE trial2 SET title = ?, place = ?,modifiedTime = datetime('now','localtime'), seconds = strftime('%s','now'), content = ? WHERE id = ?";
                SQLiteStatement statement = runSQLQuery.compileStatement(UpdateRecordQuery);

                statement.bindString(1, title);
                statement.bindString(2, place);
                //statement.bindString(3, createdTime);
                //statement.bindString(4, modifiedTime);
                statement.bindString(3, content);
                statement.bindString(4, noteID);

                statement.execute();
                Toast.makeText(getApplicationContext(), "entry Saved", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "entry Not Saved", Toast.LENGTH_LONG).show();
            }
            finish();
        }
    }
    @Override
    protected void onPause(){
        super.onPause();

        title = titleEditText.getText().toString();
        place = placeEditText.getText().toString();
        content = contentEditText.getText().toString();
        if(title.isEmpty() && content.isEmpty()){
            Toast.makeText(getApplicationContext(),"Cannot save an empty entry", Toast.LENGTH_SHORT).show();
            deleteEmptyEntry();
            finish();
        }
        else {
            try {
                UpdateRecordQuery = "UPDATE trial2 SET title = ?, place = ?,modifiedTime = datetime('now','localtime'), seconds = strftime('%s','now'), content = ? WHERE id = ?";
                SQLiteStatement statement = runSQLQuery.compileStatement(UpdateRecordQuery);

                statement.bindString(1, title);
                statement.bindString(2, place);
                //statement.bindString(3, createdTime);
                //statement.bindString(4, modifiedTime);
                statement.bindString(3, content);
                statement.bindString(4, noteID);

                statement.execute();
                Toast.makeText(getApplicationContext(), "Entry Saved", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Entry Not Saved", Toast.LENGTH_LONG).show();
            }
            finish();
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
        if(id == android.R.id.home){
            title = titleEditText.getText().toString();
            place = placeEditText.getText().toString();
            content = contentEditText.getText().toString();
            if(title.isEmpty() && content.isEmpty()){
                Toast.makeText(getApplicationContext(),"Cannot save an empty entry", Toast.LENGTH_SHORT).show();
                finish();
            }
            else {
                try {
                    UpdateRecordQuery = "UPDATE trial2 SET title = ?, place = ?,modifiedTime = datetime('now','localtime'), seconds = strftime('%s','now'), content = ? WHERE id = ?";
                    SQLiteStatement statement = runSQLQuery.compileStatement(UpdateRecordQuery);

                    statement.bindString(1, title);
                    statement.bindString(2, place);
                    //statement.bindString(3, createdTime);
                    //statement.bindString(4, modifiedTime);
                    statement.bindString(3, content);
                    statement.bindString(4, noteID);

                    statement.execute();
                    Toast.makeText(getApplicationContext(), "Entry Saved", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "entry Not Saved", Toast.LENGTH_LONG).show();
                }
                finish();
            }
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
                title = titleEditText.getText().toString();
                place = placeEditText.getText().toString();
                content = contentEditText.getText().toString();

                cursor = runSQLQuery.rawQuery("SELECT createdTime, modifiedTime from trial2 where id = " + noteID, null);
                cursor.moveToFirst();
                String createdTime = cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_Created_Time));
                String modifiedTime = cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_Modified_Time));
                //String createdTime = android.text.format.DateFormat.format("EEE, dd MMM yyyy", new java.util.Date()).toString();
                //String modifiedTime = android.text.format.DateFormat.format("hh:mm a", new java.util.Date()).toString();
                String updateTrash = "INSERT INTO trash1(title,place,createdTime,modifiedTime,seconds,content) values(?,?,?,?,strftime('%s','now'),?)";
                SQLiteStatement statement = runSQLQuery.compileStatement(updateTrash);

                statement.bindString(1, title);
                statement.bindString(2, place);
                statement.bindString(3, createdTime);
                statement.bindString(4, modifiedTime);
                statement.bindString(5, content);


                statement.execute();

                String deleteEntry = "DELETE FROM trial2 WHERE id = " + noteID;
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
    public void deleteEmptyEntry(){
        String deleteEntry = "DELETE FROM trial2 WHERE id = " + noteID;
        runSQLQuery.execSQL(deleteEntry);

    }



}
