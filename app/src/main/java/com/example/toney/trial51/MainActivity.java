package com.example.toney.trial51;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.fragment;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    final List<MenuItem> items = new ArrayList<>();
    Menu menu;
    Fragment fragment;

    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    String IdNewNote;

    SharedPreferences sharedPreferences;



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
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        menu = navigationView.getMenu();

        for(int i=0; i<menu.size(); i++){
            items.add(menu.getItem(i));
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                int position=items.indexOf(menuItem);


                switch(position) {
                    case 0 :
                        fragment = new HomeFragment();
                        break;
                    case 1 :
                        fragment = new TrashFragment();
                        break;
                    case 2 :
                        Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
                        startActivity(i);
                        break;

                    default:
                        break;
                }

                if(fragment != null){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    setTitle(menuItem.getTitle());
                }


                return true;
            }
        });

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sqLiteDatabase = getApplicationContext().openOrCreateDatabase("Trial", MODE_PRIVATE, null);
                    sqLiteDatabase.execSQL("INSERT INTO trial2(createdTime) VALUES(datetime('now','localtime'))");
                    cursor = sqLiteDatabase.rawQuery("SELECT id FROM trial2 ORDER BY datetime(createdtime) DESC LIMIT 1", null);
                    cursor.moveToFirst();
                    IdNewNote = cursor.getString(0);

                    Intent i = new Intent(getApplicationContext(), EditEntry.class);
                    i.putExtra("noteID", IdNewNote);
                    startActivity(i);
                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Unable to open a new note. Please try later.", Toast.LENGTH_LONG).show();
                }
            }
        });

        Fragment fragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        setTitle(R.string.nav_item_home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                Intent j = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(j);
                return true;
            case R.id.search_icon:
                Intent i = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(i);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
