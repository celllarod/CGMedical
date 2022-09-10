package com.tfg.apptfg.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.tfg.apptfg.DetalleActivity;
import com.tfg.apptfg.R;
import com.tfg.apptfg.io.request.FarmacoSecundario;
import com.tfg.apptfg.io.response.FarmacoResumen;
import com.tfg.apptfg.ui.mezclas.MezclasViewModel;
import com.tfg.apptfg.ui.mezclas.step.FarmacosSecundariosStepViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ListaFarmacosSecundariosAdapter extends RecyclerView.Adapter<ListaFarmacosSecundariosAdapter.ViewHolder> {

    List<FarmacoSecundario> farmacosList;


    public ListaFarmacosSecundariosAdapter(List<FarmacoSecundario> farmacosList) {
        this.farmacosList = farmacosList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflamos el layout del elemento de la lista
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.farmaco_secundario_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Manipulamos los componentes
        holder.txNombre.setText(farmacosList.get(position).getNombre());
    }

    @Override
    public int getItemCount() {
        return farmacosList.size();
    }

    public  List<FarmacoSecundario> getFarmacosList() { return farmacosList;};

    public void setFarmacosList(List<FarmacoSecundario> fsList) {
        this.farmacosList = fsList;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView txNombre;
        private final ImageButton btDelete;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Se inicializan los componentes del elemento de la lista
            txNombre = itemView.findViewById(R.id.tv_nombre_fs);
            btDelete = itemView.findViewById(R.id.bt_delete_fs);

            btDelete.setOnClickListener(v -> {
                removeAt(getLayoutPosition());
            });
        }

        private void removeAt(int position) {
            farmacosList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, farmacosList.size());
        }
    }
}
