package com.tfg.apptfg.ui.dosimetro;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.tfg.apptfg.GeneralUtils;
import com.tfg.apptfg.R;
import com.tfg.apptfg.SessionManager;
import com.tfg.apptfg.databinding.FragmentDosimetroBinding;
import com.tfg.apptfg.io.ApiAdapter;
import com.tfg.apptfg.io.request.DatosCalculoDosis;
import com.tfg.apptfg.io.request.PropiedadSimple;
import com.tfg.apptfg.io.response.Carga;
import com.tfg.apptfg.io.response.FarmacoDetalle;
import com.tfg.apptfg.io.response.Propiedad;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DosimetroFragment extends Fragment {

    private DosimetroViewModel dosimetroViewModel;
    private FragmentDosimetroBinding binding;
    private AutoCompleteTextView acNombreFd;
    private TextInputLayout tilDosisActualFd;
    private TextInputLayout tilDosisActualFs;
    private TextInputLayout tilDosisNuevaFd;
    private TextInputLayout tilDosisNuevaFs;
    private EditText etDosisActualFd;
    private EditText etDosisNuevaFd;
    private EditText etDosisActualFs;
    private EditText etDosisNuevaFs;
    private Spinner spUnidadActualFs;
    private Spinner spUnidadNuevaFs;
    private Spinner spUnidadActualFd;
    private Spinner spUnidadNuevaFd;
    private AutoCompleteTextView acNombreFs;
    private Propiedad dosisMaximaFd;
    private Propiedad dosisMaximaFs;
    private MaterialButton btReset;
    private MaterialButton btCalcular;
    private MaterialButton btDosis;
    private TextView titFd;
    private TextView titFs;
    private TextView titNuevaDosisFd;
    private TextView titNuevaDosisFs;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dosimetroViewModel = new ViewModelProvider(this).get(DosimetroViewModel.class);

        binding = FragmentDosimetroBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        acNombreFd = binding.acNombreFdDosimetro;
        etDosisActualFd = binding.lyDosisActualFdDosimetro.etValorDosis;
        etDosisNuevaFd = binding.lyDosisNuevaFdDosimetro.etValorDosis;
        spUnidadActualFd = binding.lyDosisActualFdDosimetro.spUnidadDosis;
        spUnidadNuevaFd = binding.lyDosisNuevaFdDosimetro.spUnidadDosis;
        acNombreFs = binding.acNombreFsDosimetro;
        etDosisActualFs = binding.lyDosisActualFsDosimetro.etValorDosis;
        etDosisNuevaFs = binding.lyDosisNuevaFsDosimetro.etValorDosis;
        spUnidadActualFs = binding.lyDosisActualFsDosimetro.spUnidadDosis;
        spUnidadNuevaFs = binding.lyDosisNuevaFsDosimetro.spUnidadDosis;
        btReset = binding.btResetearDosimetro;
        btCalcular = binding.btCalcularNuevaDosis;
        btDosis = binding.btDosis;
        titFd = binding.titFdDosimetro;
        titFs = binding.titFsDosimetro;
        titNuevaDosisFd = binding.tvDosisNuevaFdDosimetro;
        titNuevaDosisFs = binding.tvDosisNuevaFsDosimetro;
        tilDosisActualFd = binding.lyDosisActualFdDosimetro.tilValorDosis;
        tilDosisActualFs = binding.lyDosisActualFsDosimetro.tilValorDosis;
        tilDosisNuevaFd = binding.lyDosisNuevaFdDosimetro.tilValorDosis;
        tilDosisNuevaFs = binding.lyDosisNuevaFsDosimetro.tilValorDosis;

        // Inicializamos
        dosimetroViewModel.getNombresFarmacos(getContext()).observe(getViewLifecycleOwner(), this::inicializarNombres);
        acNombreFd.setOnItemClickListener(this::onItemClickNombresFd);
        acNombreFs.setOnItemClickListener(this::onItemClickNombresFs);
        setOnChangeTextListenerEditText();
        setOnItemSelectedListenerSpinner();

        // Inicializamos botones
        btReset.setOnClickListener(this::onClickReset);
        btDosis.setOnClickListener(this::onClickCambiarNuevaDosis);
        btCalcular.setOnClickListener(this::onClickCalcular);
        // Deshabilitamos resto de campos
       deshabilitarCamposDosis();

        Log.d("tag_dosimetro", "createView");
        return root;
    }

    private void setOnItemSelectedListenerSpinner() {
        spUnidadActualFd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               isValorDosisValid(dosisMaximaFd, etDosisActualFd, spUnidadActualFd, tilDosisActualFd);
           }
           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {}
       });
        spUnidadActualFs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               isValorDosisValid(dosisMaximaFd, etDosisActualFs, spUnidadActualFs, tilDosisActualFs);
           }
           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {}
       });
        spUnidadNuevaFd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                isValorDosisValid(dosisMaximaFd, etDosisNuevaFd, spUnidadNuevaFd, tilDosisNuevaFd);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        spUnidadNuevaFs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                isValorDosisValid(dosisMaximaFd, etDosisNuevaFs, spUnidadNuevaFs, tilDosisNuevaFs);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }


    private void setOnChangeTextListenerEditText() {
        etDosisActualFd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                isValorDosisValid(dosisMaximaFd, etDosisActualFd, spUnidadActualFd, tilDosisActualFd);
            }
        });
        etDosisActualFs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                isValorDosisValid(dosisMaximaFs, etDosisActualFs, spUnidadActualFs, tilDosisActualFs);
            }
        });
        etDosisNuevaFd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                isValorDosisValid(dosisMaximaFd, etDosisNuevaFd, spUnidadNuevaFd, tilDosisNuevaFd);
            }
        });
        etDosisNuevaFs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                isValorDosisValid(dosisMaximaFs, etDosisNuevaFs, spUnidadNuevaFs, tilDosisNuevaFs);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void inicializarNombres(Map<String, UUID> nombres) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_dropdown_item_1line, nombres.keySet().stream().collect(Collectors.toList()));
        acNombreFd.setAdapter(adapter);
        acNombreFs.setAdapter(adapter);
        Log.d("tag_dosimetro", "inicializarNombres");

    }

    private void inicializarDosisFd(FarmacoDetalle farmaco){
        // Dosis máxima
        dosisMaximaFd = farmaco.getPropiedades().getDosisMaxima();
        isValorDosisValid(dosisMaximaFd, etDosisActualFd, spUnidadActualFd, tilDosisActualFd);
        isValorDosisValid(dosisMaximaFd, etDosisNuevaFd, spUnidadNuevaFd, tilDosisNuevaFd);
        Log.d("tag_dosimetro", "inicializarDosisFd");
    }

    private void inicializarDosisFs(FarmacoDetalle farmaco){
        // Dosis máxima
        dosisMaximaFs = farmaco.getPropiedades().getDosisMaxima();
        isValorDosisValid(dosisMaximaFs, etDosisActualFs, spUnidadActualFs, tilDosisActualFs);
        isValorDosisValid(dosisMaximaFs, etDosisNuevaFs, spUnidadNuevaFs, tilDosisNuevaFs);
        Log.d("tag_dosimetro", "inicializarDosisFs");
    }
    private void onItemClickNombresFd(AdapterView<?> adapterView, View v, int i, long l) {
        // Se habilitan los campos de dosis
        etDosisActualFd.setEnabled(true);
        spUnidadActualFd.setEnabled(true);
        //---
        if (Objects.equals(btDosis.getText().toString(), "D")) {
            etDosisNuevaFd.setEnabled(true);
            spUnidadNuevaFd.setEnabled(true);
        }
        // Se obtiene el fármaco seleccionado para conocer su dosis máxima
        String nombreSeleccionado = acNombreFd.getText().toString();
        UUID uuidSeleccionado = dosimetroViewModel.getNombresFarmacos(v.getContext()).getValue().get(nombreSeleccionado);
        dosimetroViewModel.getFarmacoDetalle(getContext(), uuidSeleccionado).observe(getViewLifecycleOwner(), this::inicializarDosisFd);
        Log.d("tag_dosimetro", "onClickNombreFd");
    }

    private void onItemClickNombresFs(AdapterView<?> adapterView, View v, int i, long l) {
        // Se habilitan los campos de dosis
        etDosisActualFs.setEnabled(true);
        spUnidadActualFs.setEnabled(true);
        //---
        if (Objects.equals(btDosis.getText().toString(), "S")){
            etDosisNuevaFs.setEnabled(true);
            spUnidadNuevaFs.setEnabled(true);
        }
        // Se obtiene el fármaco seleccionado para conocer su dosis máxima
        String nombreSeleccionado = acNombreFs.getText().toString();
        UUID uuidSeleccionado = dosimetroViewModel.getNombresFarmacos(v.getContext()).getValue().get(nombreSeleccionado);
        dosimetroViewModel.getFarmacoDetalle(getContext(), uuidSeleccionado).observe(getViewLifecycleOwner(), this::inicializarDosisFs);
        Log.d("tag_dosimetro", "onClickNombreFs");
    }

    private boolean isValorDosisValid(Propiedad dosisMaxima, EditText etValor, Spinner spUnidad, TextInputLayout til){
        boolean result = true;
        String unidadSeleccionada = spUnidad.getSelectedItem().toString();
        if (!Objects.isNull(etValor.getText()) && !etValor.getText().toString().isEmpty()) {
            Double valorSeleccionado = Double.valueOf(etValor.getText().toString());
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
                til.setError("Valor máximo: " + valorMaximo + " " + unidadSeleccionada);
                result = false;
            } else {
                til.setError(null);
            }
        }
        return result;
    }

    private void onClickCalcular(View view){

        if(isValid()){
            DatosCalculoDosis datos = new DatosCalculoDosis();
            datos.setDosisActualFd(new PropiedadSimple(Double.valueOf(etDosisActualFd.getText().toString().trim()), spUnidadActualFd.getSelectedItem().toString()));
            datos.setDosisActualFs(new PropiedadSimple(Double.valueOf(etDosisActualFs.getText().toString().trim()), spUnidadActualFs.getSelectedItem().toString()));
            if(Objects.equals(btDosis.getText().toString(), "D")){
                datos.setDosisNuevaFd(new PropiedadSimple(Double.valueOf(etDosisNuevaFd.getText().toString().trim()), spUnidadNuevaFd.getSelectedItem().toString()));
            } else {
                datos.setDosisNuevaFs(new PropiedadSimple(Double.valueOf(etDosisNuevaFs.getText().toString().trim()), spUnidadNuevaFs.getSelectedItem().toString()));
            }

            SessionManager session = SessionManager.get(getContext());
            Call<PropiedadSimple> callFarmacos = ApiAdapter.getApiService().calcularNuevaDosis(session.getUserTokenType() + " " + session.getUserToken(), datos);
            callFarmacos.enqueue(new Callback<PropiedadSimple>() {
                @Override
                public void onResponse(@NonNull Call<PropiedadSimple> call, @NonNull Response<PropiedadSimple> response) {
                    if (response.isSuccessful()) {

                        PropiedadSimple nuevaDosis = response.body();
                        if(Objects.equals(btDosis.getText().toString(), "D")){
                            etDosisNuevaFs.setText(nuevaDosis.getValor().toString());
                            spUnidadNuevaFs.setSelection(0); //mg/dia
                            GeneralUtils.setBlinckEffect(titNuevaDosisFs, R.color.gris, R.color.azul_claro);
                            isValorDosisValid(dosisMaximaFs, etDosisNuevaFs, spUnidadNuevaFs, tilDosisNuevaFs);
                        } else {
                            etDosisNuevaFd.setText(nuevaDosis.getValor().toString());
                            spUnidadNuevaFd.setSelection(0); //mg/dia
                            GeneralUtils.setBlinckEffect(titNuevaDosisFd, R.color.gris, R.color.purple_200);
                            isValorDosisValid(dosisMaximaFd, etDosisNuevaFd, spUnidadNuevaFd, tilDosisNuevaFd);
                        }
                        Log.d("[CPMEDICAL][REST]", "Calcular dosis: " + nuevaDosis.getValor() + "  " + nuevaDosis.getUnidad());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<PropiedadSimple> call, @NonNull Throwable t) {
                    Log.d("[CPMEDICAL][REST][ERROR]", "Calcular receta: " + t.getMessage());
                    GeneralUtils.showErrorToast(getContext(), "Se ha producido un error.");
                }
            });
            
        } else {
            GeneralUtils.showErrorToast(getContext(), "Existen errores en el formulario");
        }
    }

    private void onClickReset(View view) {
        // Se reestablece el valor de todos los campos
        acNombreFd.setText(null);
        acNombreFs.setText(null);
        etDosisActualFd.setText(null);
        etDosisNuevaFd.setText(null);
        etDosisActualFs.setText(null);
        etDosisNuevaFs.setText(null);

        // Se eliminan posibles errores
        tilDosisActualFs.setError(null);
        tilDosisActualFd.setError(null);
        tilDosisNuevaFs.setError(null);
        tilDosisNuevaFd.setError(null);

        // Se deshabilitan los campos de dosis.
        deshabilitarCamposDosis();
    }

    private void onClickCambiarNuevaDosis(View view) {

        switch (btDosis.getText().toString()){

            case "D":
                // Cambio a S
                GeneralUtils.setBackgroundColorButton(btDosis, getResources(), R.color.azul_claro);
                btDosis.setText("S");
                titFd.setTextColor(ContextCompat.getColor(getContext(), R.color.gris));
                titNuevaDosisFd.setTextColor(ContextCompat.getColor(getContext(), R.color.gris));
                titFs.setTextColor(ContextCompat.getColor(getContext(), R.color.azul_claro));
                titNuevaDosisFs.setTextColor(ContextCompat.getColor(getContext(), R.color.azul_claro));
                GeneralUtils.setBlinckEffect(titFs, R.color.azul_claro, R.color.gris);

                etDosisNuevaFd.clearFocus();
                etDosisNuevaFd.setText(null);
                etDosisNuevaFd.setEnabled(false);
                spUnidadNuevaFd.setEnabled(false);
                tilDosisActualFd.setError(null);

                etDosisNuevaFs.setText(null);
                etDosisNuevaFs.setEnabled(true);
                spUnidadNuevaFs.setEnabled(true);
                tilDosisNuevaFs.setError(null);
                break;
            case "S":
                // Cambio a D
                GeneralUtils.setBackgroundColorButton(btDosis, getResources(), R.color.purple_200);
                btDosis.setText("D");
                titFs.setTextColor(ContextCompat.getColor(getContext(), R.color.gris));
                titNuevaDosisFs.setTextColor(ContextCompat.getColor(getContext(), R.color.gris));
                titFd.setTextColor(ContextCompat.getColor(getContext(), R.color.purple_200));
                titNuevaDosisFd.setTextColor(ContextCompat.getColor(getContext(), R.color.purple_200));
                GeneralUtils.setBlinckEffect(titFd, R.color.purple_200, R.color.gris);

                etDosisNuevaFs.clearFocus();
                etDosisNuevaFs.setText(null);
                etDosisNuevaFs.setEnabled(false);
                spUnidadNuevaFs.setEnabled(false);
                tilDosisActualFs.setError(null);

                etDosisNuevaFd.setText(null);
                etDosisNuevaFd.setEnabled(true);
                spUnidadNuevaFd.setEnabled(true);
                tilDosisNuevaFd.setError(null);
                break;
        }
    }

    private boolean isValid(){
        boolean result = !Objects.isNull(acNombreFd.getText()) && !acNombreFd.getText().toString().isEmpty();
        result = (!Objects.isNull(acNombreFs.getText()) && !acNombreFs.getText().toString().isEmpty()) && result;
        result = (!Objects.isNull(etDosisActualFd.getText()) && !etDosisActualFd.getText().toString().isEmpty()
                && isValorDosisValid(dosisMaximaFd, etDosisActualFd, spUnidadActualFd, tilDosisActualFd)) && result;
        result = (!Objects.isNull(etDosisActualFs.getText()) && !etDosisActualFs.getText().toString().isEmpty()
                && isValorDosisValid(dosisMaximaFs, etDosisActualFs, spUnidadActualFs, tilDosisActualFs)) && result;
        if(Objects.equals(btDosis.getText().toString(), "D")){
            result = (!Objects.isNull(etDosisNuevaFd.getText()) && !etDosisNuevaFd.getText().toString().isEmpty()
                    && isValorDosisValid(dosisMaximaFd, etDosisNuevaFd, spUnidadNuevaFd, tilDosisNuevaFd)) && result;
        } else {
            result = (!Objects.isNull(etDosisNuevaFs.getText()) && !etDosisNuevaFs.getText().toString().isEmpty()
                    && isValorDosisValid(dosisMaximaFs, etDosisNuevaFs, spUnidadNuevaFs, tilDosisNuevaFs)) && result;
        }
        return result;
    }
    private void deshabilitarCamposDosis(){
        etDosisActualFd.clearFocus();
        etDosisActualFd.setEnabled(false);
        spUnidadActualFd.setEnabled(false);
        //---
        etDosisNuevaFd.clearFocus();
        etDosisNuevaFd.setEnabled(false);
        spUnidadNuevaFd.setEnabled(false);
        //---
        etDosisActualFs.clearFocus();
        etDosisActualFs.setEnabled(false);
        spUnidadActualFs.setEnabled(false);
        //---
        etDosisNuevaFs.clearFocus();
        etDosisNuevaFs.setEnabled(false);
        spUnidadNuevaFs.setEnabled(false);
    }
}