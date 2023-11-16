package com.example.api_class_app;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.api_class_app.fragments.ItemViewFragment;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataHolder {

    private static final DataHolder INSTANCE = new DataHolder();
    private List<Pokemon> pokemonList;

    private List<Items> itemsList;

    private Pokemon savedPokemon;

    private Items savedItem;

    private boolean itemLoaded, pokemonLoaded;

    private OnDataLoadedListener listener;

    public interface OnDataLoadedListener {
        void onDataLoaded();
    }

    public void setOnDataLoadedListener(OnDataLoadedListener listener) {
        this.listener = listener;
    }

    public void notifyDataLoaded(int i /*Para pokemon (0) para items (cualquiera)*/) {
        if(i == 0) pokemonLoaded = true;
        else itemLoaded = true;
        if (listener != null) {
            listener.onDataLoaded();
        }
    }

    private DataHolder() {
        pokemonList = new ArrayList<>();
        itemsList = new ArrayList<>();
        itemLoaded = pokemonLoaded = false;
    }

    public static DataHolder getInstance() {
        return INSTANCE;
    }

    public Pokemon getPokemon(String n){
        for (Pokemon pokemon : pokemonList) {
            if (pokemon.getName().equalsIgnoreCase(n)) {
                return pokemon;
            }
        }
        return null;
    }
    public List<Pokemon> getPokemonList(Context context) {
        if(pokemonList.isEmpty()){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(("https://pokeapi.co/api/v2/"))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            PokemonAPIService pokemonAPIService = retrofit.create(PokemonAPIService.class);

            pokemonAPIService.getAllPokemon().enqueue(new Callback<ApiResponse>() {

                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    List<Results> results = new ArrayList<>();
                    if (response.isSuccessful() && response.body() != null) {
                        results = response.body().getResults();
                        for (Results r : results) {
                            pokemonAPIService.getPokemonDetails(r.getUrl()).enqueue(new Callback<Pokemon>() {
                                @Override
                                public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        Pokemon p = response.body();
                                        pokemonList.add(p);
                                        if (!pokemonList.isEmpty()) {
                                            pokemonList.sort(Comparator.comparingInt(Pokemon::getID));
                                            pokemonLoaded = true;
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<Pokemon> call, Throwable t) {
                                    Toast.makeText(context, "Error al obtener detalles de Pokemon", Toast.LENGTH_SHORT).show();
                                    //Log.e("PokemonViewFragment", "Error al obtener detalles de Pokemon", t);
                                }
                            });
                        }
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable throwable) {
                    Toast.makeText(context, "Error al obtener la lista de Pokémon", Toast.LENGTH_SHORT).show();
                    //Log.e("PokemonViewFragment", "Error al obtener la lista de Pokémon", throwable);
                }
            });
        }
        return pokemonList;
    }

    public Items getItem(String n){
        for (Items i : itemsList) {
            if (i.getName().equalsIgnoreCase(n)) {
                return i;
            }
        }
        return null;
    }
    public List<Items> getItemsList(Context context) {
        if(itemsList.isEmpty()){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(("https://pokeapi.co/api/v2/"))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            PokemonAPIService pokemonAPIService = retrofit.create(PokemonAPIService.class);

            pokemonAPIService.getAllItems().enqueue(new Callback<ApiResponse>() {
                List<Results> results = new ArrayList<>();
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        results = response.body().getResults();
                        for (Results r : results) {
                            Log.d("ItemViewFragment", "Fetching details for URL: " + r.getUrl());

                            pokemonAPIService.getItemDetails(r.getUrl()).enqueue(new Callback<Items>() {
                                @Override
                                public void onResponse(Call<Items> call, Response<Items> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        Items i = response.body();
                                        itemsList.add(i);
                                        if (itemsList.size() == results.size()) {
                                            itemsList.sort((p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName()));
                                            itemLoaded = true;
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<Items> call, Throwable t) {
                                    Log.e("ItemViewFragment", "Failed to fetch item details", t);
                                    Toast.makeText(context, "Error al obtener detalles de Items", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable throwable) {
                    Toast.makeText(context, "Error al obtener la lista de Pokémon", Toast.LENGTH_SHORT).show();
                    //Log.e("PokemonViewFragment", "Error al obtener la lista de Pokémon", throwable);
                }
            });
        }
        return itemsList;
    }

    public Pokemon getSavedPokemon() {
        return savedPokemon;
    }

    public void setSavedPokemon(Pokemon savedPokemon) {
        this.savedPokemon = savedPokemon;
    }

    public Items getSavedItem() {
        return savedItem;
    }

    public void setSavedItem(Items savedItem) {
        this.savedItem = savedItem;
    }

    public boolean isItemLoaded() {
        return itemLoaded;
    }

    public boolean isPokemonLoaded() {
        return pokemonLoaded;
    }
}


