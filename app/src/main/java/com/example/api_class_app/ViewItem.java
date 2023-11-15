package com.example.api_class_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewItem extends AppCompatActivity {

    ImageView normal_img;

    TextView name, cost, category, utilities;

    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);

        normal_img = (ImageView) findViewById(R.id.viewItem_img);

        name = (TextView) findViewById(R.id.viewItemName_txt);
        cost = (TextView) findViewById(R.id.viewItemCost_txt);
        category = (TextView) findViewById(R.id.viewItemCategory_txt);
        utilities = (TextView) findViewById(R.id.viewItemEffect_txt);

        back = (Button) findViewById(R.id.viewItemBack_btn);
        back.setOnClickListener(goBack);

        Intent intent = getIntent();
        name.setText(intent.getStringExtra("NAME"));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(("https://pokeapi.co/api/v2/"))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PokemonAPIService pokemonAPIService = retrofit.create(PokemonAPIService.class);

        pokemonAPIService.getItem(name.getText().toString()).enqueue(new Callback<Items>() {
            @Override
            public void onResponse(Call<Items> call, Response<Items> response) {
                Items i = response.body();
                Glide.with(getApplicationContext()).load(i.getSprites().defaultSprite).into(normal_img);
                cost.setText(String.valueOf(i.getCost()) + " Monedas");
                category.setText(i.getCategory().name.substring(0, 1).toUpperCase() + i.getCategory().name.substring(1));
                utilities.setText(i.getFlavor_text_entries().get(42).text);
            }

            @Override
            public void onFailure(Call<Items> call, Throwable throwable) {
                Toast.makeText(ViewItem.this, "Error al conectar amb API", Toast.LENGTH_SHORT).show();
            }
        });

    }

    protected View.OnClickListener goBack = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ViewItem.this, MainActivity.class);
            startActivity(intent);
        }
    };
}