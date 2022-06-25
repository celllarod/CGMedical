package com.tfg.apptfg.ui.catalogo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tfg.apptfg.adapter.FarmacosAdapter;
import com.tfg.apptfg.databinding.CatalogoFragmentBinding;
import com.tfg.apptfg.io.response.FarmacoResumen;

import java.util.List;

public class CatalogoFragment extends Fragment {

    private CatalogoFragmentBinding binding;
    private RecyclerView rvListaFarmacos;
    private FarmacosAdapter farmacosAdapter;
    private CatalogoViewModel catalogoViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        catalogoViewModel = new ViewModelProvider(this).get(CatalogoViewModel.class);

        binding = CatalogoFragmentBinding.inflate(inflater, container, false);
        catalogoViewModel.obtenerFarmacos(getContext());
        View root = binding.getRoot();

        catalogoViewModel.getFarmacos(getContext()).observe(getViewLifecycleOwner(), this::inicializarListaFarmacos);

        // catalogoViewModel.getFarmacos(getContext());



        /*final TextView textView = binding.textCatalogo;
        catalogoViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        final Button farmacosBtn = binding.farmacosBtn;*/


        // TODO: codigo para cerrar sesión
        /*farmacosBtn.setOnClickListener( view -> {
            catalogoViewModel.getFarmacos(getContext());
            SessionManager.destroy(getContext().getApplicationContext());
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            startActivity(intent);
        });*/

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void inicializarListaFarmacos(List<FarmacoResumen> farmacoResumenList){
        rvListaFarmacos = binding.rvCatalogo;
        rvListaFarmacos.setLayoutManager(new LinearLayoutManager(getContext()));
        farmacosAdapter = new FarmacosAdapter(farmacoResumenList, getContext());
        rvListaFarmacos.setAdapter(farmacosAdapter);

    }


}