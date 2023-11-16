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
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonViewFragment extends Fragment implements SelectListener {

    private RecyclerView recyclerView;

    private List<Pokemon> poke_list;

    private PokemonViewAdapter pokemonViewAdapter;

    public PokemonViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        recyclerView = view.findViewById(R.id.pokemonRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 1));
        recyclerView.setAdapter(pokemonViewAdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        poke_list = new ArrayList<>();
        Context context = getContext();
        poke_list = DataHolder.getInstance().getPokemonList(context);
        if (!poke_list.isEmpty()) { // Verifica si todos los detalles han sido agregados.
            DataHolder.getInstance().notifyDataLoaded(0);
            getActivity().runOnUiThread(() -> {
                pokemonViewAdapter = new PokemonViewAdapter(context, poke_list, PokemonViewFragment.this);
            });
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pokemon_view, container, false);
    }

    @Override
    public void OnItemClicked(Context context, String name) {
        Intent intent = new Intent(context, ViewPokemon.class);
        intent.putExtra("NAME", name);
        context.startActivity(intent);
    }
}