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

public class AdaptadorBusqueda extends RecyclerView.Adapter<AdaptadorBusqueda.BusquedaViewHolder>{
    ArrayList<Usuarios> usuarios;
    public AdaptadorBusqueda(ArrayList<Usuarios> usuarios) {
        this.usuarios=usuarios;
    }
    public static class BusquedaViewHolder extends RecyclerView.ViewHolder {
        private TextView nombre;
        private TextView email;
        private int id;
        public BusquedaViewHolder(View viewElemento) {
            super(viewElemento);
            this.nombre=viewElemento.findViewById(R.id.contBusquedaNombre);
            this.email=viewElemento.findViewById(R.id.contBusquedaEmail);
        }
    }
    @NonNull
    @Override
    public BusquedaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View elemento = LayoutInflater.from(parent.getContext()).inflate(R.layout.contenedor_busqueda,
                parent, false);
        BusquedaViewHolder mvh = new BusquedaViewHolder(elemento);
        return mvh ;
    }
    @Override
    public void onBindViewHolder(@NonNull final BusquedaViewHolder holder, final int position) {
        final Usuarios usuario=this.usuarios.get(position);
        holder.id=usuario.getId();
        holder.email.setText(usuario.getEmail());
        holder.nombre.setText(usuario.getNombre());
        holder.nombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(holder.nombre.getContext(), ""+usuario.getId(), Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(holder.nombre.getContext(),OtroUsuarioActivity.class);
                intent.putExtra("id",usuario.getId());
                holder.nombre.getContext().startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return this.usuarios.size();
    }
}
