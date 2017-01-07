package com.example.toney.trial51;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;




public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.MyViewHolder> {

    private List<Entry> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, place,  content;
        //public TextView createdTime, modifiedTime, seconds;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            place = (TextView) view.findViewById(R.id.place);
            //createdTime = (TextView) view.findViewById(createdTime);
            //modifiedTime = (TextView) view.findViewById(modifiedTime);
            //seconds = (TextView) view.findViewById(seconds);
            content = (TextView) view.findViewById(R.id.content);
        }
    }


    public EntryAdapter(List<Entry> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.entry_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Entry entry = moviesList.get(position);
        holder.title.setText(entry.getTitle());
        holder.place.setText(entry.getPlace());
        //holder.createdTime.setText(entry.getCreatedTime());
       // holder.modifiedTime.setText(entry.getModifiedTime());
       // holder.seconds.setText(entry.getSeconds());
        holder.content.setText(entry.getContent());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}