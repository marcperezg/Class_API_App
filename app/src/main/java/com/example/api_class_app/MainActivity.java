package com.example.api_class_app;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.api_class_app.fragments.PokemonViewFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity{


    EditText searchInp;

    Button searchBtn, showPoke, showItems;

    Fragment[] fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragments = new Fragment[2];
        fragments[0] = new PokemonViewFragment();
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragmentContainerView, fragments[0]);
        //fragmentTransaction.commit();


        //Button b = (Button) findViewById(R.id.call_api_btn);

        //imageView = findViewById(R.id.poke_img);

        //textView = findViewById(R.id.poke_name);
        /*
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(("https://pokeapi.co/api/v2/"))
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                PokemonAPIService pokemonAPIService = retrofit.create(PokemonAPIService.class);

                pokemonAPIService.getPokemon("ditto").enqueue(new Callback<Pokemon>() {
                    @Override
                    public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                        Pokemon p = response.body();
                        Glide.with(getApplicationContext()).load(p.sprites.front_default).into(imageView);
                        textView.setText(p.name);
                    }

                    @Override
                    public void onFailure(Call<Pokemon> call, Throwable throwable) {
                        Toast.makeText(MainActivity.this, "No va", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
         */
    }
}