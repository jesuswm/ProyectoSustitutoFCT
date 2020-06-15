package com.example.appmovil;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdaptadoPosts extends RecyclerView.Adapter<AdaptadoPosts.MyViewHolder> {
    ArrayList<Posts> posts;
    String autor;
    boolean propio;
    public AdaptadoPosts(ArrayList<Posts> posts, String autor,boolean proppio) {
       this.posts=posts;
       this.autor=autor;
       this.propio=proppio;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView fecha;
        private TextView contenido;
        private TextView creadortext;
        private TextView texComent;
        private RecyclerView rvComentarios;
        private ArrayList<Comentarios> comentarios;
        private Button btComentar;
        private int id;
        private int idCreador;
        RequestQueue requestQueue;
        public MyViewHolder(View viewElemento) {
            super(viewElemento);
            this.fecha=viewElemento.findViewById(R.id.postFecha);
            this.contenido=viewElemento.findViewById(R.id.postContenido);
            this.creadortext=viewElemento.findViewById(R.id.postCreador);
            this.rvComentarios=viewElemento.findViewById(R.id.comentarios_recycler_view);
            this.texComent=viewElemento.findViewById(R.id.postTComentarios);
            this.btComentar=viewElemento.findViewById(R.id.postButComentar);
            this.comentarios=new ArrayList<Comentarios>();
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View elemento = LayoutInflater.from(parent.getContext()).inflate(R.layout.contenedor_post,
                parent, false);
        MyViewHolder mvh = new MyViewHolder(elemento);
        return mvh ;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        Posts post=this.posts.get(position);
        if(propio){
            holder.btComentar.setVisibility(View.GONE);
        }else{
            holder.btComentar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(holder.contenido.getContext(), "Comentar", Toast.LENGTH_SHORT).show();
                }
            });
        }
        holder.requestQueue= Volley.newRequestQueue(holder.itemView.getContext());
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String fechapost = formatter.format(post.getFecha());
        holder.fecha.setText("Fecha post: "+fechapost);
        holder.creadortext.setText("Autor: "+autor);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.contenido.setText(Html.fromHtml(post.getContenido(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.contenido.setText(Html.fromHtml(post.getContenido()));
        }
        holder.idCreador=post.getIdCreador();
        holder.id=post.getId();
        AdaptadorComentarios adaptadorComentarios=new AdaptadorComentarios(holder.comentarios);
        CustomLinearLayoutManager miLayoutManager = new CustomLinearLayoutManager(holder.itemView.getContext(), LinearLayoutManager.VERTICAL, false);
        holder.rvComentarios.setLayoutManager(miLayoutManager);
        holder.rvComentarios.setAdapter(adaptadorComentarios);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(holder.rvComentarios.getContext(), 1);
        holder.rvComentarios.addItemDecoration(dividerItemDecoration);
        holder.texComent.setVisibility(View.GONE);
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(
                Request.Method.GET,
                //"http://192.168.1.126:8080/RestProyect/rest/Comentarios?token=" + PrincipalActivity.getToken()+"&idPost="+post.getId(),
                MyApplication.getUrlComentarios() + PrincipalActivity.getToken()+"&idPost="+post.getId(),
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject row = response.getJSONObject(i);
                                holder.texComent.setVisibility(View.VISIBLE);
                                Gson gson = new Gson();
                                Comentarios comentario = gson.fromJson(row.toString(), Comentarios.class);
                                holder.comentarios.add(comentario);
                                holder.rvComentarios.getAdapter().notifyDataSetChanged();
                                Log.e("prueba","Dentro");
                            } catch (JSONException e) {

                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.e("prueba","No funciono");
                    }
                }
        );
        holder.requestQueue.add(jsonArrayRequest);
                //((PrincipalActivity)holder.rvComentarios.getContext()).requestQueue.add(jsonArrayRequest);
    }
    @Override
    public int getItemCount() {
        return this.posts.size();
    }
}

