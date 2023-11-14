package com.example.api_class_app;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class PokemonViewHolder extends RecyclerView.ViewHolder{

    ImageView pokeImg;

    TextView name, weight, height;

    CardView cardView;

    public PokemonViewHolder(@NonNull View itemView) {
        super(itemView);
        this.pokeImg = itemView.findViewById(R.id.poke_img);
        this.name = itemView.findViewById(R.id.poke_name);
        this.weight = itemView.findViewById(R.id.poke_weight);
        this.height = itemView.findViewById(R.id.poke_height);
        this.cardView = itemView.findViewById(R.id.main_containerP);
    }
}
