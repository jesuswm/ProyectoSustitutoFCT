package com.example.appmovil;

import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AdaptadorComentarios extends RecyclerView.Adapter<AdaptadorComentarios.ComentariosViewHolder>{
    ArrayList<Comentarios> comentarios;
    public AdaptadorComentarios(ArrayList<Comentarios> comentarios) {
        this.comentarios=comentarios;
    }
    public static class ComentariosViewHolder extends RecyclerView.ViewHolder {
        private TextView fecha;
        private TextView contenido;
        private TextView creadortext;
        private int id;
        private int idCreador;
        public ComentariosViewHolder(View viewElemento) {
            super(viewElemento);
            this.fecha=viewElemento.findViewById(R.id.comentarioFecha);
            this.contenido=viewElemento.findViewById(R.id.comentarioContenido);
            this.creadortext=viewElemento.findViewById(R.id.comentarioCreador);
        }
    }
    @NonNull
    @Override
    public ComentariosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View elemento = LayoutInflater.from(parent.getContext()).inflate(R.layout.contenedor_comentario,
                parent, false);
        ComentariosViewHolder mvh = new ComentariosViewHolder(elemento);
        return mvh ;
    }
    @Override
    public void onBindViewHolder(@NonNull final ComentariosViewHolder holder, final int position) {
        final Comentarios coment=this.comentarios.get(position);
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String fechapost = formatter.format(coment.getFecha());
        holder.fecha.setText(fechapost);
        holder.creadortext.setText(coment.getAutor());
        holder.creadortext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(holder.contenido.getContext(), ""+coment.getIdCreador(), Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(holder.contenido.getContext(),OtroUsuarioActivity.class);
                intent.putExtra("id",coment.getIdCreador());
                holder.contenido.getContext().startActivity(intent);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.contenido.setText(Html.fromHtml(coment.getContenido(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.contenido.setText(Html.fromHtml(coment.getContenido()));
        }
        holder.idCreador=coment.getIdCreador();
        holder.id=coment.getId();
    }
    @Override
    public int getItemCount() {
        return this.comentarios.size();
    }
}
