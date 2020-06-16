package com.example.appmovil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class ActualizarUsuarioActivity extends AppCompatActivity {
    Button buttonConfirmar;
    Button buttonCancelar;
    EditText imANombre;
    EditText imAEmail;
    EditText imAContraseña;
    EditText imAConfContraseña;
    EditText imAViejaConfContraseña;
    String url=MyApplication.getUrlModUsuario();
    BottomNavigationView bottomNav;
    Intent intent;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_usuario);

        bottomNav=findViewById(R.id.AUbottonNav);
        bottomNav.setSelectedItemId(R.id.musuario);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        requestQueue= Volley.newRequestQueue(this);
        buttonConfirmar = findViewById(R.id.AbtnActualizarUsuario);
        buttonCancelar = findViewById(R.id.AbtnCancelar);
        imANombre=findViewById(R.id.AUimNombre);
        imAEmail=findViewById(R.id.AUimEmail);
        imAContraseña=findViewById(R.id.AUimContraseña);
        imAConfContraseña=findViewById(R.id.AUimConfContraseña);
        imAViejaConfContraseña=findViewById(R.id.AUimOldContraseña);
        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(ActualizarUsuarioActivity.this, PrincipalActivity.class);
                startActivity(intent);
            }
        });

        buttonConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valido=true;
                Usuarios user= new Usuarios() ;
                String error=getResources().getString(R.string.errorCamposVacios);
                if(imANombre.getText().toString().trim().length() > 0) {
                    user.setNombre(imANombre.getText().toString());
                }
                if(imAEmail.getText().toString().trim().length() > 0) {
                    user.setEmail(imAEmail.getText().toString());
                }
                if(imAContraseña.getText().toString().trim().length() > 0) {
                    user.setPass(imAContraseña.getText().toString());
                }
                if(!imAConfContraseña.getText().toString().equals(imAContraseña.getText().toString())){
                    valido=false;
                    error=getResources().getString(R.string.confContraseña);

                }
                if(valido) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        if(user.getNombre()!=null) {
                            jsonObject.put("nombre", user.getNombre());
                        }
                        if(user.getEmail()!=null) {
                            jsonObject.put("email", user.getEmail());
                        }
                        if(user.getPass()!=null) {
                            jsonObject.put("pass", user.getPass());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                            Request.Method.POST, url+MyApplication.getTokens()+"&pass=" +imAViejaConfContraseña.getText().toString(), jsonObject,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject jsonObject) {
                                    intent = new Intent(ActualizarUsuarioActivity.this, PrincipalActivity.class);
                                    startActivity(intent);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    //Toast.makeText(RegisterActivity.this, "Error??", Toast.LENGTH_SHORT).show();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ActualizarUsuarioActivity.this);
                                    builder.setMessage(volleyError.networkResponse.headers.get("error"));
                                    builder.setTitle(R.string.tituloError);
                                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int id) {
                                        }
                                    });
                                    builder.show();
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
                    requestQueue.add(jsonObjectRequest);
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActualizarUsuarioActivity.this);
                    builder.setMessage(error);
                    builder.setTitle(R.string.tituloError);
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
                    builder.show();
                }
            }
        });
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.musuario:
                    intent=new Intent(ActualizarUsuarioActivity.this, PrincipalActivity.class);
                    startActivity(intent);
                    break;
                case R.id.mamigos:
                    intent=new Intent(ActualizarUsuarioActivity.this, AmigosActivity.class);
                    startActivity(intent);
                    break;
                case R.id.mbuscar:
                    intent=new Intent(ActualizarUsuarioActivity.this, BusquedaActivity.class);
                    startActivity(intent);
                    break;
            }
            return true;
        }
    };
}
