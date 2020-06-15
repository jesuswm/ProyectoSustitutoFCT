package com.example.appmovil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AmigosActivity extends AppCompatActivity {

    TextView txtAmigos;
    TextView txtPeticiones;
    RecyclerView rvAmigos;
    RecyclerView rvPeticiones;
    View viewDecAmigos;
    View viewDecPeticiones;
    RequestQueue requestQueue;
    BottomNavigationView bottomNav;
    Intent intent;
    String urlAmigos;
    String urlPeticiones;
    private ArrayList<Usuarios> amigos;
    private ArrayList<Peticiones> peticiones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amigos);
        amigos=new ArrayList<>();
        peticiones=new ArrayList<>();
        requestQueue= Volley.newRequestQueue(this);
        urlAmigos=MyApplication.getUrlAmigos();
        urlPeticiones=MyApplication.getUrlPeticiones();
        txtAmigos = findViewById(R.id.TextDecAmigos);
        txtPeticiones = findViewById(R.id.TextDecPeticiones);
        rvAmigos = findViewById(R.id.amigos_recycler_view);
        rvPeticiones = findViewById(R.id.peticiones_recycler_view);
        viewDecAmigos = findViewById(R.id.DecoracionA);
        viewDecPeticiones = findViewById(R.id.decoracionP);
        bottomNav=findViewById(R.id.AbottonNav);
        bottomNav.setSelectedItemId(R.id.mamigos);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        rvPeticiones.setVisibility(View.GONE);
        txtPeticiones.setVisibility(View.GONE);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvPeticiones.getContext(), 1);
        rvPeticiones.addItemDecoration(dividerItemDecoration);
        DividerItemDecoration dividerItemDecorationA = new DividerItemDecoration(rvAmigos.getContext(), 1);
        rvAmigos.addItemDecoration(dividerItemDecorationA);
        cargarAmigos();
        cargarPeticiones();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.musuario:
                    intent=new Intent(AmigosActivity.this, PrincipalActivity.class);
                    startActivity(intent);
                    break;
                case R.id.mamigos:

                    break;
                case R.id.mbuscar:
                    intent=new Intent(AmigosActivity.this, BusquedaActivity.class);
                    startActivity(intent);
                    break;
            }
            return true;
        }
    };
    public void cargarAmigos(){
        amigos=new ArrayList<>();
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(
                Request.Method.GET,
                urlAmigos + MyApplication.getTokens(),
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response.length()==0){
                            txtAmigos.setText(R.string.noAmigos);
                            rvAmigos.setVisibility(View.GONE);

                            //viewDecAmigos.setVisibility(View.GONE);
                        }else{
                            txtAmigos.setText(R.string.Amigos);
                            rvAmigos.setVisibility(View.VISIBLE);

                        }
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject row = response.getJSONObject(i);
                                Gson gson = new Gson();
                                Usuarios amigo = gson.fromJson(row.toString(), Usuarios.class);
                                amigos.add(amigo);

                            } catch (JSONException e) {

                            }
                        }
                        AdaptadorBusqueda adaptadoAmigos=new AdaptadorBusqueda(amigos);
                        RecyclerView.LayoutManager miLayoutManager = new LinearLayoutManager(AmigosActivity.this, LinearLayoutManager.VERTICAL, false);

                        rvAmigos.setLayoutManager(miLayoutManager);
                        rvAmigos.setAdapter(adaptadoAmigos);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        txtAmigos.setText(R.string.noAmigos);
                        rvAmigos.setVisibility(View.GONE);
                        rvPeticiones.setVisibility(View.VISIBLE);
                        //viewDecAmigos.setVisibility(View.GONE);
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }
    public void cargarPeticiones(){
        peticiones=new ArrayList<>();
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(
                Request.Method.GET,
                urlPeticiones + MyApplication.getTokens(),
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response.length()==0){
                            txtPeticiones.setVisibility(View.GONE);
                            rvPeticiones.setVisibility(View.GONE);
                            AdaptadorPeticiones adaptadorPeticiones=new AdaptadorPeticiones(peticiones);
                            RecyclerView.LayoutManager miLayoutManager = new LinearLayoutManager(AmigosActivity.this, LinearLayoutManager.VERTICAL, false);
                            rvPeticiones.setLayoutManager(miLayoutManager);
                            rvPeticiones.setAdapter(adaptadorPeticiones);

                            //viewDecAmigos.setVisibility(View.GONE);
                        }else{
                            txtPeticiones.setVisibility(View.VISIBLE);
                            rvPeticiones.setVisibility(View.VISIBLE);
                        }
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject row = response.getJSONObject(i);
                                Gson gson = new Gson();
                                Peticiones peticion = gson.fromJson(row.toString(), Peticiones.class);
                                peticiones.add(peticion);

                            } catch (JSONException e) {

                            }
                        }
                        AdaptadorPeticiones adaptadorPeticiones=new AdaptadorPeticiones(peticiones);
                        RecyclerView.LayoutManager miLayoutManager = new LinearLayoutManager(AmigosActivity.this, LinearLayoutManager.VERTICAL, false);
                        rvPeticiones.setLayoutManager(miLayoutManager);
                        rvPeticiones.setAdapter(adaptadorPeticiones);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        txtPeticiones.setVisibility(View.GONE);
                        rvPeticiones.setVisibility(View.GONE);
                        AdaptadorPeticiones adaptadorPeticiones=new AdaptadorPeticiones(peticiones);
                        RecyclerView.LayoutManager miLayoutManager = new LinearLayoutManager(AmigosActivity.this, LinearLayoutManager.VERTICAL, false);
                        rvPeticiones.setLayoutManager(miLayoutManager);
                        rvPeticiones.setAdapter(adaptadorPeticiones);
                        //viewDecAmigos.setVisibility(View.GONE);
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }
    public void onBackPressed() {
        Toast.makeText(this, "Desabilitado", Toast.LENGTH_SHORT).show();
    }

}
