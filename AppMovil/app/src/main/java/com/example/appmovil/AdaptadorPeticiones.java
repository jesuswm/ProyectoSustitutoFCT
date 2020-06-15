package com.example.appmovil;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AdaptadorPeticiones extends RecyclerView.Adapter<AdaptadorPeticiones.PeticionesViewHolder>{
    ArrayList<Peticiones> peticiones;
    public AdaptadorPeticiones(ArrayList<Peticiones> peticiones) {
        this.peticiones=peticiones;
    }
    public static class PeticionesViewHolder extends RecyclerView.ViewHolder {
        private TextView textoPeticion;
        private Button btnAceptar;
        private Button btnRechazar;
        RequestQueue requestQueue;
        private int id;
        private int idCreador;
        public PeticionesViewHolder(View viewElemento) {
            super(viewElemento);
            this.textoPeticion=viewElemento.findViewById(R.id.petiText);
            this.btnAceptar=viewElemento.findViewById(R.id.petiBtAceptar);
            this.btnRechazar=viewElemento.findViewById(R.id.petiBtRechazar);
        }
    }
    @NonNull
    @Override
    public PeticionesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View elemento = LayoutInflater.from(parent.getContext()).inflate(R.layout.contenedor_peticion,
                parent, false);
        PeticionesViewHolder mvh = new PeticionesViewHolder(elemento);
        return mvh ;
    }
    @Override
    public void onBindViewHolder(@NonNull final PeticionesViewHolder holder, final int position) {
        final Peticiones peticion=this.peticiones.get(position);
        String text = holder.textoPeticion.getResources().getString(R.string.textPeticion1)+" "+peticion.nombreSolicitante+" "+holder.textoPeticion.getResources().getString(R.string.textPeticion2);
        holder.textoPeticion.setText(text);
        holder.requestQueue= Volley.newRequestQueue(holder.itemView.getContext());
        holder.btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.btnAceptar.setClickable(false);
                holder.btnRechazar.setClickable(false);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.POST,MyApplication.getUrlContestarPeticiones()+MyApplication.getTokens()+"&idSolicitud="+peticion.getId()+"&aceptar=true", null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                ((AmigosActivity) holder.itemView.getContext()).cargarPeticiones();
                                ((AmigosActivity) holder.itemView.getContext()).cargarAmigos();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Toast.makeText(holder.itemView.getContext(), "Mal", Toast.LENGTH_SHORT).show();
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
                holder.requestQueue.add(jsonObjectRequest);
            }
        });
        holder.btnRechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(holder.btnRechazar.getContext(), "Rechazar peticion "+peticion.getId(), Toast.LENGTH_SHORT).show();
                holder.btnAceptar.setClickable(false);
                holder.btnRechazar.setClickable(false);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.POST,MyApplication.getUrlContestarPeticiones()+MyApplication.getTokens()+"&idSolicitud="+peticion.getId()+"&aceptar=false", null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                ((AmigosActivity) holder.itemView.getContext()).cargarPeticiones();
                                ((AmigosActivity) holder.itemView.getContext()).cargarAmigos();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Toast.makeText(holder.itemView.getContext(), "Mal", Toast.LENGTH_SHORT).show();
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
                holder.requestQueue.add(jsonObjectRequest);
            }
        });
    }
    @Override
    public int getItemCount() {
        return this.peticiones.size();
    }
}
