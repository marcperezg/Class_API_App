package com.example.api_class_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PokemonViewAdapter extends RecyclerView.Adapter<PokemonViewHolder>{
    private Context context;

    private List<Pokemon> pokeList;

    private SelectListener listener;

    public PokemonViewAdapter(Context context, List<Pokemon> pokeList, SelectListener listener) {
        this.context = context;
        this.pokeList = pokeList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PokemonViewHolder(LayoutInflater.from(context).inflate(R.layout.pokemon_rv, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        final String name = pokeList.get(position).getName();

        holder.name.setText(pokeList.get(position).getName().substring(0, 1).toUpperCase() + pokeList.get(position).getName().substring(1));
        holder.height.setText("Altura: " + String.valueOf(pokeList.get(position).getHeight()/10) + "m");
        holder.weight.setText("Peso: " + String.valueOf(pokeList.get(position).getWeight()/10) + "kg");
        Glide.with(context).load(pokeList.get(position).getSprites().front_default).into(holder.pokeImg);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnItemClicked(v.getContext(), name);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pokeList.size();
    }
}

