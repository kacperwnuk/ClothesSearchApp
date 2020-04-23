package com.example.clothessearchapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clothessearchapp.R;
import com.example.clothessearchapp.structure.Occasion;

import java.util.List;

public class OccasionRecyclerAdapter extends RecyclerView.Adapter<OccasionRecyclerAdapter.ViewHolder> {

    private List<Occasion> favouriteClothes;

    public OccasionRecyclerAdapter(List<Occasion> favouriteClothes) {
        this.favouriteClothes = favouriteClothes;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ConstraintLayout constraintLayout;
        private TextView typeText;
        private TextView sizeText;
        private TextView priceText;
        private TextView colorText;
        private ImageButton deleteButton;

        ViewHolder(@NonNull View view) {
            super(view);
            this.constraintLayout = (ConstraintLayout) view;
            this.sizeText = view.findViewById(R.id.clothes_size);
            this.typeText = view.findViewById(R.id.clothes_type);
            this.priceText = view.findViewById(R.id.clothes_price);
            this.colorText = view.findViewById(R.id.clothes_color);
            this.deleteButton = view.findViewById(R.id.delete_button);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.occassion_card_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Occasion occasion = favouriteClothes.get(position);
        holder.sizeText.setText(occasion.getSize());
        holder.typeText.setText(occasion.getType());
        holder.colorText.setText(occasion.getColor());
        holder.priceText.setText(occasion.getPrice());
        holder.deleteButton.setTag(occasion.getKey());
        holder.constraintLayout.setTag(occasion.getKey());
    }

    @Override
    public int getItemCount() {
        return favouriteClothes.size();
    }


}
