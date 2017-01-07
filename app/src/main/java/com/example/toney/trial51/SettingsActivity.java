package com.example.toney.trial51;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.support.v4.app.TaskStackBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SettingsActivity extends AppCompatActivity {

    public static SharedPreferences sharedPreferences;
    ArrayList<Settings> arrayList;
    RecyclerView settingsRecyclerView;
    SettingsAdapter settingsAdapter;
    SharedPreferences.Editor editor;
    ArrayList<String> themeList = new ArrayList<String>();
    ArrayList<String> fontList = new ArrayList<String>();
    ArrayList<String> orderList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPreferences = this.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String theme = sharedPreferences.getString("Theme", "");
        switch (theme) {
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
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        arrayList = new ArrayList<Settings>();
        themeList.add(0, "Light");
        themeList.add(1, "Dark");
        fontList.add(0, "Small");
        fontList.add(1, "Medium");
        fontList.add(2, "Large");
        orderList.add(0, "Newest Created");
        orderList.add(1, "Oldest Created");
        orderList.add(2, "Newest Modified");
        orderList.add(3, "Oldest Modified");



        settingsRecyclerView = (RecyclerView) findViewById(R.id.settings_recycler_view);

        settingsAdapter = new SettingsAdapter(arrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        settingsRecyclerView.setLayoutManager(mLayoutManager);
        settingsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        settingsRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        settingsRecyclerView.setAdapter(settingsAdapter);
        settingsRecyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getApplicationContext(), settingsRecyclerView, new ClickListener() {

            public void onClick(View view, int position) {
                Settings settings = arrayList.get(position);
                final Dialog dialog = new Dialog(SettingsActivity.this);
                final RadioGroup radioGroup;


                switch (String.valueOf(settings.getKey())) {
                    case "Theme":
                        dialog.setTitle("Theme");
                        dialog.setContentView(R.layout.theme_layout);
                        radioGroup = (RadioGroup) dialog.findViewById(R.id.radio_group);

                        for (int i = 0; i < themeList.size(); i++) {
                            RadioButton radioButton = new RadioButton(SettingsActivity.this); // dynamically creating RadioButton and adding to RadioGroup.
                            radioButton.setText(themeList.get(i));
                            radioGroup.addView(radioButton);
                        }
                        for (int x = 0; x < radioGroup.getChildCount(); x++) {
                            RadioButton button = (RadioButton) radioGroup.getChildAt(x);
                            if (sharedPreferences.getString("Theme", null).equals(button.getText().toString())) {
                                int checkedId = button.getId();
                                radioGroup.check(checkedId);
                            }
                        }
                        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                int childCount = group.getChildCount();
                                for (int x = 0; x < childCount; x++) {
                                    RadioButton button = (RadioButton) group.getChildAt(x);
                                    if (button.getId() == checkedId) {
                                        Log.e("selected RadioButton->", button.getText().toString());
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("Theme", button.getText().toString());
                                        editor.commit();
                                        prepareData();
                                        radioGroup.check(checkedId);
                                        dialog.dismiss();
                                        TaskStackBuilder.create(SettingsActivity.this)
                                                .addNextIntent(new Intent(SettingsActivity.this, MainActivity.class))
                                                .addNextIntent(SettingsActivity.this.getIntent())
                                                .startActivities();

                                    }
                                }
                            }
                        });

                        break;
                    case "FontSize":
                        dialog.setTitle("FontSize");
                        dialog.setContentView(R.layout.theme_layout);
                        radioGroup = (RadioGroup) dialog.findViewById(R.id.radio_group);

                        for (int i = 0; i < fontList.size(); i++) {
                            RadioButton radioButton = new RadioButton(SettingsActivity.this); // dynamically creating RadioButton and adding to RadioGroup.
                            radioButton.setText(fontList.get(i));
                            radioGroup.addView(radioButton);
                        }
                        for (int x = 0; x < radioGroup.getChildCount(); x++) {
                            RadioButton button = (RadioButton) radioGroup.getChildAt(x);
                            if (sharedPreferences.getString("FontSize", null).equals(button.getText().toString())) {
                                int checkedId = button.getId();
                                radioGroup.check(checkedId);
                            }
                        }
                        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                int childCount = group.getChildCount();
                                for (int x = 0; x < childCount; x++) {
                                    RadioButton button = (RadioButton) group.getChildAt(x);
                                    if (button.getId() == checkedId) {
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("FontSize", button.getText().toString());
                                        editor.commit();
                                        prepareData();
                                        radioGroup.check(checkedId);
                                        dialog.dismiss();

                                    }
                                }
                            }
                        });

                        break;
                    case "Order":
                        dialog.setTitle("Order");
                        dialog.setContentView(R.layout.theme_layout);
                        radioGroup = (RadioGroup) dialog.findViewById(R.id.radio_group);

                        for (int i = 0; i < orderList.size(); i++) {
                            RadioButton radioButton = new RadioButton(SettingsActivity.this); // dynamically creating RadioButton and adding to RadioGroup.
                            radioButton.setText(orderList.get(i));
                            radioGroup.addView(radioButton);
                        }
                        for (int x = 0; x < radioGroup.getChildCount(); x++) {
                            RadioButton button = (RadioButton) radioGroup.getChildAt(x);
                            if (sharedPreferences.getString("Order", null).equals(button.getText().toString())) {
                                int checkedId = button.getId();
                                radioGroup.check(checkedId);
                            }
                        }
                        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                int childCount = group.getChildCount();
                                for (int x = 0; x < childCount; x++) {
                                    RadioButton button = (RadioButton) group.getChildAt(x);
                                    if (button.getId() == checkedId) {
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("Order", button.getText().toString());
                                        editor.commit();
                                        prepareData();
                                        radioGroup.check(checkedId);
                                        dialog.dismiss();

                                    }
                                }
                            }
                        });

                        break;
                    default:
                        break;

                }
                dialog.show();


            }

            public void onLongClick(View view, int position) {


            }
        }));

        sharedPreferences = this.getSharedPreferences("Settings", Context.MODE_PRIVATE);

        Settings settings = new Settings("Theme", sharedPreferences.getString("Theme", ""));
        if ( settings.getValue().isEmpty()){
            editor = sharedPreferences.edit();
            editor.putString("Theme", "Light");
            editor.putString("FontSize", "Medium");
            editor.putString("Order", "Newest Created");
            editor.commit();

        }




        prepareData();


    }

    public void prepareData() {

        arrayList.clear();
        Settings settings = new Settings("Theme", sharedPreferences.getString("Theme", ""));

        arrayList.add(settings);

        settings = new Settings("FontSize", sharedPreferences.getString("FontSize", ""));
        arrayList.add(settings);

        settings = new Settings("Order", sharedPreferences.getString("Order", ""));
        arrayList.add(settings);
        settingsAdapter.notifyDataSetChanged();
    }



}


/*
Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
        }

 */