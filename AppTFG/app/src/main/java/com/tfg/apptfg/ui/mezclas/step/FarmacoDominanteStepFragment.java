package com.tfg.apptfg.ui.mezclas.step;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.tfg.apptfg.ValidationUtils;
import com.tfg.apptfg.databinding.FarmacoDominanteStepFragmentBinding;
import com.tfg.apptfg.io.request.PropiedadSimple;
import com.tfg.apptfg.io.response.FarmacoDetalle;
import com.tfg.apptfg.io.response.Propiedad;
import com.tfg.apptfg.ui.mezclas.MezclasViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class FarmacoDominanteStepFragment extends Fragment {

    private FarmacoDominanteStepFragmentBinding binding;
    private AutoCompleteTextView acNombreFd;
    private EditText etValorConcentracion;
    private AppCompatSpinner spUnidadConcentracion;
    private EditText etValorDosis;
    private AppCompatSpinner spUnidadDosis;
    private AutoCompleteTextView acPresentacion;
    private FarmacoDominanteStepViewModel farmacoDominanteStepViewModel;
    private MezclasViewModel mezclasViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        farmacoDominanteStepViewModel = new ViewModelProvider(this).get(FarmacoDominanteStepViewModel.class);
        mezclasViewModel = new ViewModelProvider(requireParentFragment()).get(MezclasViewModel.class);
        binding = FarmacoDominanteStepFragmentBinding.inflate(inflater, container, false);
        farmacoDominanteStepViewModel.getNombresFarmacos(getContext()).observe(getViewLifecycleOwner(), this::inicializarNombres);

        View root = binding.getRoot();
        acNombreFd = binding.acNombreFd;
        etValorConcentracion = binding.lyConcentracion.etValorConcentracion;
        spUnidadConcentracion = binding.lyConcentracion.spUnidadConcentracion;
        acPresentacion = binding.acPresentacionFd;
        etValorDosis = binding.lyDosis.etValorDosis;
        spUnidadDosis = binding.lyDosis.spUnidadDosis;
        acNombreFd.setOnItemClickListener(this::setOnItemClickNombres);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // Se ejecuta antes de que el fragmento se reemplace
    @Override
    public void onPause() {
        super.onPause();
        String nombre = (ValidationUtils.validateNombreMezclas(acNombreFd))? acNombreFd.getText().toString() : null;
        PropiedadSimple presentacion = (ValidationUtils.validatePresentacionMezclas(acPresentacion))? convertirPresentacion(acPresentacion.getText().toString()) : null;
        PropiedadSimple concentracion = (ValidationUtils.validateConcentracionMezclas(etValorConcentracion, spUnidadConcentracion))?
                new PropiedadSimple(Double.valueOf(etValorConcentracion.getText().toString()), spUnidadConcentracion.getSelectedItem().toString()) : null;
        PropiedadSimple dosis = (ValidationUtils.validateDosisMezclas(etValorDosis, spUnidadDosis))?
                new PropiedadSimple(Double.valueOf(etValorDosis.getText().toString()), spUnidadDosis.getSelectedItem().toString()) : null;

        Bundle estado = new Bundle();
        if(Objects.isNull(nombre) && Objects.isNull(presentacion) && Objects.isNull(concentracion) && Objects.isNull(dosis)) {
            estado.putString("bundleKey", "empty");
            getParentFragmentManager().setFragmentResult("requestKey", estado);
        } else if (Objects.isNull(nombre) || Objects.isNull(presentacion) || Objects.isNull(concentracion) || Objects.isNull(dosis)) {
            estado.putString("bundleKey", "error");
            getParentFragmentManager().setFragmentResult("requestKey", estado);
        } else {
            estado.putString("bundleKey", "correct");
            getParentFragmentManager().setFragmentResult("requestKey", estado);
        }
        this.getActivity().getSupportFragmentManager().setFragmentResult("requestKey", estado);
        mezclasViewModel.setFarmacoDominante(nombre, concentracion, presentacion, dosis);
    }

    public void setOnItemClickNombres(AdapterView<?> adapterView, View view, int i, long l) {
        String nombreSeleccionado = acNombreFd.getText().toString();
        UUID uuidSeleccionado = farmacoDominanteStepViewModel.getNombresFarmacos(view.getContext()).getValue().get(nombreSeleccionado);
        farmacoDominanteStepViewModel.getFarmacoDetalle(getContext(), uuidSeleccionado).observe(getViewLifecycleOwner(), this::inicializarPropiedades);
    }

    private void inicializarNombres(Map<String, UUID> nombres) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_dropdown_item_1line, nombres.keySet().stream().collect(Collectors.toList()));
        acNombreFd.setAdapter(adapter);
    }

    private void inicializarPropiedades(FarmacoDetalle farmaco) {

        // Presentacion spinner
        List<String> preStr = new ArrayList<>();
        if (!Objects.isNull(farmaco)) {
            Set<Propiedad> presentaciones = farmaco.getPropiedades().getPresentaciones();
            presentaciones.forEach(p -> {
                preStr.add(p.getValor() + " " + p.getUnidad());
            });
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_dropdown_item_1line, preStr);
        acPresentacion.setAdapter(adapter);

        //TODO: Dosis máxima
        Propiedad dosisMaxima = farmaco.getPropiedades().getDosisMaxima();
    }

    private PropiedadSimple convertirPresentacion(String presentacion){
        String[] presentacionSplit = presentacion.split(" ");
        return new PropiedadSimple(Double.valueOf(presentacionSplit[0]), presentacionSplit[1]);
    }
}