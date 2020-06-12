package com.example.appmovil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;


public class BusquedaActivity extends AppCompatActivity {

    private ArrayList<Usuarios> resultados;
    RequestQueue requestQueue;
    Button butBuscar;
    EditText impBusqueda;
    RecyclerView rvResultado;
    AdaptadorBusqueda adaptadorBusqueda;
    BottomNavigationView bottomNav;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda);
        bottomNav=findViewById(R.id.BbottonNav);
        bottomNav.setSelectedItemId(R.id.mbuscar);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        resultados=new ArrayList<Usuarios>();
        butBuscar=findViewById(R.id.busButBuscar);
        impBusqueda=findViewById(R.id.busImpBuscar);
        rvResultado=findViewById(R.id.busqueda_recycler_view);
        requestQueue=Volley.newRequestQueue(this);
        adaptadorBusqueda=new AdaptadorBusqueda(resultados);
        CustomLinearLayoutManager miLayoutManager = new CustomLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvResultado.setLayoutManager(miLayoutManager);
        rvResultado.setAdapter(adaptadorBusqueda);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, 1);
        rvResultado.addItemDecoration(dividerItemDecoration);
        butBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = BusquedaActivity.this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(BusquedaActivity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                if (!impBusqueda.getText().toString().trim().equals("")){
                    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                            Request.Method.GET,
                            MyApplication.getUrlBuscar() + MyApplication.getTokens() + "&busqueda=" + impBusqueda.getText().toString().trim(),
                            null,
                            new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    resultados = new ArrayList<Usuarios>();
                                    for (int i = 0; i < response.length(); i++) {
                                        try {
                                            JSONObject row = response.getJSONObject(i);
                                            Gson gson = new Gson();
                                            Usuarios use = gson.fromJson(row.toString(), Usuarios.class);
                                            resultados.add(use);
                                        } catch (JSONException e) {

                                        }
                                    }
                                    for (Usuarios re : resultados
                                    ) {
                                        Log.i("res", re.getNombre());
                                    }
                                    adaptadorBusqueda = new AdaptadorBusqueda(resultados);
                                    rvResultado.setAdapter(adaptadorBusqueda);
                                    //resultados.clear();
                                    //rvResultado.getAdapter().notifyDataSetChanged();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    resultados.clear();
                                    rvResultado.getAdapter().notifyDataSetChanged();
                                }
                            }
                    );
                    requestQueue.add(jsonArrayRequest);
                }
            }
        });
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.musuario:
                    intent = new Intent(BusquedaActivity.this, PrincipalActivity.class);
                    startActivity(intent);
                    break;
                case R.id.mamigos:
                    intent = new Intent(BusquedaActivity.this, AmigosActivity.class);
                    startActivity(intent);
                    break;
                case R.id.mbuscar:

                    break;
            }
            return true;
        }
    };

}
