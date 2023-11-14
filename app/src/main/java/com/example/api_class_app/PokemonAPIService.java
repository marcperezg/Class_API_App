package com.example.api_class_app;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface PokemonAPIService {
    @GET("pokemon/")
    Call<ApiResponse> getAllPokemon();

    @GET("pokemon/{p_name}")
    Call<Pokemon> getPokemon(@Path("p_name") String name);

    @GET
    Call<Pokemon> getPokemonDetails(@Url String url);

    @GET("item/")
    Call<List<Results>> getAllItems();

    /*
    @GET("item/{t_name}")
    Call<Items> getItem(@Path("t_name") String name);
    */

    // Exemple
    @GET("pokemon/ditto")
    Call<Pokemon> getPokemonDitto();
}