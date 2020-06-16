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
import android.widget.LinearLayout;
import android.widget.RadioButton;
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
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class PublicarActivity extends AppCompatActivity {
    boolean privado=false;
    boolean post;
    Button buttonPublicar;
    Button buttonCancelar;
    TextView text;
    LinearLayout layoutPrivacidad;
    String urlPublicarPost=MyApplication.getUrlPublicarPost();
    String urlPublicarComentario=MyApplication.getUrlPublicarComentario();
    int idPost;
    int idPropietarioPost;
    EditText textArea;
    BottomNavigationView bottomNav;
    Intent intent;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicar);
        text=findViewById(R.id.textPublicar);
        buttonCancelar=findViewById(R.id.PUbtnCancelar);
        buttonPublicar=findViewById(R.id.PUbtnPublicar);
        layoutPrivacidad=findViewById(R.id.PrivOPublico);
        bottomNav=findViewById(R.id.PUbottonNav);
        bottomNav.setSelectedItemId(R.id.musuario);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        textArea=findViewById(R.id.textArea);
        intent=getIntent();
        requestQueue= Volley.newRequestQueue(this);
        post=intent.getBooleanExtra("post",false);
        idPost=intent.getIntExtra("idpost",-1);
        idPropietarioPost=intent.getIntExtra("idpropietario",-1);
        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(post) {
                    intent = new Intent(PublicarActivity.this, PrincipalActivity.class);
                    startActivity(intent);
                }else {
                    intent = new Intent(PublicarActivity.this, OtroUsuarioActivity.class);
                    intent.putExtra("id",idPropietarioPost);
                    startActivity(intent);
                }
            }
        });
        if(post){
            text.setText(R.string.publicarPost);
            buttonPublicar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean valido=true;
                    Posts post= new Posts() ;
                    String error=getResources().getString(R.string.errorCamposVacios);
                    if(textArea.getText().toString().trim().length() > 0) {
                        post.setContenido(textArea.getText().toString());
                    }else{
                        valido=false;
                    }
                    post.setPrivado(privado);
                    if(valido) {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            if(post.isPrivado()){
                                jsonObject.put("privado", "true");
                            }else{
                                jsonObject.put("privado", "false");
                            }
                            jsonObject.put("contenido", textArea.getText().toString().trim());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                                Request.Method.POST, urlPublicarPost+MyApplication.getTokens(), jsonObject,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject jsonObject) {
                                        intent = new Intent(PublicarActivity.this, PrincipalActivity.class);
                                        startActivity(intent);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError volleyError) {
                                        //Toast.makeText(RegisterActivity.this, "Error??", Toast.LENGTH_SHORT).show();
                                        AlertDialog.Builder builder = new AlertDialog.Builder(PublicarActivity.this);
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(PublicarActivity.this);
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
        }else {
            text.setText(R.string.publicarComentario);
            layoutPrivacidad.setVisibility(View.GONE);
            buttonPublicar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean valido=true;
                    Comentarios comentario= new Comentarios() ;
                    String error=getResources().getString(R.string.errorCamposVacios);
                    if(textArea.getText().toString().trim().length() > 0) {
                        comentario.setContenido(textArea.getText().toString());
                    }else{
                        valido=false;
                    }
                    comentario.setIdPost(idPost);
                    if(valido) {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("contenido", textArea.getText().toString().trim());
                            jsonObject.put("idPost", idPost);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                                Request.Method.POST, urlPublicarComentario+MyApplication.getTokens(), jsonObject,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject jsonObject) {
                                        intent = new Intent(PublicarActivity.this, OtroUsuarioActivity.class);
                                        intent.putExtra("id",idPropietarioPost);
                                        startActivity(intent);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError volleyError) {
                                        //Toast.makeText(RegisterActivity.this, "Error??", Toast.LENGTH_SHORT).show();
                                        AlertDialog.Builder builder = new AlertDialog.Builder(PublicarActivity.this);
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(PublicarActivity.this);
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
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.radio_privado:
                if (checked)

                    privado=true;
                    break;
            case R.id.radio_publico:
                if (checked)
                    privado=false;
                    break;
        }
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.musuario:
                    intent=new Intent(PublicarActivity.this, PrincipalActivity.class);
                    startActivity(intent);
                    break;
                case R.id.mamigos:
                    intent=new Intent(PublicarActivity.this, AmigosActivity.class);
                    startActivity(intent);
                    break;
                case R.id.mbuscar:
                    intent=new Intent(PublicarActivity.this, BusquedaActivity.class);
                    startActivity(intent);
                    break;
            }
            return true;
        }
    };

}
