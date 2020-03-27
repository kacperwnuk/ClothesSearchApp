package com.example.clothessearchapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clothessearchapp.structure.Type;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class TypesRecyclerAdapter extends RecyclerView.Adapter<TypesRecyclerAdapter.MyViewHolder> {

    private List<Type> types;

    TypesRecyclerAdapter(List<Type> types) {
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
        View view = inflater.inflate(R.layout.type_card_view_item, parent,  false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.clothesType.setText(types.get(position).getName());
        holder.constraintLayout.setElevation(40.0f);

    }


    @Override
    public int getItemCount() {
        return types.size();
    }
}
