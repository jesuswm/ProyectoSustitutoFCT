package com.example.appmovil;

import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AdaptadoPosts extends RecyclerView.Adapter<AdaptadoPosts.MyViewHolder> {
    ArrayList<Posts> posts;
    // Constructor
    public AdaptadoPosts(ArrayList<Posts> posts) {
       this.posts=posts;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // Elementos que queremos mostrar en el RecyclerView, normalmente se corresponderán
        // con los definidos en el layout
        private TextView fecha;
        private TextView contenido;
        private TextView creadortext;
        private int id;
        private int idCreador;
        // Constructor: asocia cada atributo de la clase con su correspondiente en el layout definido por View
        public MyViewHolder(View viewElemento) {
            super(viewElemento);
            this.fecha=viewElemento.findViewById(R.id.postFecha);
            this.contenido=viewElemento.findViewById(R.id.postContenido);
            this.creadortext=viewElemento.findViewById(R.id.postCreador);
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Posts post=this.posts.get(position);
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String fechapost = formatter.format(post.getFecha());
        holder.fecha.setText(fechapost);
        holder.creadortext.setText("Tu :");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.contenido.setText(Html.fromHtml(post.getContenido(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.contenido.setText(Html.fromHtml(post.getContenido()));
        }
        holder.idCreador=post.getIdCreador();
        holder.id=post.getId();
    }
    @Override
    public int getItemCount() {
        return this.posts.size();
    }
}

