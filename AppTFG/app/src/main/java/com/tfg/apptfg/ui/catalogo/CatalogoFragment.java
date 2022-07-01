package com.tfg.apptfg.ui.catalogo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.tfg.apptfg.InicioActivity;
import com.tfg.apptfg.RegistrarFarmaco;
import com.tfg.apptfg.SessionManager;
import com.tfg.apptfg.adapter.ListaFarmacosAdapter;
import com.tfg.apptfg.databinding.CatalogoFragmentBinding;
import com.tfg.apptfg.io.response.FarmacoResumen;

import java.util.List;

public class CatalogoFragment extends Fragment implements SearchView.OnQueryTextListener{

    private CatalogoFragmentBinding binding;
    private RecyclerView rvListaFarmacos;
    private ListaFarmacosAdapter listaFarmacosAdapter;
    private SearchView svBuscador;
    private ExtendedFloatingActionButton fab;
    private static final String ROL_GESTOR = "GESTOR";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CatalogoViewModel catalogoViewModel = new ViewModelProvider(this).get(CatalogoViewModel.class);
        binding = CatalogoFragmentBinding.inflate(inflater, container, false);
        catalogoViewModel.obtenerFarmacos(getContext());
        View root = binding.getRoot();
        catalogoViewModel.getFarmacos(getContext()).observe(getViewLifecycleOwner(), this::inicializarListaFarmacos);
        svBuscador = binding.svBuscador;
        rvListaFarmacos = binding.rvCatalogo;
        fab = binding.fab;

        // Botón añadir fármaco solo visible para rol GESTOR
        if (ROL_GESTOR.equals(SessionManager.get(getContext()).getUserRol())){
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), RegistrarFarmaco.class);
                startActivity(intent);
            });
        }
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
        listaFarmacosAdapter.filtrado(s);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        listaFarmacosAdapter.filtrado(s);
        return false;
    }
}