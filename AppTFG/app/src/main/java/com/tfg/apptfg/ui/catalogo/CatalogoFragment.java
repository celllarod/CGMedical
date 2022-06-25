package com.tfg.apptfg.ui.catalogo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tfg.apptfg.adapter.ListaFarmacosAdapter;
import com.tfg.apptfg.databinding.CatalogoFragmentBinding;
import com.tfg.apptfg.io.response.FarmacoResumen;

import java.util.List;

public class CatalogoFragment extends Fragment implements SearchView.OnQueryTextListener{

    private CatalogoFragmentBinding binding;
    private RecyclerView rvListaFarmacos;
    private ListaFarmacosAdapter listaFarmacosAdapter;
    private SearchView svBuscador;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CatalogoViewModel catalogoViewModel = new ViewModelProvider(this).get(CatalogoViewModel.class);
        binding = CatalogoFragmentBinding.inflate(inflater, container, false);
        catalogoViewModel.obtenerFarmacos(getContext());
        View root = binding.getRoot();
        catalogoViewModel.getFarmacos(getContext()).observe(getViewLifecycleOwner(), this::inicializarListaFarmacos);
        svBuscador = binding.svBuscador;
        rvListaFarmacos = binding.rvCatalogo;



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

        //rvListaFarmacos.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvListaFarmacos.setLayoutManager(new LinearLayoutManager(getContext()));
        listaFarmacosAdapter = new ListaFarmacosAdapter(farmacoResumenList);
        rvListaFarmacos.setAdapter(listaFarmacosAdapter);

        // Buscador
        svBuscador.setOnQueryTextListener(this); // Hace que se llame a onQueryTextChange/Submit

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        listaFarmacosAdapter.filtrado(s);
        return false;
    }
}