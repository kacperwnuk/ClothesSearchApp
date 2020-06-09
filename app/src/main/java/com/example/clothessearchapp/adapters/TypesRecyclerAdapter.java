package com.example.clothessearchapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clothessearchapp.R;
import com.example.clothessearchapp.structure.Type;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;


public class TypesRecyclerAdapter extends RecyclerView.Adapter<TypesRecyclerAdapter.MyViewHolder> {

    private List<Type> types;
    private static HashMap<String, Integer> bgImages = new HashMap<String, Integer>() {{
        put("T-SHIRT", R.drawable.tshirt);
        put("SHIRT", R.drawable.shirt);
        put("PANTS", R.drawable.pants);
        put("JACKET", R.drawable.jacket);
        put("SWEATER", R.drawable.sweater);
        put("SHORTS", R.drawable.shorts);

    }};


    public TypesRecyclerAdapter(List<Type> types) {
        this.types = types;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout constraintLayout;
        ImageView backgroundImage;
        TextView clothesType;

        MyViewHolder(View view) {
            super(view);
            this.constraintLayout = (ConstraintLayout) view;
            this.backgroundImage = view.findViewById(R.id.background_image);
            this.clothesType = view.findViewById(R.id.type_name);
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.type_card_view_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String typeName = types.get(position).getName();
        holder.clothesType.setText(Type.polishNames.getOrDefault(typeName, "Ubrania"));
        holder.clothesType.setTag(typeName);
        holder.constraintLayout.setElevation(40.0f);
        holder.backgroundImage.setImageResource(bgImages.getOrDefault(typeName, R.drawable.tshirt));
    }


    @Override
    public int getItemCount() {
        return types.size();
    }
}
