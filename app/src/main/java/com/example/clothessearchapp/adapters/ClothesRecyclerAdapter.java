package com.example.clothessearchapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clothessearchapp.R;
import com.example.clothessearchapp.structure.Clothes;

import java.util.ArrayList;
import java.util.List;

public class ClothesRecyclerAdapter extends RecyclerView.Adapter<ClothesRecyclerAdapter.MyViewHolder> implements Filterable {

    private List<Clothes> clothes;
    private List<Clothes> allClothes;

    public ClothesRecyclerAdapter(List<Clothes> clothes) {
        this.clothes = clothes;
        this.allClothes = new ArrayList<>(clothes);
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Clothes> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0){
                filteredList.addAll(allClothes);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Clothes clothes: allClothes){
                    if (clothes.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(clothes);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clothes.clear();
            clothes.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    class MyViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout constraintLayout;
        ImageView favouriteStar;
        TextView clothesName;
        TextView clothesType;
//        TextView testViewTitle;

        MyViewHolder(View view) {
            super(view);
//            this.testViewTitle = view.findViewById(R.id.title);
            this.constraintLayout = (ConstraintLayout) view;
            this.favouriteStar = view.findViewById(R.id.favourite_star);
            this.clothesName = view.findViewById(R.id.clothes_name);
            this.clothesType = view.findViewById(R.id.type);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.clothes_card_view_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        Clothes chosenClothes = clothes.get(position);
        holder.clothesName.setText(chosenClothes.getName());
        holder.clothesType.setText(chosenClothes.getType());

//        if (chosenClothes.isFavourite()){
//            holder.favouriteStar.setImageResource(android.R.drawable.btn_star_big_on);
//        } else {
//            holder.favouriteStar.setImageResource(android.R.drawable.btn_star_big_off);
//        }
//
//        holder.favouriteStar.setOnClickListener(v -> {
//            Toast.makeText(v.getContext(), chosenClothes.getName(), Toast.LENGTH_SHORT).show();
//
//            if(chosenClothes.isFavourite()){
//                holder.favouriteStar.setImageResource(android.R.drawable.btn_star_big_off);
//                chosenClothes.setFavourite(false);
//            } else {
//                holder.favouriteStar.setImageResource(android.R.drawable.btn_star_big_on);
//                chosenClothes.setFavourite(true);
//            }
//        });
        holder.constraintLayout.setElevation(40.0f);
//        holder.constraintLayout.setOnClickListener(view -> Toast.makeText(view.getContext(), clothes.get(position).getName(), Toast.LENGTH_LONG).show());

    }

    @Override
    public int getItemCount() {
        return clothes.size();
    }
}
