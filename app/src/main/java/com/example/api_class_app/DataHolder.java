package com.example.api_class_app;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class DataHolder {
    private static final DataHolder INSTANCE = new DataHolder();
    private List<Pokemon> pokemonList;

    private List<Items> itemsList;

    private Pokemon savedPokemon;

    private Items savedItem;

    private int savedFragment;

    private DataHolder() {
        pokemonList = new ArrayList<>();
        itemsList = new ArrayList<>();
        savedFragment = 0;
    }

    public static DataHolder getInstance() {
        return INSTANCE;
    }

    public List<Pokemon> getPokemonList() {
        return pokemonList;
    }

    public void setPokemonList(List<Pokemon> pokemonList) {
        this.pokemonList = pokemonList;
    }

    public List<Items> getItemsList() {
        return itemsList;
    }

    public void setItemsList(List<Items> itemsList) {
        this.itemsList = itemsList;
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

    public int getSavedFragment() {
        return savedFragment;
    }

    public void setSavedFragment(int savedFragment) {
        this.savedFragment = savedFragment;
    }

    public Pokemon getPokemon(String n){
        for (Pokemon pokemon : pokemonList) {
            if (pokemon.getName().equalsIgnoreCase(n)) {
                return pokemon;
            }
        }
        return null;
    }
    public Items getItem(String n){
        for (Items i : itemsList) {
            if (i.getName().equalsIgnoreCase(n)) {
                return i;
            }
        }
        return null;
    }
}
