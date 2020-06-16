package com.example.appmovil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class OtroUsuarioActivity extends AppCompatActivity {
    BottomNavigationView bottomNav;
    TextView textNombre;
    TextView textEmail;
    Button btnPeticion;
    String url=MyApplication.getUrlInfoOtroUsuario();
    String urlPost=MyApplication.getUrlPostsOtro();
    Intent intent;
    String nombre;
    private static String token;
    RecyclerView rvpost;
    RequestQueue requestQueue;
    ArrayList<Posts> posts;
    JsonArrayRequest jsonArrayRequest;
    int idUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otro_usuario);
        btnPeticion=findViewById(R.id.ObuttonPedirAmistad);
        bottomNav=findViewById(R.id.ObottonNav);
        bottomNav.setSelectedItemId(R.id.musuario);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        textNombre=findViewById(R.id.OprInfoNombre);
        textEmail=findViewById(R.id.OprInfoEmail);
        rvpost=findViewById(R.id.Oposts_recycler_view);
        intent=getIntent();
        idUser=intent.getIntExtra("id",-1);
        if(idUser==-1){
            intent=new Intent(OtroUsuarioActivity.this,PrincipalActivity.class);
        }
        token=MyApplication.getTokens();
        requestQueue= Volley.newRequestQueue(this);
        posts=new ArrayList<Posts>();
        jsonArrayRequest=new JsonArrayRequest(
                Request.Method.GET,
                urlPost + token+ "&idCreador=" +idUser,
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
                                AdaptadoPosts adaptadoPosts=new AdaptadoPosts(posts,nombre,false);
                                RecyclerView.LayoutManager miLayoutManager = new LinearLayoutManager(OtroUsuarioActivity.this, LinearLayoutManager.VERTICAL, false);
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
                        intent=new Intent(OtroUsuarioActivity.this, PrincipalActivity.class);
                        startActivity(intent);
                    }
                }
        );
        final StringRequest stringRequest=new StringRequest(
                Request.Method.GET,
                MyApplication.getUrlComprobarAmistad()+MyApplication.getTokens()+"&idBuscado="+idUser,
                new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        switch(response)
                        {
                            case "1":
                                btnPeticion.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(
                                                Request.Method.POST, MyApplication.getUrlCrearPeticiones()+MyApplication.getTokens()+"&idSolicitado=" +idUser, null,
                                                new Response.Listener<JSONObject>() {
                                                    @Override
                                                    public void onResponse(JSONObject jsonObject) {

                                                    }
                                                },
                                                new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError volleyError) {
                                                        Toast.makeText(OtroUsuarioActivity.this, "Mal rollo", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                        ) {

                                            @Override
                                            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {


                                                try {
                                                    String json = new String(
                                                            response.data,
                                                            "UTF-8"
                                                    );

                                                    if (json.length() == 0) {
                                                        return Response.success(
                                                                null,
                                                                HttpHeaderParser.parseCacheHeaders(response)
                                                        );
                                                    } else {
                                                        return super.parseNetworkResponse(response);
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    return Response.error(new ParseError(e));
                                                }
                                            }
                                        };
                                        requestQueue.add(jsonObjectRequest2);
                                        btnPeticion.setText(R.string.esperandoAmistad);
                                        btnPeticion.setEnabled(false);
                                    }
                                });
                                break;
                            case "2":
                                btnPeticion.setText(R.string.esperandoAmistad);
                                btnPeticion.setEnabled(false);
                                break;
                            case "3":
                                btnPeticion.setText(R.string.yaAmigos);
                                btnPeticion.setEnabled(false);
                                break;
                        }
                        requestQueue.add(jsonArrayRequest);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(
                Request.Method.GET,
                url + token+"&idBuscado=" +idUser,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        Usuarios user = gson.fromJson(response.toString(), Usuarios.class);
                        nombre=user.getNombre();
                        textNombre.setText(user.getNombre());
                        textEmail.setText(user.getEmail());
                        requestQueue.add(stringRequest);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OtroUsuarioActivity.this, "Error??", Toast.LENGTH_SHORT).show();
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
                    intent=new Intent(OtroUsuarioActivity.this, PrincipalActivity.class);
                    startActivity(intent);
                    break;
                case R.id.mamigos:
                    intent=new Intent(OtroUsuarioActivity.this, AmigosActivity.class);
                    startActivity(intent);
                    break;
                case R.id.mbuscar:
                    intent=new Intent(OtroUsuarioActivity.this, BusquedaActivity.class);
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
