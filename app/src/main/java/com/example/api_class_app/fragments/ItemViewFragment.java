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
import com.example.api_class_app.DataHolder;
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
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ItemViewFragment extends Fragment implements SelectListener {

    private RecyclerView recyclerView;

    private List<Items> item_list;

    ItemViewAdapter itemViewAdapter;

    public ItemViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {


        recyclerView = view.findViewById(R.id.itemRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 1));
        recyclerView.setAdapter(itemViewAdapter);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        item_list = new ArrayList<>();
        Context context = getContext();
        item_list = DataHolder.getInstance().getItemsList(context);
        if(!item_list.isEmpty()){
            DataHolder.getInstance().notifyDataLoaded(5);
            getActivity().runOnUiThread(() -> {
                itemViewAdapter = new ItemViewAdapter(context, item_list, ItemViewFragment.this);
            });
        }
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