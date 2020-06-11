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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PrincipalActivity extends AppCompatActivity {
    BottomNavigationView bottomNav;
    TextView textNombre;
    TextView textEmail;
    Button btnCerrarSesion;
    //String url="http://192.168.1.126:8080/RestProyect/rest/Usuarios/MiUsuario?token=";
    //String urlPost="http://192.168.1.126:8080/RestProyect/rest/Posts/Propios?token=";
    String url=MyApplication.getUrlInfoUsuario();
    String urlPost=MyApplication.getUrlPostsPropios();
    Intent intent;
    String nombre;
    private static String token;
    RecyclerView rvpost;
    RequestQueue requestQueue;
    ArrayList<Posts> posts;
    JsonArrayRequest jsonArrayRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        btnCerrarSesion=findViewById(R.id.buttonCerrarSesion);
        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.setTokens(null);
                intent=new Intent(PrincipalActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        bottomNav=findViewById(R.id.PbottonNav);
        bottomNav.setSelectedItemId(R.id.musuario);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        textNombre=findViewById(R.id.prInfoNombre);
        textEmail=findViewById(R.id.prInfoEmail);
        rvpost=findViewById(R.id.posts_recycler_view);
        intent=getIntent();
        //token=intent.getStringExtra("token");
        token=MyApplication.getTokens();
        requestQueue= Volley.newRequestQueue(this);
        posts=new ArrayList<Posts>();
        jsonArrayRequest=new JsonArrayRequest(
                Request.Method.GET,
                urlPost + token,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject row = response.getJSONObject(i);
                                Gson gson = new Gson();
                                Posts post = gson.fromJson(row.toString(), Posts.class);
                                posts.add(post);
                                AdaptadoPosts adaptadoPosts=new AdaptadoPosts(posts,nombre);
                                RecyclerView.LayoutManager miLayoutManager = new LinearLayoutManager(PrincipalActivity.this, LinearLayoutManager.VERTICAL, false);

                                rvpost.setLayoutManager(miLayoutManager);
                                rvpost.setAdapter(adaptadoPosts);
                                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvpost.getContext(), 1);
                                rvpost.addItemDecoration(dividerItemDecoration);
                            } catch (JSONException e) {

                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        intent=new Intent(PrincipalActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }
        );
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(
                Request.Method.GET,
                url + token,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        Usuarios user = gson.fromJson(response.toString(), Usuarios.class);
                        nombre=user.getNombre();
                        textNombre.setText(user.getNombre());
                        textEmail.setText(user.getEmail());
                        requestQueue.add(jsonArrayRequest);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PrincipalActivity.this, "Error??", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.musuario:

                    break;
                case R.id.mamigos:
                    Log.i("pruebaBott","amigo");
                    break;
                case R.id.mbuscar:
                    intent=new Intent(PrincipalActivity.this, BusquedaActivity.class);
                    startActivity(intent);
                    break;
            }
            return true;
        }
    };
    public static String getToken(){
        return token;
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Desabilitado", Toast.LENGTH_SHORT).show();
    }
}
