package com.tfg.apptfg.ui.mezclas.step;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tfg.apptfg.GeneralUtils;
import com.tfg.apptfg.adapter.ListaFarmacosRecetaAdapter;
import com.tfg.apptfg.databinding.FragmentStepRecetaBinding;
import com.tfg.apptfg.io.request.DatosCalculoMezclas;
import com.tfg.apptfg.io.response.Carga;
import com.tfg.apptfg.ui.mezclas.MezclasViewModel;
import java.util.List;
import java.util.Objects;

public class RecetaStepFragment extends Fragment {

    private RecetaViewModel recetaViewModel;
    private MezclasViewModel mezclasViewModel;
    private RecyclerView rvRecetas;
    private List<Carga> recetaList;
    ListaFarmacosRecetaAdapter listaFarmacosRecetaAdapter;
    private FragmentStepRecetaBinding binding;
    private DatosCalculoMezclas datos;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d("tag_s4", "attach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("tag_s4", "create");
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.d("tag_s4", "start");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("tag_s4", "viewcreated");
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d("tag_s4", "viewstaterestored");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("tag_s4", "resume");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("tag_s4", "saveinstancestate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("tag_s4", "destroy");
    }

    @Override
    public void onStop() {
        Log.d("tag_s4", "stop");
        super.onStop();
    }
    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("tag_s4", "detach");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("tag_s4", "pause");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("tag_s4", "destroyview");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("tag_s4", "createView");
        recetaViewModel = new ViewModelProvider(this).get(RecetaViewModel.class);
        mezclasViewModel = new ViewModelProvider(requireParentFragment()).get(MezclasViewModel.class);
        binding = FragmentStepRecetaBinding.inflate(inflater, container, false);

        View root = binding.getRoot();
        rvRecetas = binding.rvRecetas;

        if (!Objects.isNull(mezclasViewModel.getDatos())){
            datos = mezclasViewModel.getDatos();
            recetaViewModel.getReceta(getContext(), datos).observe(getViewLifecycleOwner(), this::inicializarListaReceta);
        } else {
            GeneralUtils.showErrorToast(getContext(), "Revise los datos introducidos, existen errores.");
        }

        return root;
    }

    private void inicializarListaReceta(List<Carga> receta) {
        rvRecetas.setLayoutManager(new LinearLayoutManager(getContext()));
        listaFarmacosRecetaAdapter = new ListaFarmacosRecetaAdapter(receta);
        rvRecetas.setAdapter(listaFarmacosRecetaAdapter);
    }


}