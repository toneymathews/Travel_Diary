package com.example.toney.trial51;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.MyViewHolder> {

    private List<Settings> list;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView key, value;

        public MyViewHolder(View view) {
            super(view);
            key = (TextView) view.findViewById(R.id.key);
            value = (TextView) view.findViewById(R.id.value);

        }
    }


    public SettingsAdapter(List<Settings> list) {
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.settings_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Settings settings = list.get(position);
        holder.key.setText(settings.getKey());
        holder.value.setText(settings.getValue());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}