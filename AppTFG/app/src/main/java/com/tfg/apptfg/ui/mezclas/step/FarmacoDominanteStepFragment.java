package com.tfg.apptfg.ui.mezclas.step;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
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
    private TextInputLayout tilDosis;
    private TextInputLayout tilConcentracion;
    private TextInputLayout tilPresentacion;
    private TextInputLayout tilNombre;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("tag_s2", "create");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("tag_s2", "destroyview");
        binding = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!acNombreFd.getText().toString().isEmpty()) {
            onItemClickNombres(null, getView(), 0, 0);
            Log.d("tag_s2", "start");
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d("tag_s2", "createview");
        farmacoDominanteStepViewModel = new ViewModelProvider(this).get(FarmacoDominanteStepViewModel.class);
        mezclasViewModel = new ViewModelProvider(requireParentFragment()).get(MezclasViewModel.class);
        binding = FragmentStepFarmacoDominanteBinding.inflate(inflater, container, false);
        farmacoDominanteStepViewModel.getNombresFarmacos(getContext()).observe(getViewLifecycleOwner(), this::inicializarNombres);

        // Inicializar elementos
        View root = binding.getRoot();
        acNombreFd = binding.acNombreFd;
        etValorConcentracion = binding.lyConcentracion.etValorConcentracion;
        spUnidadConcentracion = binding.lyConcentracion.spUnidadConcentracion;
        acPresentacion = binding.acPresentacionFd;
        etValorDosis = binding.lyDosis.etValorDosis;
        spUnidadDosis = binding.lyDosis.spUnidadDosis;
        tilDosis = binding.lyDosis.tilValorDosis;
        tilConcentracion = binding.lyConcentracion.tilValorConcentracion;
        tilPresentacion = binding.tilPresentacionFd;
        tilNombre = binding.tilNombreFd;

        acNombreFd.setOnItemClickListener(this::onItemClickNombres);
        setOnChangeTextListenerEditText();
        setOnItemSelectedListenerSpinner();

        return root;
    }

    // Se ejecuta antes de que el fragmento se reemplace
    @Override
    public void onStop() {
        super.onStop();
        String nombre = (isValidNombre()) ? acNombreFd.getText().toString() : null;
        PropiedadSimple presentacion = (isValidPresentacion())? convertirPresentacion(acPresentacion.getText().toString()) : null;
        PropiedadSimple concentracion = (isValidConcentracion())?
                new PropiedadSimple(Double.valueOf(etValorConcentracion.getText().toString()), spUnidadConcentracion.getSelectedItem().toString()) : null;
        PropiedadSimple dosis = (isValorDosisValid() && !isDosisEmpty())?
                new PropiedadSimple(Double.valueOf(etValorDosis.getText().toString()), spUnidadDosis.getSelectedItem().toString()) : null;
//    @Override
//    public void onStop() {
//        super.onStop();
//        String nombre = (isValidNombre()) ? acNombreFd.getText().toString() : null;
//        PropiedadSimple presentacion = (isValidPresentacion())? convertirPresentacion(acPresentacion.getText().toString()) : null;
//        PropiedadSimple concentracion = (isConcentracionValid())?
//                new PropiedadSimple(Double.valueOf(etValorConcentracion.getText().toString()), spUnidadConcentracion.getSelectedItem().toString()) : null;
//        PropiedadSimple dosis = (isValorDosisValid() && !isDosisEmpty())?
//                new PropiedadSimple(Double.valueOf(etValorDosis.getText().toString()), spUnidadDosis.getSelectedItem().toString()) : null;

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
//        mezclasViewModel.setFarmacoDominante(nombre, concentracion, presentacion, dosis);
        mezclasViewModel.setFarmacoDominante(nombre, concentracion, presentacion, dosis);
    }


    private void inicializarNombres(Map<String, UUID> nombres) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_dropdown_item_1line, new ArrayList<>(nombres.keySet()));
        acNombreFd.setAdapter(adapter);
    }

    private void inicializarPropiedades(FarmacoDetalle farmaco) {
        // Presentación
        List<String> preStr = new ArrayList<>();
        if (!Objects.isNull(farmaco)) {
            Set<Propiedad> presentaciones = farmaco.getPropiedades().getPresentaciones();
            presentaciones.forEach(p -> preStr.add(p.getValor() + " " + p.getUnidad()));
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
        tilPresentacion.setError(null);
        isValidPresentacion();
    }

    public void onItemClickNombres(AdapterView<?> adapterView, View view, int i, long l) {

        tilNombre.setError(null);
        String nombreSeleccionado = acNombreFd.getText().toString();
        UUID uuidSeleccionado = Objects.requireNonNull(farmacoDominanteStepViewModel.getNombresFarmacos(view.getContext()).getValue()).get(nombreSeleccionado);
        farmacoDominanteStepViewModel.getFarmacoDetalle(getContext(), uuidSeleccionado).observe(getViewLifecycleOwner(), this::inicializarPropiedades);
    }


    private void setOnChangeTextListenerEditText() {
        etValorDosis.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                isValorDosisValid();
            }
        });
        //--
        etValorConcentracion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                isValidConcentracion();
            }
        });
    }


    private void setOnItemSelectedListenerSpinner() {
        spUnidadDosis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                isValorDosisValid();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }


    private boolean isValid() {
        boolean result = !isDosisEmpty();
        result = isValorDosisValid() && result;
        result = isValidNombre() && result;
        result = isValidPresentacion() && result;
        return !acPresentacion.getText().toString().isEmpty() && result;
    }

    private boolean isValorDosisValid(){
        boolean result = true;
        String unidadSeleccionada = spUnidadDosis.getSelectedItem().toString();
        if (!Objects.isNull(etValorDosis.getText()) && !etValorDosis.getText().toString().isEmpty()) {
            double valorSeleccionado = Double.parseDouble(etValorDosis.getText().toString());
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
                tilDosis.setError("Valor máximo: " + valorMaximo + " " + unidadSeleccionada);
                result = false;
            } else {
                tilDosis.setError(null);
            }
        }
        return result;
    }

    private boolean isDosisEmpty(){
        boolean result = false;
        if (Objects.isNull(etValorDosis.getText()) || etValorDosis.getText().toString().isEmpty()) {
            tilDosis.setError("Este campo es obligatorio");
            result = true;
        } else{
            tilDosis.setError(null);
        }
        return result;
    }

    private boolean isValidConcentracion() {
        boolean result = true;
        if (etValorConcentracion.getText().toString().isEmpty()) {
            tilConcentracion.setError("Este campo es obligatorio");
            result = false;
        } else {
            tilConcentracion.setError(null);
        }
        return result;
    }

    private boolean isValidNombre() {
        boolean result = true;
        if (acNombreFd.getText().toString().isEmpty()) {
            tilNombre.setError("Este campo es obligatorio");
            result = false;
        } else {
            tilNombre.setError(null);
        }
        return result;
    }

    private boolean isValidPresentacion() {
        boolean result = true;
        if (acPresentacion.getText().toString().isEmpty()) {
            tilPresentacion.setError("Este campo es obligatorio");
            result = false;
        } else {
            tilPresentacion.setError(null);
        }
        return result;
    }

    public void resetearFd(){
        acNombreFd.setSelected(false);
        acPresentacion.setSelected(false);
        etValorConcentracion.setText(null);
        spUnidadDosis.setSelection(0);
        etValorDosis.setText(null);
        spUnidadConcentracion.setSelection(0);
    }
}