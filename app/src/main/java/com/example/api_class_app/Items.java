package com.example.api_class_app;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Items {

    private String name;

    private int cost;

    private Category category;

    private List<EffectEntry> effect_entries;

    private List<FlavorTextEntries> flavor_text_entries;

    private Sprites sprites; // 42

    public class EffectEntry {
        public String effect;
        public Language language;
        public String short_effect;

    }

    public class Category {
        String name;
        String url;
    }

    public class Language {
        private String name;
        private String url;
    }

    public class FlavorTextEntries {

        //Language language;

        String text;

    }

    public class Sprites {
        @SerializedName("default")
        public String defaultSprite;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public List<EffectEntry> getEffectEntry() {
        return effect_entries;
    }

    public List<FlavorTextEntries> getFlavor_text_entries() {
        return flavor_text_entries;
    }

    public Sprites getSprites() { return sprites; }

    public Category getCategory() { return category; }
}
