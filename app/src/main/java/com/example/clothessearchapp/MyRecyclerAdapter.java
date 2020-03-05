package com.example.clothessearchapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> implements Filterable {

    private List<String> titles;
    private List<String> allTitles;

    MyRecyclerAdapter(List<String> titles) {
        this.titles = titles;
        this.allTitles = new ArrayList<>(titles);
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<String> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0){
                filteredList.addAll(allTitles);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (String title: allTitles){
                    if (title.toLowerCase().contains(filterPattern)){
                        filteredList.add(title);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            titles.clear();
            titles.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    class MyViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout constraintLayout;
//        TextView testViewTitle;

        MyViewHolder(View view) {
            super(view);
//            this.testViewTitle = view.findViewById(R.id.title);
            this.constraintLayout = (ConstraintLayout) view;
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_view_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        ((TextView)holder.constraintLayout.findViewById(R.id.clothes_name)).setText(titles.get(position));
        holder.constraintLayout.setElevation(40.0f);
//        holder.constraintLayout.setOnClickListener(view -> Toast.makeText(view.getContext(), titles.get(position), Toast.LENGTH_LONG).show());
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }
}
