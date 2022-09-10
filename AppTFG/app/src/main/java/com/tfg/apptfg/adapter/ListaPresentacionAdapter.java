package com.tfg.apptfg.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tfg.apptfg.R;
import com.tfg.apptfg.io.request.PropiedadSimple;
import com.tfg.apptfg.io.response.Carga;
import com.tfg.apptfg.io.response.Propiedad;

import java.util.List;
import java.util.Set;

public class ListaPresentacionAdapter extends RecyclerView.Adapter<ListaPresentacionAdapter.ViewHolder> {

    List<Propiedad> lista;


    public ListaPresentacionAdapter(List<Propiedad> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflamos el layout del elemento de la lista
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.box_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Manipulamos los componentes
        holder.txValor.setText(String.valueOf(lista.get(position).getValor()));
        holder.txUnidad.setText(lista.get(position).getUnidad());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }


    public void setLista(List<Propiedad> preList) {
        this.lista = preList;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView txValor;
        private final TextView txUnidad;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Se inicializan los componentes del elemento de la lista
            txValor = itemView.findViewById(R.id.tv_valor);
            txUnidad = itemView.findViewById(R.id.tv_unidad);
        }
    }
}
