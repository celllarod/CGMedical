package com.tfg.apptfg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.flexbox.AlignContent;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.tfg.apptfg.adapter.ListaPresentacionAdapter;
import com.tfg.apptfg.io.ApiAdapter;
import com.tfg.apptfg.io.request.DatosFarmacoCrear;
import com.tfg.apptfg.io.request.Propiedades;
import com.tfg.apptfg.io.response.FarmacoDetalle;
import com.tfg.apptfg.io.response.Propiedad;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleActivity extends AppCompatActivity {
    private TextView tvTitulo;
    private RecyclerView rvPresentaciones;
    private ListaPresentacionAdapter presentacionesAdapter;
    private List<Propiedad> presentaciones = new ArrayList<>();
    private TextView tvValorDosis;
    private TextView tvUnidadDosis;
    private LinearLayout lyEditDelete;
    private MaterialButton btDeleteFarmaco;
    private MaterialButton btEditFarmaco;
    private SessionManager session;
    private FarmacoDetalle farmaco;
    private BottomSheetDialog bottomSheetDialog;
    private ConstraintLayout clForm;

    // Editar
    private TextView titNombre;
    private LinearLayout presentacionListLayout;
    private MaterialButton btAdd;
    private EditText etEtValorDosisEditar;
    private AppCompatSpinner spUnidadDosisEditar;

    private static final String ROL_GESTOR = "GESTOR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        tvTitulo = findViewById(R.id.editar_farmaco_title);
        rvPresentaciones = findViewById(R.id.rv_presentaciones);
        presentacionesAdapter = new ListaPresentacionAdapter(presentaciones);
        tvValorDosis = findViewById(R.id.ly_dosis).findViewById(R.id.tv_valor);
        tvUnidadDosis = findViewById(R.id.ly_dosis).findViewById(R.id.tv_unidad);
        btDeleteFarmaco = findViewById(R.id.bt_editar_farmaco_delete);
        btEditFarmaco = findViewById(R.id.bt_editar_farmaco_update);
        lyEditDelete = findViewById(R.id.ly_edit_delete);
        session = SessionManager.get(this);

        if (ROL_GESTOR.equals(session.getUserRol())) {
            lyEditDelete.setVisibility(View.VISIBLE);
            // TODO: diálogo estás seguro y redirigir a pantalla catalodo
            btDeleteFarmaco.setOnClickListener(this::onClickCancelar);
            btEditFarmaco.setOnClickListener(this::onClickEditar);
        }

        inicializarElementos();
    }


    private void onClickCancelar(View v) {
        Call<Void> callDelete = ApiAdapter.getApiService().deleteFarmaco(session.getUserTokenType() + " " + session.getUserToken(), String.valueOf(farmaco.getId()));
        callDelete.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("[CPMEDICAL][REST]", "Eliminar fármaco éxito");
                    GeneralUtils.showSuccesToast(getApplicationContext(), "Fármaco eliminado con éxito");
                    finish();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.d("[CPMEDICAL][REST]", "Eliminar fármaco error");
                GeneralUtils.showSuccesToast(getApplicationContext(), "ENo se ha podido eliminar el fármaco");

            }
        });
    }

    private void onClickEditar(View v) {
        inicializarDialogo();
    }

    private void inicializarDialogo() {

        bottomSheetDialog = new BottomSheetDialog(this, R.style.DialogStyle);
        View formView = getLayoutInflater().inflate(R.layout.bottom_sheet_editar_farmaco_form, clForm, false);

        // Obtenemos componentes del dialog
        titNombre = formView.findViewById(R.id.tit_nombre);
        btAdd = formView.findViewById(R.id.bt_add_pre_editar);
        presentacionListLayout = formView.findViewById(R.id.layout_lista_pre);
        etEtValorDosisEditar = formView.findViewById(R.id.et_valor_dosis);
        spUnidadDosisEditar = formView.findViewById(R.id.sp_unidad_dosis);
        MaterialButton btSubmit = formView.findViewById(R.id.bt_submit_editar);

        // Inicializamos valor de los componentes del dialog
        String[] unidadesDosisList = getResources().getStringArray(R.array.unidades_dosis);
        String[] unidadesPresentacionList = getResources().getStringArray(R.array.unidades_presentacion);

        // Nombre
        titNombre.setText(farmaco.getNombre());

        // Presentaciones
        farmaco.getPropiedades().getPresentaciones().forEach(p -> {
            View presentacionView = addView(formView);
            EditText etValorPresentacionEditar= presentacionView.findViewById(R.id.et_valor_pre);
            AppCompatSpinner spUnidadPresentacionEditar = presentacionView.findViewById(R.id.sp_unidad_pre);
            etValorPresentacionEditar.setText(p.getValor().toString());
            spUnidadPresentacionEditar.setSelection(Arrays.asList(unidadesPresentacionList).indexOf(p.getUnidad()));
        });
        btAdd.setOnClickListener(v -> addView(formView));

        // Dosis
        etEtValorDosisEditar.setText(String.valueOf(farmaco.getPropiedades().getDosisMaxima().getValor()));
        spUnidadDosisEditar.setSelection(Arrays.asList(unidadesDosisList).indexOf(farmaco.getPropiedades().getDosisMaxima().getUnidad()));

        // Botón de aceptar
        btSubmit.setOnClickListener(this::onClickSubmit);

        // Mostramos el diálogo
        bottomSheetDialog.setContentView(formView);
        bottomSheetDialog.show();
    }

    private void onClickSubmit(View view) {
//        this.closeKeyboard();
        getPresentaciones();
        if(isValid()){
            Log.d("[CPMEDICAL][Create farmaco]", "Fármaco a registrar válido.");
            Propiedades propiedades = new Propiedades();
            Propiedad dosisMaxima = new Propiedad();
            dosisMaxima.setValor(Double.parseDouble(etEtValorDosisEditar.getText().toString()));
            dosisMaxima.setUnidad(spUnidadDosisEditar.getSelectedItem().toString());
            propiedades.setDosisMaxima(dosisMaxima);
            propiedades.setPresentaciones(presentaciones.stream().collect(Collectors.toSet()));

            SessionManager session = SessionManager.get(this);
            Call<FarmacoDetalle> callEditarFarmaco = ApiAdapter.getApiService().updateFarmaco(session.getUserTokenType() + " " + session.getUserToken(), farmaco.getId().toString(), propiedades);
            callEditarFarmaco.enqueue(new Callback<FarmacoDetalle>() {
                @Override
                public void onResponse(@NonNull Call<FarmacoDetalle> call, @NonNull Response<FarmacoDetalle> response) {
                    if(response.isSuccessful()) {
                        Log.d("[CPMEDICAL][REST]", "Fármaco registrado");
                        GeneralUtils.showSuccesToast(getApplicationContext(), "Fármaco actualizado con éxito");
                        farmaco = response.body();
                        reinicializarFarmacoEditado();
                        bottomSheetDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<FarmacoDetalle> call, @NonNull Throwable t) {
                    Log.d("[CPMEDICAL][REST][ERROR]",  "Error al registrar fármaco " + t.getMessage());
                }

            });
        }

    }

    private void onClickDeletePre(View v, View presentacionView, View formView) {
        removeView(presentacionView);
        if(presentacionListLayout.getChildCount() == 0) {
            formView.findViewById(R.id.sr_lista_pre_editar).setVisibility(View.GONE);
        }
    }

    private void inicializarElementos() {

        // Obtener fármaco
        Bundle extras = getIntent().getExtras();
        if (!Objects.isNull(extras)) {
            UUID id = UUID.fromString(extras.getString("ID"));
            Log.d("[CPMedical][Debug]", "ID: " + id.toString());
            Call<FarmacoDetalle> callFarmacos = ApiAdapter.getApiService().getFarmaco(session.getUserTokenType() + " " + session.getUserToken(), id.toString());
            callFarmacos.enqueue(new Callback<FarmacoDetalle>() {
                @Override
                public void onResponse(@NonNull Call<FarmacoDetalle> call, @NonNull Response<FarmacoDetalle> response) {
                    if (response.isSuccessful()) {
                        farmaco = response.body();
                        Propiedad dm = farmaco.getPropiedades().getDosisMaxima();
                        List<Propiedad> presentaciones = farmaco.getPropiedades().getPresentaciones().stream().collect(Collectors.toList());
                        Log.d("[CPMEDICAL][REST]", "Obtener fármaco a partir de su ID: " + farmaco.getNombre());

                        // Setear valores
                        // Nombre del fármaco
                        tvTitulo.setText(farmaco.getNombre());

                        // Presentaciones
                        FlexboxLayoutManager flexLayoutManager = new FlexboxLayoutManager(getApplicationContext());
                        flexLayoutManager.setFlexWrap(FlexWrap.WRAP);
                        rvPresentaciones.setLayoutManager(flexLayoutManager);
                        presentacionesAdapter = new ListaPresentacionAdapter(presentaciones);
                        rvPresentaciones.setAdapter(presentacionesAdapter);

                        // Dosis máxima
                        tvValorDosis.setText(dm.getValor().toString());
                        tvUnidadDosis.setText(dm.getUnidad());

                    }
                }

                @Override
                public void onFailure(@NonNull Call<FarmacoDetalle> call, @NonNull Throwable t) {
                    Log.d("[CPMEDICAL][REST][ERROR]", "Obtener fármaco a partir de su ID: " + t.getMessage());
                }
            });
        }
    }


    private void reinicializarFarmacoEditado(){
        // Presentaciones
        presentacionesAdapter = new ListaPresentacionAdapter(farmaco.getPropiedades().getPresentaciones().stream().collect(Collectors.toList()));
        rvPresentaciones.setAdapter(presentacionesAdapter);

        // Dosis máxima
        tvValorDosis.setText(farmaco.getPropiedades().getDosisMaxima().getValor().toString());
        tvUnidadDosis.setText(farmaco.getPropiedades().getDosisMaxima().getUnidad());
    }

    private View addView(View formView) {
        final View presentacionView = getLayoutInflater().inflate(R.layout.presentacion_comercial, presentacionListLayout, false);
        MaterialButton btDelete = presentacionView.findViewById(R.id.bt_delete_pre);
        btDelete.setOnClickListener( v -> onClickDeletePre(v, presentacionView, formView));
        presentacionListLayout.addView(presentacionView);
        if(presentacionListLayout.getChildCount() >= 0) {
            formView.findViewById(R.id.sr_lista_pre_editar).setVisibility(View.VISIBLE);
        }
        return presentacionView;
    }

    private void getPresentaciones(){
        int countChild = presentacionListLayout.getChildCount();
        presentaciones.clear();
        Log.d("[CPMedical][GetPresentaciones]", "Hijos: " + countChild);
        if (countChild != 0) {
            for (int i=0; i<countChild; i++) {
                View presentacionView = presentacionListLayout.getChildAt(i);
                EditText etValor = presentacionView.findViewById(R.id.et_valor_pre);
                AppCompatSpinner spUnidad = presentacionView.findViewById(R.id.sp_unidad_pre);
                String valor = etValor.getText().toString();
                String unidad = spUnidad.getSelectedItem().toString();

                if (!valor.isEmpty() && !unidad.isEmpty()) {
                    Propiedad pre = new Propiedad(null, Double.parseDouble(valor), unidad );
                    presentaciones.add(pre);
                    Log.d("[CPMedical][Validation]", "Presentación " + i + ": " + valor + " -- " + unidad);
                }
            }
        }
    }

    private void removeView(View view){
        presentacionListLayout.removeView(view);
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private boolean isValid(){
        boolean result = true;
        result = ValidationUtils.validatePresentaciones(this, presentaciones.stream().collect(Collectors.toSet())) && result;
        result = ValidationUtils.validateDosisMaxima(etEtValorDosisEditar, spUnidadDosisEditar) && result;
        return result;
    }
}