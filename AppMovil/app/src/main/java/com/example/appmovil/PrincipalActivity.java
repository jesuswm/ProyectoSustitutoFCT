package com.example.appmovil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class PrincipalActivity extends AppCompatActivity {
    TextView textNombre;
    TextView textEmail;
    String url="http://192.168.1.126:8080/RestProyect/rest/Usuarios/MiUsuario?token=";
    String urlPost="http://192.168.1.126:8080/RestProyect/rest/Posts/Propios?token=";
    Intent intent;
    String token;
    RecyclerView rvpost;
    RequestQueue requestQueue;
    ArrayList<Posts> posts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        textNombre=findViewById(R.id.prInfoNombre);
        textEmail=findViewById(R.id.prInfoEmail);
        rvpost=findViewById(R.id.posts_recycler_view);
        intent=getIntent();
        token=intent.getStringExtra("token");
        requestQueue= Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(
                Request.Method.GET,
                url + token,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        Usuarios user = gson.fromJson(response.toString(), Usuarios.class);
                        textNombre.setText(user.getNombre());
                        textEmail.setText(user.getEmail());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PrincipalActivity.this, "Error??", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        posts=new ArrayList<Posts>();
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(
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
                                AdaptadoPosts adaptadoPosts=new AdaptadoPosts(posts);
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

                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
        requestQueue.add(jsonArrayRequest);
//        Posts prueba=new Posts();
//        prueba.setContenido("<p style=\"text-align: center; \"><b>Prueba post privado</b></p><p style=\"text-align: left;\">Hola amigos</p>");
//        prueba.setFecha( new Date(System.currentTimeMillis()));
//        prueba.setId(1);
//        prueba.setIdCreador(1);
//        posts.add(prueba);




        //Toast.makeText(this, token, Toast.LENGTH_SHORT).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //prueba.setText(Html.fromHtml("<p style=\"text-align: center; \"><b>Prueba post público</b></p><p style=\"text-align: left;\">Hola mundo</p>", Html.FROM_HTML_MODE_COMPACT));
        } else {
            //prueba.setText(Html.fromHtml("<p style=\"text-align: center; \"><b>Prueba post público</b></p><p style=\"text-align: left;\">Hola mundo</p>"));
        }
    }
}
