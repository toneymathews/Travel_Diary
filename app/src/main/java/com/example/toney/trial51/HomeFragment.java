package com.example.toney.trial51;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

import static com.example.toney.trial51.SettingsActivity.sharedPreferences;


public class HomeFragment extends Fragment {


    protected List<Entry> entryList = new ArrayList<>();
    protected RecyclerView recyclerView;
    protected EntryAdapter entryAdapter;

    static ArrayList<String> id_ArrayList;
    SQLiteDatabase liteDatabase;
    SQLiteHelper sqLiteHelper;
    Cursor cursor;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        sqLiteHelper = new SQLiteHelper(getActivity());
        id_ArrayList = new ArrayList<String>();

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        entryAdapter = new EntryAdapter(entryList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(entryAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getActivity(), recyclerView, new ClickListener() {

            public void onClick(View view, int position) {
                Entry entry = entryList.get(position);
                Toast.makeText(getActivity(), entry.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(getActivity(), EditEntry.class);
                i.putExtra("noteID", id_ArrayList.get(position));
                startActivity(i);
            }


            public void onLongClick(View view, int position) {

                final String ID = id_ArrayList.get(position);   //gets the id of the entry. listadapter position is used to get the entry id

                new AlertDialog.Builder(getActivity())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                try {
                                    String updateTrash = "INSERT INTO trash1 SELECT * FROM trial2 WHERE id = "+ ID;
                                    liteDatabase = getActivity().openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, MODE_PRIVATE, null);
                                    liteDatabase.execSQL(updateTrash);

                                    String deleteEntry = "DELETE FROM trial2 WHERE id = " + ID;
                                    liteDatabase.execSQL(deleteEntry);

                                    showRecyclerView();
                                    entryAdapter.notifyDataSetChanged();
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                    Toast.makeText(getActivity(), "Unable to delete the note. Please try later.", Toast.LENGTH_LONG).show();


                                }




                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                //return true;

                showRecyclerView();

                entryAdapter.notifyDataSetChanged();


            }
        }));


        showRecyclerView();

        entryAdapter.notifyDataSetChanged();

        //prepareMovieData();


        return rootView;
    }

    public void prepareMovieData() {
        Entry entry = new Entry("Mad Max", "Fury" ,"Road", "Action" , "Adventure", "2015");
        entryList.add(entry);

        entry = new Entry("Inside"," Out", "Animation", "Kids "," Family", "2015");
        entryList.add(entry);

        entryAdapter.notifyDataSetChanged();


    }

    @Override
    public void onResume() {
        super.onResume();
        showRecyclerView();
        entryAdapter.notifyDataSetChanged(); //this command will always be called from the oncreate/onresume to maintain the scroll position of the main activity

    }

    public void createDB() {

        try {
            liteDatabase = getActivity().openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, MODE_PRIVATE, null);
            liteDatabase.execSQL("CREATE TABLE IF NOT EXISTS trial2(id INTEGER PRIMARY KEY, title VARCHAR, place VARCHAR, createdTime VARCHAR, modifiedTime VARCHAR, seconds VARCHAR, content VARCHAR)");
            //liteDatabase.execSQL("DELETE FROM trial2");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void createTrash() {

        try {
            liteDatabase = getActivity().openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, MODE_PRIVATE, null);
            liteDatabase.execSQL("CREATE TABLE IF NOT EXISTS trash1(id INTEGER PRIMARY KEY, title VARCHAR, place VARCHAR, createdTime VARCHAR, modifiedTime VARCHAR, seconds VARCHAR, content VARCHAR)");
            //String createdTime = android.text.format.DateFormat.format("EEE dd MMM yyyy", new java.util.Date()).toString();
            //String modifiedTime = android.text.format.DateFormat.format("hh:mm a", new java.util.Date()).toString();
            //liteDatabase.execSQL("DELETE FROM trash1");
            //liteDatabase.execSQL("INSERT INTO trash1(title,place,createdTime,modifiedTime,seconds,content) VALUES('hi', 'chennai', '" + createdTime + "', '" + modifiedTime + "', strftime('%s','now'), 'content')");
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    public void showRecyclerView() {


        sharedPreferences = getActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);

        String order = sharedPreferences.getString("Order", "");
        String query = "";
        switch (order){
            case "Newest Created":
                query = "SELECT * FROM trial2 ORDER BY createdTime DESC";
                break;
            case "Oldest Created":
                query = "SELECT * FROM trial2 ORDER BY createdTime";
                break;
            case "Newest Modified":
                query = "SELECT * FROM trial2 ORDER BY modifiedTime DESC";
                break;
            case "Oldest Modified":
                query = "SELECT * FROM trial2 ORDER BY modifiedTime";
                break;
            default:
                query = "SELECT * FROM trial2";
                break;
        }
        createDB();
        createTrash();
        liteDatabase = sqLiteHelper.getReadableDatabase();

        cursor = liteDatabase.rawQuery(query, null);

        entryList.clear();
        id_ArrayList.clear();

        Entry entry;

        try {
            if (cursor.moveToFirst()) {
                do {

                    entry = new Entry(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_Title)), cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_Place)), cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_Created_Time)), cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_Modified_Time)), cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_Seconds)), cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_Content)));

                    entryList.add(entry);
                    id_ArrayList.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_ID)));
                } while (cursor.moveToNext());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }


        //listAdapter.notifyDataSetChanged();

        cursor.close();
    }




}
