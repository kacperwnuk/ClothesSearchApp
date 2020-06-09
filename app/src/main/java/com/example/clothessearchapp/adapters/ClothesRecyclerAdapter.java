package com.example.clothessearchapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clothessearchapp.R;
import com.example.clothessearchapp.structure.Clothes;
import com.example.clothessearchapp.structure.DetailClothesRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClothesRecyclerAdapter extends RecyclerView.Adapter<ClothesRecyclerAdapter.MyViewHolder> implements Filterable {

    private List<Clothes> clothes;
    private List<Clothes> allClothes;
    private int card_view_id;

    private HashMap<String, Integer> logos = new HashMap<String, Integer>(){{
        put("HM", R.drawable.hm_logo);
        put("House", R.drawable.house_logo);
        put("Reserved", R.drawable.reserved_logo);
    }};

    public ClothesRecyclerAdapter(List<Clothes> clothes, int card_view_id) {
        this.clothes = clothes;
        this.allClothes = new ArrayList<>(clothes);
        this.card_view_id = card_view_id;
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
        ImageView logo;
        TextView clothesName;
        TextView clothesPrice;
        ImageButton deleteButton;

        MyViewHolder(View view) {
            super(view);
            this.constraintLayout = (ConstraintLayout) view;
            this.logo = view.findViewById(R.id.logo);
            this.clothesName = view.findViewById(R.id.product_name);
            this.clothesPrice = view.findViewById(R.id.clothes_price);
            this.deleteButton = view.findViewById(R.id.delete_button);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(card_view_id, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        Clothes chosenClothes = clothes.get(position);
        holder.clothesName.setText(chosenClothes.getName());
        holder.clothesPrice.setText(chosenClothes.getPrice());
        holder.logo.setImageResource(logos.getOrDefault(chosenClothes.getShop(), R.drawable.hm_logo));
        DetailClothesRequest detailClothesRequest = new DetailClothesRequest(chosenClothes.getShop(), chosenClothes.getKey());
        holder.constraintLayout.setTag(detailClothesRequest);
        if(holder.deleteButton != null){
            holder.deleteButton.setTag(detailClothesRequest);
        }

        holder.constraintLayout.setElevation(40.0f);

    }

    @Override
    public int getItemCount() {
        return clothes.size();
    }
}
