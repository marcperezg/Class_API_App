package com.example.api_class_app.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.api_class_app.ApiResponse;
import com.example.api_class_app.ItemViewAdapter;
import com.example.api_class_app.Items;
import com.example.api_class_app.Pokemon;
import com.example.api_class_app.PokemonAPIService;
import com.example.api_class_app.PokemonViewAdapter;
import com.example.api_class_app.R;
import com.example.api_class_app.Results;
import com.example.api_class_app.SelectListener;
import com.example.api_class_app.ViewItem;
import com.example.api_class_app.ViewPokemon;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ItemViewFragment extends Fragment implements SelectListener {

    private RecyclerView recyclerView;

    private List<Items> item_list;

    private List<Results> results;

    public ItemViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        item_list = new ArrayList<>();
        results = new ArrayList<>();
        Context context = view.getContext();
        recyclerView = view.findViewById(R.id.itemRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(("https://pokeapi.co/api/v2/"))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PokemonAPIService pokemonAPIService = retrofit.create(PokemonAPIService.class);

        pokemonAPIService.getAllItems().enqueue(new Callback<ApiResponse>() {
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
                                    item_list.add(i);
                                    if (item_list.size() == results.size()) { // Verifica si todos los detalles han sido agregados.
                                        // Actualiza el adaptador en el hilo de UI
                                        getActivity().runOnUiThread(() -> {
                                            ItemViewAdapter itemViewAdapter = new ItemViewAdapter(context, item_list, ItemViewFragment.this);
                                            recyclerView.setAdapter(itemViewAdapter);
                                        });
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item_view, container, false);
    }

    @Override
    public void OnItemClicked(Context context, String name) {
        Intent intent = new Intent(context, ViewItem.class);
        intent.putExtra("NAME", name);
        context.startActivity(intent);
    }
}