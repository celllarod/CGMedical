package com.tfg.apptfg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tfg.apptfg.R;
import com.tfg.apptfg.io.response.FarmacoResumen;

import java.util.List;

public class FarmacosAdapter extends RecyclerView.Adapter<FarmacosAdapter.ViewHolder> {

    private List<FarmacoResumen> farmacosList;
    private Context context;


    public FarmacosAdapter(List<FarmacoResumen> farmacosList, Context context) {
        this.farmacosList = farmacosList;
        this.context = context;
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
        String dosisMaxima = Double.toString(farmacosList.get(position).getDosisMaxima().getValor()) + farmacosList.get(position).getDosisMaxima().getUnidad();
        holder.txDosis.setText(dosisMaxima);

    }

    @Override
    public int getItemCount() {
        return farmacosList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgFarmaco;
        private TextView txNombre;
        private TextView txDosis;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Se inicializan los componentes del elemento de la lista
            imgFarmaco= itemView.findViewById(R.id.img_farmaco);
            txNombre = itemView.findViewById(R.id.tv_nombre_farmaco);
            txDosis = itemView.findViewById(R.id.tv_dosis_maxima);
        }
    }
}
