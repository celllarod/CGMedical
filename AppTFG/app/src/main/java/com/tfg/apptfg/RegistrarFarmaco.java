package com.tfg.apptfg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;
import com.tfg.apptfg.io.response.Propiedad;

import java.util.ArrayList;
import java.util.List;

public class RegistrarFarmaco extends AppCompatActivity {
    List<Propiedad> presentacionesList = new ArrayList<>( );
    MaterialButton btAdd;
    LinearLayout presentacionListLayout;
    List<String> unidades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_farmaco);
        btAdd = findViewById(R.id.bt_add_pre);

        presentacionListLayout = findViewById(R.id.layout_lista_pre);

        // Primer elemento (obligatorio)
        this.addView();

        btAdd.setOnClickListener(view -> addView());


    }

    private void addView() {
        final View presentacionView = getLayoutInflater().inflate(R.layout.presentacion_comercial, presentacionListLayout, false);
        EditText etValor = presentacionView.findViewById(R.id.et_valor_pre);
        AppCompatSpinner spUnidad = presentacionView.findViewById(R.id.sp_unidad_pre);
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
}

