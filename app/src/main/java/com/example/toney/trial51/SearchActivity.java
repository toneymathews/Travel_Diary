package com.example.toney.trial51;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.support.v7.widget.SearchView;
import android.app.SearchManager;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    SearchView searchView;
    ArrayList<Entry> arrayList;
    RecyclerView searchRecyclerView;
    SearchAdapter searchAdapter;
    SQLiteHelper sqLiteHelper;
    Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sqLiteHelper = new SQLiteHelper(this);
        arrayList = new ArrayList<Entry>();
        searchRecyclerView = (RecyclerView) findViewById(R.id.search_recycler_view);

        searchAdapter = new SearchAdapter(arrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        searchRecyclerView.setLayoutManager(mLayoutManager);
        searchRecyclerView.setItemAnimator(new DefaultItemAnimator());
        searchRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        searchRecyclerView.setAdapter(searchAdapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setIconifiedByDefault(false);
        searchView.setFocusable(true);
        searchView.clearFocus();
        searchView.requestFocusFromTouch();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                showResult(query);
                searchView.setQuery(query,false);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    public void showResult(String searchQuery){

        String query = "%"+searchQuery+"%";
        cursor = sqLiteHelper.getReadableDatabase().query("trial2", null, "title LIKE ? OR place LIKE ? OR content LIKE ?", new String[] {query, query, query},null,null,null);
        Entry entry;
        if (cursor.moveToFirst()){
            do {

                entry = new Entry(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_Title)), cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_Place)), cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_Created_Time)), cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_Modified_Time)), cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_Seconds)), cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_Content)));
                arrayList.add(entry);
            }while (cursor.moveToNext());
        }
        cursor.close();
        searchAdapter.notifyDataSetChanged();
    }

}
