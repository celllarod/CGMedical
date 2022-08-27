package com.tfg.apptfg.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tfg.apptfg.DetalleActivity;
import com.tfg.apptfg.R;
import com.tfg.apptfg.io.response.FarmacoResumen;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ListaFarmacosAdapter extends RecyclerView.Adapter<ListaFarmacosAdapter.ViewHolder> {

    List<FarmacoResumen> farmacosList;
    List<FarmacoResumen> farmacosOriginalList;


    public ListaFarmacosAdapter(List<FarmacoResumen> farmacosList) {
        this.farmacosList = farmacosList;
        farmacosOriginalList = new ArrayList<>();
        farmacosOriginalList.addAll(farmacosList);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflamos el layout del elemento de la lista
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.farmaco, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Manipulamos los componentes
        holder.txNombre.setText(farmacosList.get(position).getNombre());
        String dosisMaxima = " " + farmacosList.get(position).getDosisMaxima().getValor() + " " + farmacosList.get(position).getDosisMaxima().getUnidad();
        holder.txDosis.setText(dosisMaxima);

    }

    @Override
    public int getItemCount() {
        return farmacosList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

       // private final ImageView imgFarmaco;
        private final TextView txNombre;
        private final TextView txDosis;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Se inicializan los componentes del elemento de la lista
            //imgFarmaco= itemView.findViewById(R.id.img_farmaco);
            txNombre = itemView.findViewById(R.id.tv_nombre_farmaco);
            txDosis = itemView.findViewById(R.id.tv_dosis_maxima);

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), DetalleActivity.class);
                intent.putExtra("ID",farmacosOriginalList.get(getAbsoluteAdapterPosition()).getId().toString());
                v.getContext().startActivity(intent);
            });
        }
    }

    public void filtrado(String txtBuscar){
        Log.d("Debug", "Filtrando por subcadena: " + txtBuscar);
        int longitud = txtBuscar.length();
        if (longitud == 0) { // Si no se busca nada, se devuelve la lista completa
            farmacosList.clear();
            farmacosList.addAll(farmacosOriginalList);
        } else{
            List<FarmacoResumen> farmacosFiltradosList = farmacosOriginalList.stream()
                    .filter(i -> i.getNombre().toLowerCase().contains(txtBuscar.toLowerCase(Locale.ROOT)))
                    .collect(Collectors.toList());
            farmacosList.clear();
            farmacosList.addAll(farmacosFiltradosList);
        }
        notifyDataSetChanged();
    }
}
