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
import android.widget.ImageButton;
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

    Button showPoke, showItems;

    ImageButton searchBtn;

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

        searchInp = findViewById(R.id.search_inp);

        searchBtn = findViewById(R.id.search_btn);
        searchBtn.setOnClickListener(buttonAction);
    }

    protected View.OnClickListener buttonAction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if(id == searchBtn.getId()){
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(("https://pokeapi.co/api/v2/"))
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                PokemonAPIService pokemonAPIService = retrofit.create(PokemonAPIService.class);

                String url = "https://pokeapi.co/api/v2/pokemon/" + searchInp.getText().toString().toLowerCase();
                pokemonAPIService.getPokemonDetails(url).enqueue(new Callback<Pokemon>() {
                    @Override
                    public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Pokemon p = response.body();
                            Intent intent = new Intent(MainActivity.this, ViewPokemon.class);
                            intent.putExtra("NAME", searchInp.getText().toString());
                            startActivity(intent);
                        } else {
                            String iturl = "https://pokeapi.co/api/v2/item/" + searchInp.getText().toString().toLowerCase();
                            pokemonAPIService.getItemDetails(iturl).enqueue(new Callback<Items>() {
                                @Override
                                public void onResponse(Call<Items> call, Response<Items> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        Items i = response.body();
                                        Intent intent = new Intent(MainActivity.this, ViewPokemon.class);
                                        intent.putExtra("NAME", searchInp.getText().toString());
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(MainActivity.this, "No existe", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Items> call, Throwable throwable) {
                                    Toast.makeText(MainActivity.this, "No encontrado", Toast.LENGTH_SHORT).show();
                                    //Log.e("PokemonViewFragment", "Error al obtener la lista de Pokémon", throwable);
                                }
                            });
                            Toast.makeText(MainActivity.this, "No existe", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Pokemon> call, Throwable throwable) {
                        Toast.makeText(MainActivity.this, "No encontrado", Toast.LENGTH_SHORT).show();
                        //Log.e("PokemonViewFragment", "Error al obtener la lista de Pokémon", throwable);
                    }
                });
            }
            /* else if (id == showItems.getId()) {

            } else {

            }
            */
        }


    };
}