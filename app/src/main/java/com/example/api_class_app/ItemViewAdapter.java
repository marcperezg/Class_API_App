package com.example.api_class_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ItemViewAdapter extends RecyclerView.Adapter<ItemViewHolder>{

    private Context context;

    private List<Items> itemsList;

    private SelectListener listener;

    public ItemViewAdapter(Context context, List<Items> itemsList, SelectListener listener) {
        this.context = context;
        this.itemsList = itemsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_rv, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        final String name = itemsList.get(position).getName();

        holder.name.setText(itemsList.get(position).getName());
        holder.category.setText(String.valueOf(itemsList.get(position).getCategory().name));
        holder.effect.setText(String.valueOf(itemsList.get(position).getEffectEntry().get(0).short_effect));
        Glide.with(context).load(itemsList.get(position).getSprites().defaultSprite).into(holder.itemImg);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnItemClicked(v.getContext(), name);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }
}
