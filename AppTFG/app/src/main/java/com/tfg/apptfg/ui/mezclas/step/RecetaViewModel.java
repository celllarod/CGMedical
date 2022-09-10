package com.tfg.apptfg.ui.mezclas.step;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tfg.apptfg.SessionManager;
import com.tfg.apptfg.io.ApiAdapter;
import com.tfg.apptfg.io.request.DatosCalculoMezclas;
import com.tfg.apptfg.io.response.Carga;
import com.tfg.apptfg.io.response.FarmacoResumen;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecetaViewModel extends ViewModel {

    private MutableLiveData<List<Carga>> receta;

    public RecetaViewModel(){  }

    public MutableLiveData<List<Carga>> getReceta(Context cxt, DatosCalculoMezclas datos) {
        if (Objects.isNull(receta)){
            receta = new MutableLiveData<>();
            obtenerReceta(cxt, datos);
        }
        return receta;
    }

    public void obtenerReceta(Context ctx, DatosCalculoMezclas datos) {
        SessionManager session = SessionManager.get(ctx);
        Call<List<Carga>> callFarmacos = ApiAdapter.getApiService().calcularReceta(session.getUserTokenType() + " " + session.getUserToken(), datos);
        callFarmacos.enqueue(new Callback<List<Carga>>() {
            @Override
            public void onResponse(@NonNull Call<List<Carga>> call, @NonNull Response<List<Carga>> response) {
                if (response.isSuccessful()) {
                    receta.setValue(response.body());
                    Log.d("[CPMEDICAL][REST]", "Calcular receta: " + receta.getValue().size());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Carga>> call, @NonNull Throwable t) {
                Log.d("[CPMEDICAL][REST][ERROR]", "Calcular receta: " + t.getMessage());
            }
        });
    }

}