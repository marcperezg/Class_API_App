package com.example.api_class_app;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ItemViewHolder extends RecyclerView.ViewHolder{

    private ImageView itemImg;

    private TextView name, effect;

    private CardView cardView;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemImg = itemView.findViewById(R.id.item_img);
        this.name = itemView.findViewById(R.id.item_name);
        this.effect = itemView.findViewById(R.id.item_effect);
        this.cardView = itemView.findViewById(R.id.main_containerI);
    }
}
