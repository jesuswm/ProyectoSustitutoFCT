package com.example.appmovil;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class RegisterActivity extends AppCompatActivity {
    Button buttonRegistrar;
    TextView textYaRegistrado;
    EditText imRegNombre;
    EditText imRegEmail;
    EditText imRegContraseña;
    EditText imRegConfContraseña;
    //String url="http://192.168.1.126:8080/RestProyect/rest/Usuarios/Crear";
    String url=MyApplication.getUrlRegistrar();
    Intent intent;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        requestQueue= Volley.newRequestQueue(this);
        buttonRegistrar = (Button) findViewById(R.id.butRegister);
        imRegNombre=findViewById(R.id.imRegNombre);
        imRegEmail=findViewById(R.id.imRegEmail);
        imRegContraseña=findViewById(R.id.imRegContraseña);
        imRegConfContraseña=findViewById(R.id.imRegConfContraseña);
        textYaRegistrado=findViewById(R.id.textYaRegistrado);
        textYaRegistrado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        buttonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valido=true;
                Usuarios user= new Usuarios() ;
                String error=getResources().getString(R.string.errorCamposVacios);
                if(imRegNombre.getText().toString().trim().length() > 0) {
                    user.setNombre(imRegNombre.getText().toString());
                }else{
                    valido = false;
                }
                if(imRegEmail.getText().toString().trim().length() > 0) {
                    user.setEmail(imRegEmail.getText().toString());
                }else{
                    valido=false;
                }
                if(imRegConfContraseña.getText().toString().trim().length() > 0) {
                    user.setPass(imRegContraseña.getText().toString());
                }else {
                    valido=false;
                }
                if(!imRegContraseña.getText().toString().equals(imRegConfContraseña.getText().toString())){
                    valido=false;
                    error=getResources().getString(R.string.confContraseña);
                }
                if(valido) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("nombre", user.getNombre());
                        jsonObject.put("email", user.getEmail());
                        jsonObject.put("pass", user.getPass());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                            Request.Method.POST, url, jsonObject,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject jsonObject) {
                                    intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    //Toast.makeText(RegisterActivity.this, "Error??", Toast.LENGTH_SHORT).show();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
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
}
