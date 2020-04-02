package com.example.clothessearchapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clothessearchapp.R;
import com.example.clothessearchapp.structure.OldClothes;

import java.util.List;

public class FavouriteClothesRecyclerAdapter extends RecyclerView.Adapter<FavouriteClothesRecyclerAdapter.ViewHolder> {

    private List<OldClothes> favouriteClothes;

    public FavouriteClothesRecyclerAdapter(List<OldClothes> favouriteClothes) {
        this.favouriteClothes = favouriteClothes;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ConstraintLayout constraintLayout;
        private TextView nameText;
        private TextView typeText;
        private TextView priceText;
        private ImageButton deleteButton;
        private Button seeMoreButton;

        ViewHolder(@NonNull View view) {
            super(view);
            this.constraintLayout = (ConstraintLayout) view;
            this.nameText = view.findViewById(R.id.clothes_name);
            this.typeText = view.findViewById(R.id.clothes_type);
            this.priceText = view.findViewById(R.id.clothes_price);
            this.deleteButton = view.findViewById(R.id.delete_button);
            this.seeMoreButton = view.findViewById(R.id.see_more_button);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.favourite_clothes_card_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OldClothes chosenClothes = favouriteClothes.get(position);
        holder.nameText.setText(chosenClothes.getName());
        holder.typeText.setText(chosenClothes.getType());
        holder.priceText.setText(chosenClothes.getPrice().toString());
        holder.deleteButton.setId(chosenClothes.getId());
        holder.seeMoreButton.setId(chosenClothes.getId());
    }

    @Override
    public int getItemCount() {
        return favouriteClothes.size();
    }


}
