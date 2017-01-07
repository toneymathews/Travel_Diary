package com.example.toney.trial51;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    private List<Entry> list;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, place, content;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.search_title);
            place = (TextView) view.findViewById(R.id.search_place);
            content = (TextView) view.findViewById(R.id.search_content);

        }
    }


    public SearchAdapter(List<Entry> list) {
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Entry entry = list.get(position);
        holder.title.setText(entry.getTitle());
        holder.place.setText(entry.getPlace());
        holder.content.setText(entry.getContent());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
