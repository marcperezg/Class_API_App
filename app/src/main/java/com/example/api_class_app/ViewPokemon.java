package com.example.api_class_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewPokemon extends AppCompatActivity {

    ImageView normal_img, shiny_img;

    TextView name, weight, height, types, exp, abilities;

    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pokemon);

        normal_img = (ImageView) findViewById(R.id.normalPoke_img);
        shiny_img = (ImageView) findViewById(R.id.shinyPoke_img);

        name = (TextView) findViewById(R.id.viewPokeName_txt);
        weight = (TextView) findViewById(R.id.viewPokeWeight_txt);
        height = (TextView) findViewById(R.id.viewPokeHeight_txt);
        types = (TextView) findViewById(R.id.viewPokeTypes_txt);
        exp = (TextView) findViewById(R.id.viewPokeExp_txt);
        abilities = (TextView) findViewById(R.id.viewPokeAbilities_txt);

        back = (Button) findViewById(R.id.viewPokeBack_btn);
        back.setOnClickListener(goBack);

        Intent intent = getIntent();
        String aux = intent.getStringExtra("NAME");
        name.setText(aux.substring(0, 1).toUpperCase() + aux.substring(1));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(("https://pokeapi.co/api/v2/"))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PokemonAPIService pokemonAPIService = retrofit.create(PokemonAPIService.class);

        pokemonAPIService.getPokemon(aux).enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                Pokemon p = response.body();
                Glide.with(getApplicationContext()).load(p.getSprites().front_default).into(normal_img);
                Glide.with(getApplicationContext()).load(p.getSprites().front_shiny).into(shiny_img);
                weight.setText(String.valueOf(p.getWeight()/10) + "kg");
                height.setText(String.valueOf(p.getHeight()/10) + "m");

                String aux = p.getTypes().get(0).type.name.substring(0, 1).toUpperCase() + p.getTypes().get(0).type.name.substring(1);
                for(int i = 1; i<p.getTypes().size(); i++){
                    aux = aux + ", " + p.getTypes().get(i).type.name.substring(0, 1).toUpperCase() + p.getTypes().get(i).type.name.substring(1);
                }
                types.setText(aux);

                exp.setText(String.valueOf(p.getBase_experience()) + "xp");

                aux = p.getAbilities().get(0).ability.name.substring(0, 1).toUpperCase() + p.getAbilities().get(0).ability.name.substring(1);
                for(int i = 1; i<p.getAbilities().size(); i++){
                    aux = aux + ", " + p.getAbilities().get(i).ability.name.substring(0, 1).toUpperCase() + p.getAbilities().get(i).ability.name.substring(1);;
                }
                abilities.setText(aux);

            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable throwable) {
                Toast.makeText(ViewPokemon.this, "Error al conectar amb API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected View.OnClickListener goBack = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ViewPokemon.this, MainActivity.class);
            startActivity(intent);
        }
    };
}