package com.tfg.apptfg.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tfg.apptfg.R;
import com.tfg.apptfg.io.response.Carga;

import java.util.List;

public class ListaFarmacosRecetaAdapter extends RecyclerView.Adapter<ListaFarmacosRecetaAdapter.ViewHolder> {

    List<Carga> farmacosList;


    public ListaFarmacosRecetaAdapter(List<Carga> farmacosList) {
        this.farmacosList = farmacosList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflamos el layout del elemento de la lista
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.receta_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Manipulamos los componentes
        holder.txNombre.setText(farmacosList.get(position).getNombre());
        holder.txCarga.setText(farmacosList.get(position).getCarga());
    }

    @Override
    public int getItemCount() {
        return farmacosList.size();
    }


    public void setFarmacosList(List<Carga> fsList) {
        this.farmacosList = fsList;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView txNombre;
        private final TextView txCarga;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Se inicializan los componentes del elemento de la lista
            txNombre = itemView.findViewById(R.id.tv_nombre_receta);
            txCarga = itemView.findViewById(R.id.tv_carga);


        }
    }
}
