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
import com.tfg.apptfg.databinding.FragmentStepFarmacoDominanteBinding;
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

    private FragmentStepFarmacoDominanteBinding binding;
    private AutoCompleteTextView acNombreFd;
    private EditText etValorConcentracion;
    private AppCompatSpinner spUnidadConcentracion;
    private EditText etValorDosis;
    private AppCompatSpinner spUnidadDosis;
    private AutoCompleteTextView acPresentacion;
    private FarmacoDominanteStepViewModel farmacoDominanteStepViewModel;
    private MezclasViewModel mezclasViewModel;
    private Propiedad dosisMaxima;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        farmacoDominanteStepViewModel = new ViewModelProvider(this).get(FarmacoDominanteStepViewModel.class);
        mezclasViewModel = new ViewModelProvider(requireParentFragment()).get(MezclasViewModel.class);
        binding = FragmentStepFarmacoDominanteBinding.inflate(inflater, container, false);
        farmacoDominanteStepViewModel.getNombresFarmacos(getContext()).observe(getViewLifecycleOwner(), this::inicializarNombres);

        View root = binding.getRoot();
        acNombreFd = binding.acNombreFd;
        etValorConcentracion = binding.lyConcentracion.etValorConcentracion;
        spUnidadConcentracion = binding.lyConcentracion.spUnidadConcentracion;
        acPresentacion = binding.acPresentacionFd;
        etValorDosis = binding.lyDosis.etValorDosis;
        spUnidadDosis = binding.lyDosis.spUnidadDosis;

        acNombreFd.setOnItemClickListener(this::onItemClickNombres);
        etValorConcentracion.setOnClickListener(this::onClickConcentracionValor);
        etValorDosis.setOnClickListener(this::onClickDosisValor);

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!acNombreFd.getText().toString().isEmpty()) {
            onItemClickNombres(null, getView(), 0, 0);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    // Se ejecuta antes de que el fragmento se reemplace
    @Override
    public void onStop() {
        super.onStop();
        String nombre = (isValidNombre()) ? acNombreFd.getText().toString() : null;
        PropiedadSimple presentacion = (isValidPresentacion())? convertirPresentacion(acPresentacion.getText().toString()) : null;
        PropiedadSimple concentracion = (isConcentracionValid())?
                new PropiedadSimple(Double.valueOf(etValorConcentracion.getText().toString()), spUnidadConcentracion.getSelectedItem().toString()) : null;
        PropiedadSimple dosis = (isValorDosisValid() && !isDosisEmpty())?
                new PropiedadSimple(Double.valueOf(etValorDosis.getText().toString()), spUnidadDosis.getSelectedItem().toString()) : null;

//        Bundle estado = new Bundle();
//        if(Objects.isNull(nombre) && Objects.isNull(presentacion) && Objects.isNull(concentracion) && Objects.isNull(dosis)) {
//            estado.putString("bundleKey", "empty");
//            getParentFragmentManager().setFragmentResult("requestKey", estado);
//        } else if (Objects.isNull(nombre) || Objects.isNull(presentacion) || Objects.isNull(concentracion) || Objects.isNull(dosis)) {
//            estado.putString("bundleKey", "error");
//            getParentFragmentManager().setFragmentResult("requestKey", estado);
//        } else {
//            estado.putString("bundleKey", "correct");
//            getParentFragmentManager().setFragmentResult("requestKey", estado);
//        }
//        this.getActivity().getSupportFragmentManager().setFragmentResult("requestKey", estado);
        mezclasViewModel.setFarmacoDominante(nombre, concentracion, presentacion, dosis);
    }


    private void inicializarNombres(Map<String, UUID> nombres) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_dropdown_item_1line, nombres.keySet().stream().collect(Collectors.toList()));
        acNombreFd.setAdapter(adapter);
    }

    private void inicializarPropiedades(FarmacoDetalle farmaco) {
        // Presentación
        List<String> preStr = new ArrayList<>();
        if (!Objects.isNull(farmaco)) {
            Set<Propiedad> presentaciones = farmaco.getPropiedades().getPresentaciones();
            presentaciones.forEach(p -> {
                preStr.add(p.getValor() + " " + p.getUnidad());
            });
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_dropdown_item_1line, preStr);
        acPresentacion.setAdapter(adapter);
        acPresentacion.setOnItemClickListener(this:: onItemClickPresentacion);

        // Dosis máxima
        dosisMaxima = farmaco.getPropiedades().getDosisMaxima();
        isValorDosisValid();
    }

    private PropiedadSimple convertirPresentacion(String presentacion){
        String[] presentacionSplit = presentacion.split(" ");
        return new PropiedadSimple(Double.valueOf(presentacionSplit[0]), presentacionSplit[1]);
    }

    public void onItemClickPresentacion(AdapterView<?> adapterView, View view, int i, long l) {
        if (acPresentacion.getError() != null) {
            acPresentacion.setError(null);
            isValidPresentacion();
        }
    }

    public void onItemClickNombres(AdapterView<?> adapterView, View view, int i, long l) {
        acNombreFd.setError(null);
        String nombreSeleccionado = acNombreFd.getText().toString();
        UUID uuidSeleccionado = farmacoDominanteStepViewModel.getNombresFarmacos(view.getContext()).getValue().get(nombreSeleccionado);
        farmacoDominanteStepViewModel.getFarmacoDetalle(getContext(), uuidSeleccionado).observe(getViewLifecycleOwner(), this::inicializarPropiedades);
    }


    private void onClickDosisValor(View v){
        etValorDosis.setError(null);
        isValorDosisValid();
    }

    public void onClickDosisUnidad(AdapterView<?> parent, View view, int position, long id) {
        // TODO: asociar al spinner dosis unidad
        onClickDosisValor(view);
    }

    private void onClickConcentracionValor(View v){
        if (etValorDosis.getError() != null) {
            etValorDosis.setError(null);
            isConcentracionValid();
        }
    }

    public void onClickConcentracionUnidad(AdapterView<?> parent, View view, int position, long id) {
        // TODO: asociar al spinner conc unidad
        onClickConcentracionValor(view);
    }

    private boolean isValid() {
        boolean result = true;
        result = !isDosisEmpty() && result;
        result = isValorDosisValid() && result;
        result = isValidNombre() && result;
        result = isValidPresentacion() && result;
        return !acPresentacion.getText().toString().isEmpty() && result;
    }

    private boolean isValorDosisValid(){
        boolean result = true;
        String unidadSeleccionada = spUnidadDosis.getSelectedItem().toString();
        if (!Objects.isNull(etValorDosis.getText()) && !etValorDosis.getText().toString().isEmpty()) {
            Double valorSeleccionado = Double.valueOf(etValorDosis.getText().toString());
            Double valorMaximo = dosisMaxima.getValor();
            if (!Objects.equals(unidadSeleccionada, dosisMaxima.getUnidad())) {
                switch (unidadSeleccionada) {
                    case "mg/día":
                        valorMaximo = dosisMaxima.getValor() / 1000;
                        break;
                    case "ug/día":
                        valorMaximo = dosisMaxima.getValor() * 1000;
                        break;
                }
            }

            if (valorSeleccionado > valorMaximo) {
                etValorDosis.setError("Valor máximo: " + valorMaximo + " " + unidadSeleccionada);
                result = false;
            }
        }
        return result;
    }

    private boolean isDosisEmpty(){
        boolean result = false;
        if (Objects.isNull(etValorDosis.getText()) || etValorDosis.getText().toString().isEmpty()) {
            etValorDosis.setError("Este campo es obligatorio");
            result = true;
        }
        return result;
    }

    private boolean isConcentracionValid() {
        boolean result = true;
        if (etValorConcentracion.getText().toString().isEmpty()) {
            etValorConcentracion.setError("Este campo es obligatorio");
            result = false;
        }
        return result;
    }

    private boolean isValidNombre() {
        boolean result = true;
        if (acNombreFd.getText().toString().isEmpty()) {
            acNombreFd.setError("Este campo es obligatorio", null);
            result = false;
        }
        return result;
    }

    private boolean isValidPresentacion() {
        boolean result = true;
        if (acPresentacion.getText().toString().isEmpty()) {
            acPresentacion.setError("Este campo es obligatorio", null);
            result = false;
        }
        return result;
    }
}