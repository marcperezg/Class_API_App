package com.example.api_class_app.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.api_class_app.ApiResponse;
import com.example.api_class_app.DataHolder;
import com.example.api_class_app.Pokemon;
import com.example.api_class_app.PokemonAPIService;
import com.example.api_class_app.PokemonViewAdapter;
import com.example.api_class_app.R;
import com.example.api_class_app.Results;
import com.example.api_class_app.SelectListener;
import com.example.api_class_app.ViewPokemon;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonViewFragment extends Fragment implements SelectListener {

    private RecyclerView recyclerView;

    private List<Pokemon> poke_list;

    private List<Results> results;

    public PokemonViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        Context context = view.getContext();
        recyclerView = view.findViewById(R.id.pokemonRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
        if(poke_list.isEmpty()) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(("https://pokeapi.co/api/v2/"))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            PokemonAPIService pokemonAPIService = retrofit.create(PokemonAPIService.class);

            pokemonAPIService.getAllPokemon().enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        results = response.body().getResults();
                        for (Results r : results) {
                            pokemonAPIService.getPokemonDetails(r.getUrl()).enqueue(new Callback<Pokemon>() {
                                @Override
                                public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        Pokemon p = response.body();
                                        poke_list.add(p);
                                        if (poke_list.size() == results.size()) { // Verifica si todos los detalles han sido agregados.
                                            // Actualiza el adaptador en el hilo de UI
                                            poke_list.sort(Comparator.comparingInt(Pokemon::getID));
                                            DataHolder.getInstance().setPokemonList(poke_list);
                                            setUpAdapter(context);
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
        } else setUpAdapter(view.getContext());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        poke_list = new ArrayList<>();
        results = new ArrayList<>();

        if(!DataHolder.getInstance().getPokemonList().isEmpty()) poke_list = DataHolder.getInstance().getPokemonList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pokemon_view, container, false);
    }

    @Override
    public void OnItemClicked(Context context, String name) {
        DataHolder.getInstance().setSavedFragment(0);
        Intent intent = new Intent(context, ViewPokemon.class);
        intent.putExtra("NAME", name);
        context.startActivity(intent);
    }

    private void setUpAdapter (Context context){
        PokemonViewAdapter pokemonViewAdapter = new PokemonViewAdapter(context, poke_list, PokemonViewFragment.this);
        recyclerView.setAdapter(pokemonViewAdapter);
    }
}