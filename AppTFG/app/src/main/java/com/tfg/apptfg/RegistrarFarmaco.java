package com.tfg.apptfg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;
import com.tfg.apptfg.io.ApiAdapter;
import com.tfg.apptfg.io.request.DatosFarmacoCrear;
import com.tfg.apptfg.io.request.Propiedades;
import com.tfg.apptfg.io.response.FarmacoDetalle;
import com.tfg.apptfg.io.response.Propiedad;
import com.tfg.apptfg.ui.catalogo.CatalogoFragment;

import java.util.HashSet;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrarFarmaco extends AppCompatActivity {
    EditText etNombre;
    LinearLayout presentacionListLayout;
    Set<Propiedad> presentaciones = new HashSet<>( );
    MaterialButton btAdd;
    EditText etValorDosis;
    AppCompatSpinner spUnidadDosis;
    MaterialButton btSubmit;
    MaterialButton btCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_farmaco);
        etNombre = findViewById(R.id.et_add_farmaco_name);
        btAdd = findViewById(R.id.bt_add_pre);
        etValorDosis = findViewById(R.id.et_valor_dosis);
        spUnidadDosis = findViewById(R.id.sp_unidad_dosis);
        btSubmit = findViewById(R.id.bt_add_farmaco_submit);
        btCancel = findViewById(R.id.bt_add_farmaco_cancel);
        presentacionListLayout = findViewById(R.id.layout_lista_pre);

        // Se añade un elemento a la lista de presentación comercial
        this.addView();
        btAdd.setOnClickListener(v -> addView());
        btCancel.setOnClickListener(v -> {
            this.closeKeyboard();
            goToCatalogoFragment();
        }); // TODO: dialog ¿Seguro que desea volver? Se eliminarán todos los datos
        btSubmit.setOnClickListener(v -> {
            this.closeKeyboard();
            this.getPresentaciones();
            if(this.isValid()){
                Log.d("[CPMEDICAL][Create farmaco]", "Fármaco a registrar válido.");
                DatosFarmacoCrear datosFarmaco = new DatosFarmacoCrear();
                datosFarmaco.setNombre(etNombre.getText().toString().trim());
                Propiedades propiedades = new Propiedades();
                Propiedad dosisMaxima = new Propiedad();
                dosisMaxima.setValor(Double.parseDouble(etValorDosis.getText().toString()));
                dosisMaxima.setUnidad(spUnidadDosis.getSelectedItem().toString());
                propiedades.setDosisMaxima(dosisMaxima);
                propiedades.setPresentaciones(presentaciones);
                datosFarmaco.setPropiedades(propiedades);

                SessionManager session = SessionManager.get(this);
                Call<FarmacoDetalle> callCreateFarmaco = ApiAdapter.getApiService().createFarmaco(session.getUserTokenType() + " " + session.getUserToken(), datosFarmaco);
                callCreateFarmaco.enqueue(new Callback<FarmacoDetalle>() {
                    @Override
                    public void onResponse(@NonNull Call<FarmacoDetalle> call, @NonNull Response<FarmacoDetalle> response) {
                        if(response.isSuccessful()) {
                            Log.d("[CPMEDICAL][REST]", "Fármaco registrado");
                            GeneralUtils.showSuccesToast(v.getContext(), "Fármaco registrado con éxito");
                            // TODO: Toast: fármaco registrado con éxito fondo verde redondeado
                            goToCatalogoFragment();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<FarmacoDetalle> call, @NonNull Throwable t) {
                        Log.d("[CPMEDICAL][REST][ERROR]",  "Error al registrar fármaco " + t.getMessage());
                    }

                });
            }
        });
    }

    private void addView() {
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
    }

    private void removeView(View view){
        presentacionListLayout.removeView(view);
    }

    private void goToCatalogoFragment(){
        // TODO: ver problema error al ir hacia atrás
        CatalogoFragment catalogoFragment =new CatalogoFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.catalogo_fragment, catalogoFragment).commit();

    }

    private boolean isValid(){
        boolean result = true;
        result = ValidationUtils.validateNombreFarmaco(etNombre) && result;
        result = ValidationUtils.validatePresentaciones(this, presentaciones) && result;
        result = ValidationUtils.validateDosisMaxima(etValorDosis, spUnidadDosis) && result;
        return result;
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

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


}

