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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class MainActivity extends AppCompatActivity {

    public  String prueba;
    Button button;
    TextView textView;
    TextView textNoRegistrado;
    EditText imEmail;
    EditText imContraseña;
    String url="http://192.168.1.126:8080/RestProyect/rest/Usuarios/Autentificar";
    Intent intent;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue= Volley.newRequestQueue(this);
        button = (Button) findViewById(R.id.butEntar);
        textView = findViewById(R.id.textIniciar);
        imEmail=findViewById(R.id.imEmail);
        imContraseña=findViewById(R.id.imPass);
        textNoRegistrado=findViewById(R.id.textNoRegistrado);
        textNoRegistrado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(MainActivity.this, Principal.class);
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest=new StringRequest(
                        Request.Method.GET,
                        url+"?pass="+imContraseña.getText()+"&email="+imEmail.getText(),
                        new Response.Listener<String>(){

                            @Override
                            public void onResponse(String response) {
                                Log.e("Response",response);
                                prueba=response;
                                textView.setText(response);
                                intent=new Intent(MainActivity.this, Principal.class);
                                intent.putExtra("token",response);
                                startActivity(intent);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //Log.e("ErrorPeticion",error.networkResponse.headers.get("error"));
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setMessage(error.networkResponse.headers.get("error"));
                                builder.setTitle(R.string.tituloError);
                                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int id) {
                                    }
                                });
                                builder.show();
                            }
                        }
                );
                requestQueue.add(stringRequest);

            }
        });

    }
}
