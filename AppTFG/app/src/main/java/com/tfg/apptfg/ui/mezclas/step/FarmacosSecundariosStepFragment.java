package com.tfg.apptfg.ui.mezclas.step;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.tfg.apptfg.R;
import com.tfg.apptfg.adapter.ListaFarmacosSecundariosAdapter;
import com.tfg.apptfg.databinding.FragmentStepFarmacosSecundariosBinding;
import com.tfg.apptfg.io.request.FarmacoSecundario;
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

public class FarmacosSecundariosStepFragment extends Fragment {

    private FragmentStepFarmacosSecundariosBinding binding;
    private FarmacosSecundariosStepViewModel farmacosSecundariosViewModel;
    private MezclasViewModel mezclasViewModel;
    private MaterialButton btAdd;
    private RecyclerView rvFarmacosList;
    private ConstraintLayout clForm;
    private AutoCompleteTextView acNombreFs;
    private EditText etValorDosis;
    private AppCompatSpinner spUnidadDosis;
    private AutoCompleteTextView acPresentacion;
    private Propiedad dosisMaxima;
    private MaterialButton btSubmit;
    private List<FarmacoSecundario> fsList;
    private BottomSheetDialog bottomSheetDialog;
    private ListaFarmacosSecundariosAdapter listaFsAdapter;


//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        farmacosSecundariosViewModel = new ViewModelProvider(this).get(FarmacosSecundariosStepViewModel.class);
        mezclasViewModel = new ViewModelProvider(requireParentFragment()).get(MezclasViewModel.class);
        binding = FragmentStepFarmacosSecundariosBinding.inflate(inflater, container, false);
       // farmacosSecundariosViewModel.getNombresFarmacos(getContext()).observe(getViewLifecycleOwner(), this::inicializarNombres);

        View root = binding.getRoot();
        btAdd = binding.btAdd;
        rvFarmacosList = binding.contenedorLista;

        if (Objects.isNull(mezclasViewModel.getFarmacosSecundariosList())){
            fsList = new ArrayList<>();
        } else {
            fsList = mezclasViewModel.getFarmacosSecundariosList().getValue();
        }

        rvFarmacosList.setLayoutManager(new LinearLayoutManager(getContext()));
        listaFsAdapter = new ListaFarmacosSecundariosAdapter(fsList);
        rvFarmacosList.setAdapter(listaFsAdapter);

        btAdd.setOnClickListener(view -> onClickAdd(view));
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        mezclasViewModel.setFarmacosSecundariosList(listaFsAdapter.getFarmacosList());
    }

    public void onClickAdd(View view) {
        bottomSheetDialog = new BottomSheetDialog(getContext());

        View formView = getLayoutInflater().inflate(R.layout.bottom_sheet_farmaco_secundario_form, clForm, false);
        acNombreFs = formView.findViewById(R.id.ac_nombre_fs);
        etValorDosis= formView.findViewById(R.id.et_valor_dosis);
        spUnidadDosis = formView.findViewById(R.id.sp_unidad_dosis);
        acPresentacion = formView.findViewById(R.id.ac_presentacion_fs);
        btSubmit = formView.findViewById(R.id.bt_submit);
        farmacosSecundariosViewModel.getNombresFarmacos(getContext()).observe(getViewLifecycleOwner(), this::inicializarNombres);

        fsList = new ArrayList<>();
        btSubmit.setOnClickListener(this::onClickSubmit);
        acNombreFs.setOnItemClickListener(this::onItemClickNombres);
        etValorDosis.setOnClickListener(this::onClickDosisValor);
        etValorDosis.setImeOptions(EditorInfo.IME_ACTION_GO);
        spUnidadDosis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                onClickDosisValor(view);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        bottomSheetDialog.setContentView(formView);
        bottomSheetDialog.show();
    }

    private void inicializarNombres(Map<String, UUID> nombres) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_dropdown_item_1line, nombres.keySet().stream().collect(Collectors.toList()));
        acNombreFs.setAdapter(adapter);
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

        // Dosis máxima
        dosisMaxima = farmaco.getPropiedades().getDosisMaxima();
        isValorDosisValid();
    }

    public void onItemClickNombres(AdapterView<?> adapterView, View view, int i, long l) {
        String nombreSeleccionado = acNombreFs.getText().toString();
        UUID uuidSeleccionado = farmacosSecundariosViewModel.getNombresFarmacos(view.getContext()).getValue().get(nombreSeleccionado);
        farmacosSecundariosViewModel.getFarmacoDetalle(getContext(), uuidSeleccionado).observe(getViewLifecycleOwner(), this::inicializarPropiedades);
    }

    public void onClickDosisValor(View view) {
        etValorDosis.setError(null);
        isValorDosisValid();
    }

    public void onClickDosisUnidad(AdapterView<?> parent, View view, int position, long id) {
        // TODO: asociar al spinner dosis unidad
        onClickDosisValor(view);
    }

    public void onClickSubmit(View view) {
        if (isValid()) {
            FarmacoSecundario fs = new FarmacoSecundario();
            fs.setNombre(acNombreFs.getText().toString());
            fs.setPresentacion(convertirPresentacion(acPresentacion.getText().toString()));
            fs.setDosis(new PropiedadSimple(Double.valueOf(etValorDosis.getText().toString()), spUnidadDosis.getSelectedItem().toString()));
            bottomSheetDialog.dismiss();
//            rvListaFarmacos.setLayoutManager(new GridLayoutManager(getContext(), 1));
            farmacosSecundariosViewModel.addFsItemList(fs);

            fsList = farmacosSecundariosViewModel.getFsList().getValue();
            listaFsAdapter.setFarmacosList(fsList);
            listaFsAdapter.notifyDataSetChanged();
//            rvFarmacosList.setLayoutManager(new LinearLayoutManager(getContext()));
//            listaFsAdapter = new ListaFarmacosSecundariosAdapter(f);
//            rvFarmacosList.setAdapter(listaFsAdapter);
        } else {
            // TODO: toast hay errores
        }
    }

    private boolean isValid(){
        boolean result = true;
        result = !isDosisEmpty() && result;
        result = isValorDosisValid() && result;
        result = !acNombreFs.getText().toString().isEmpty() && result;
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
            etValorDosis.setError("Este campo no puede ir vacío");
            result = true;
        }
        return result;
    }

    private PropiedadSimple convertirPresentacion(String presentacion){
        String[] presentacionSplit = presentacion.split(" ");
        return new PropiedadSimple(Double.valueOf(presentacionSplit[0]), presentacionSplit[1]);
    }
}