package com.example.api_class_app;

import java.util.ArrayList;
import java.util.List;

public class Pokemon {
    private String name;

    private float height;

    private float weight;

    private int base_experience;

    private Sprites sprites;

    private List<TypesData> types;

    private List<AbilityData> abilities;

    public class Sprites {
        public String front_default;

        public String front_shiny;
    }
    public class Type {
        public String name;
        public String url;
    }

    public class TypesData {
        public Type type;
        public int slot;
    }

    public class Ability {
        public String name;
        public String url;
    }

    public class AbilityData {
        public Ability ability;
        public boolean is_hidden;
        public int slot;
    }

    public String getName() {
        return name;
    }

    public float getHeight() {
        return height;
    }

    public float getWeight() {
        return weight;
    }

    public int getBase_experience() {
        return base_experience;
    }

    public Sprites getSprites() { return sprites; }
    public List<TypesData> getTypes() { return types; }
    public List<AbilityData> getAbilities() {
        return abilities;
    }
}