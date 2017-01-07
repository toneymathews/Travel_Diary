package com.example.toney.trial51;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
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


public class TrashFragment extends Fragment {

    protected List<Entry> trashList = new ArrayList<>();
    protected RecyclerView trashRecyclerView;
    protected EntryAdapter trashAdapter;

    SQLiteDatabase trashDB;
    SQLiteTrashHelper sqLiteTrashHelper;
    ArrayList<String> id_trashList;
    Cursor cursor;
    String title, place, createdTime, modifiedTime, seconds, content;

    public TrashFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        sqLiteTrashHelper = new SQLiteTrashHelper(getActivity());
        id_trashList = new ArrayList<String>();

        View rootView = inflater.inflate(R.layout.fragment_trash, container, false);

        trashRecyclerView = (RecyclerView) rootView.findViewById(R.id.trash_recycler_view);
        trashAdapter = new EntryAdapter(trashList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        trashRecyclerView.setLayoutManager(mLayoutManager);
        trashRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //trashRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        trashRecyclerView.setAdapter(trashAdapter);

        trashRecyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getActivity(), trashRecyclerView, new ClickListener() {

            public void onClick(View view, int position) {
                Entry entry = trashList.get(position);
                Toast.makeText(getActivity(), entry.getSeconds() + " is selected!", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(getActivity(), ViewTrashEntry.class);
                i.putExtra("noteID", id_trashList.get(position));
                startActivity(i);
            }

            public void onLongClick(View view, int position) {

                final String ID = id_trashList.get(position);   //gets the id of the entry. listadapter position is used to get the entry id

                new AlertDialog.Builder(getActivity())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to move entry from trash to main folder?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                try {
                                    String selectEntry = "SELECT * FROM "+SQLiteTrashHelper.Trash_Table+" WHERE id = " + ID;
                                    cursor = trashDB.rawQuery(selectEntry, null);
                                    cursor.moveToFirst();
                                    title = cursor.getString(cursor.getColumnIndex(SQLiteTrashHelper.Trash_Title));
                                    place = cursor.getString(cursor.getColumnIndex(SQLiteTrashHelper.Trash_Place));
                                    createdTime = cursor.getString(cursor.getColumnIndex(SQLiteTrashHelper.Trash_Created_Time));
                                    modifiedTime = cursor.getString(cursor.getColumnIndex(SQLiteTrashHelper.Trash_Modified_Time));
                                    seconds = cursor.getString(cursor.getColumnIndex(SQLiteTrashHelper.Trash_Seconds));
                                    content = cursor.getString(cursor.getColumnIndex(SQLiteTrashHelper.Trash_Content));

                                    String updateMainEntry = "INSERT INTO trial2(title, place, createdTime, modifiedTime, seconds, content) VALUES(?,?,?,?,?,?)";
                                    SQLiteStatement statement = trashDB.compileStatement(updateMainEntry);
                                    statement.bindString(1, title);
                                    statement.bindString(2, place);
                                    statement.bindString(3, createdTime);
                                    statement.bindString(4, modifiedTime);
                                    statement.bindString(5, seconds);
                                    statement.bindString(6, content);
                                    statement.execute();

                                    String deleteEntry = "DELETE from trash1 where id =" + ID;
                                    trashDB = getActivity().openOrCreateDatabase(SQLiteTrashHelper.DATABASE_NAME, MODE_PRIVATE, null);
                                    trashDB.execSQL(deleteEntry);
                                    showTrashRecyclerView();
                                    trashAdapter.notifyDataSetChanged();

                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                    Toast.makeText(getActivity(), "Unable to delete the note. Please try later.", Toast.LENGTH_LONG).show();


                                }
                                //listAdapter.notifyDataSetChanged();




                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                //return true;
                showTrashRecyclerView();
                trashAdapter.notifyDataSetChanged();

            }
        }));




        showTrashRecyclerView();
        trashAdapter.notifyDataSetChanged();

        return rootView;
    }

    public void createTrash() {

        try {
            trashDB = getActivity().openOrCreateDatabase(SQLiteTrashHelper.DATABASE_NAME, MODE_PRIVATE, null);
            trashDB.execSQL("CREATE TABLE IF NOT EXISTS "+SQLiteTrashHelper.Trash_Table+"(id INTEGER PRIMARY KEY, title VARCHAR, place VARCHAR, createdTime VARCHAR, modifiedTime VARCHAR, seconds VARCHAR, content VARCHAR)");
            //String createdTime = android.text.format.DateFormat.format("EEE dd MMM yyyy", new java.util.Date()).toString();
            //String modifiedTime = android.text.format.DateFormat.format("hh:mm a", new java.util.Date()).toString();
            //liteDatabase.execSQL("INSERT INTO trial1(title,place,createdTime,modifiedTime,seconds,content) VALUES('hi', 'chennai', '" + createdTime + "', '" + modifiedTime + "', strftime('%s','now'), 'content')");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void showTrashRecyclerView(){

        sharedPreferences = getActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String order = sharedPreferences.getString("Order", "");
        String query = "";
        switch (order){
            case "Newest Created":
                query = "SELECT * FROM "+SQLiteTrashHelper.Trash_Table+" ORDER BY createdTime DESC";
                break;
            case "Oldest Created":
                query = "SELECT * FROM "+SQLiteTrashHelper.Trash_Table+" ORDER BY createdTime";
                break;
            case "Newest Modified":
                query = "SELECT * FROM "+SQLiteTrashHelper.Trash_Table+" ORDER BY modifiedTime DESC";
                break;
            case "Oldest Modified":
                query = "SELECT * FROM "+SQLiteTrashHelper.Trash_Table+" ORDER BY modifiedTime";
                break;
            default:
                query = "SELECT * FROM trash1";
                break;
        }

        createTrash();
        trashDB = sqLiteTrashHelper.getReadableDatabase();

        cursor = trashDB.rawQuery(query, null);

        trashList.clear();
        id_trashList.clear();

        Entry entry;


        if (cursor.moveToFirst()) {
            do {
                entry = new Entry(cursor.getString(cursor.getColumnIndex(SQLiteTrashHelper.Trash_Title)),cursor.getString(cursor.getColumnIndex(SQLiteTrashHelper.Trash_Place)),cursor.getString(cursor.getColumnIndex(SQLiteTrashHelper.Trash_Created_Time)),cursor.getString(cursor.getColumnIndex(SQLiteTrashHelper.Trash_Modified_Time)),cursor.getString(cursor.getColumnIndex(SQLiteTrashHelper.Trash_Seconds)),cursor.getString(cursor.getColumnIndex(SQLiteTrashHelper.Trash_Content)));
                trashList.add(entry);
                id_trashList.add(cursor.getString(cursor.getColumnIndex(SQLiteTrashHelper.Trash_ID)));
            } while (cursor.moveToNext());
        }


        //listAdapter.notifyDataSetChanged();

        cursor.close();
    }

    @Override
    public void onResume() {
        super.onResume();
        showTrashRecyclerView();
        trashAdapter.notifyDataSetChanged();

    }




}
