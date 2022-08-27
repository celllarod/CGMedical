package com.tfg.apptfg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.tfg.apptfg.io.ApiAdapter;
import com.tfg.apptfg.io.response.FarmacoDetalle;
import com.tfg.apptfg.io.response.Propiedad;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleActivity extends AppCompatActivity {
    TextView tvTitulo;
    EditText etNombre;
    TextView txTituloNombre;
    TextInputLayout tilNombre;
    LinearLayout presentacionListLayout;
    Set<Propiedad> presentaciones = new HashSet<>( );
    MaterialButton btAdd;
    EditText etValorDosis;
    AppCompatSpinner spUnidadDosis;
    MaterialButton btSubmit;
    MaterialButton btCancel;
    MaterialButton btDeleteFarmaco;
    MaterialButton btEditFarmaco;
    SessionManager session;
    FarmacoDetalle farmaco;
    LinearLayout lyEditDelete;
    static final String ROL_GESTOR = "GESTOR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        tvTitulo = findViewById(R.id.editar_farmaco_title);
        txTituloNombre = findViewById(R.id.tx_tit_nombre);
        etNombre = findViewById(R.id.et_add_farmaco_name);
        tilNombre = findViewById(R.id.til_add_farmaco_name);
        btAdd = findViewById(R.id.bt_add_pre);
        etValorDosis = findViewById(R.id.et_valor_dosis);
        spUnidadDosis = findViewById(R.id.sp_unidad_dosis);
        btSubmit = findViewById(R.id.bt_add_farmaco_submit);
        btCancel = findViewById(R.id.bt_add_farmaco_cancel);
        btDeleteFarmaco = findViewById(R.id.bt_editar_farmaco_delete);
        btEditFarmaco = findViewById(R.id.bt_editar_farmaco_update);
        lyEditDelete = findViewById(R.id.ly_edit_delete);
        presentacionListLayout = findViewById(R.id.layout_lista_pre);
        session = SessionManager.get(this);

        if (ROL_GESTOR.equals(session.getUserRol())){
            lyEditDelete.setVisibility(View.VISIBLE);
            btDeleteFarmaco.setOnClickListener(v -> {
                // TODO: llamar  endpoint delete y diálogo estás seguro y redirigir a pantalla catalodo
            });
            btEditFarmaco.setOnClickListener(v -> {
                // TODO: llamar endpoint update y cargar vista update
            });
        }

        // Se ocultan elementos
        // TDO: hacer al final
        btAdd.setVisibility(View.GONE);
        btSubmit.setVisibility(View.GONE);
        btCancel.setVisibility(View.GONE);

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
                        Set<Propiedad> pre = farmaco.getPropiedades().getPresentaciones();
                        String[] unidadesDosisList = getResources().getStringArray(R.array.unidades_dosis);
                        String[] unidadesPresentacionList = getResources().getStringArray(R.array.unidades_presentacion);
                        Log.d("[CPMEDICAL][REST]", "Obtener fármaco a partir de si ID: " + farmaco.getNombre());

                        // Setear valores y deshabilitar edición
                        tvTitulo.setText(farmaco.getNombre());
                        tilNombre.setVisibility(View.GONE);
                        txTituloNombre.setVisibility(View.GONE);

                        pre.forEach(p -> {
                            View presentacionView = addView();
                            TextInputLayout tilPresentacion = presentacionView.findViewById(R.id.til_valor_pre);
                            EditText etValorPresentacion = presentacionView.findViewById(R.id.et_valor_pre);
                            AppCompatSpinner spUnidadPresentacion = presentacionView.findViewById(R.id.sp_unidad_pre);
                            MaterialButton btDelete = presentacionView.findViewById(R.id.bt_delete_pre);
                            etValorPresentacion.setText(p.getValor().toString());
                            spUnidadPresentacion.setSelection(Arrays.asList(unidadesPresentacionList).indexOf(dm.getUnidad()));
                            btDelete.setVisibility(View.GONE);
                            tilPresentacion.setEnabled(false);
                            spUnidadPresentacion.setEnabled(false);


                        });

                        etValorDosis.setText(dm.getValor().toString());
                        etValorDosis.setEnabled(false);
                        spUnidadDosis.setSelection(Arrays.asList(unidadesDosisList).indexOf(dm.getUnidad()));
                        spUnidadDosis.setEnabled(false);

                    }
                }

                @Override
                public void onFailure(@NonNull Call<FarmacoDetalle> call, @NonNull Throwable t) {
                    Log.d("[CPMEDICAL][REST][ERROR]", "Obtener fármaco a partir de su ID: " + t.getMessage());
                }
            });
        }


    }

    private View addView() {
        final View presentacionView = getLayoutInflater().inflate(R.layout.presentacion_comercial, presentacionListLayout, false);
        EditText etValor = presentacionView.findViewById(R.id.et_valor_pre);
        MaterialButton btDelete = presentacionView.findViewById(R.id.bt_delete_pre);

        btDelete.setOnClickListener( v -> {
            removeView(presentacionView);
            if(presentacionListLayout.getChildCount() == 0) {
                findViewById(R.id.sr_lista_pre).setVisibility(View.GONE);
            }
        });

        presentacionListLayout.addView(presentacionView);

        if(presentacionListLayout.getChildCount() >= 0) {
            findViewById(R.id.sr_lista_pre).setVisibility(View.VISIBLE);
        }

        return presentacionView;
    }

    private void removeView(View view){
        presentacionListLayout.removeView(view);
    }
}