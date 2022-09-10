package com.tfg.apptfg.ui.catalogo;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tfg.apptfg.SessionManager;
import com.tfg.apptfg.io.ApiAdapter;
import com.tfg.apptfg.io.response.FarmacoResumen;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CatalogoViewModel extends ViewModel{

    private MutableLiveData<List<FarmacoResumen>> farmacosList;

    public CatalogoViewModel(){  }

    public MutableLiveData<List<FarmacoResumen>> getFarmacos(Context cxt) {
        farmacosList = new MutableLiveData<>();
        obtenerFarmacos(cxt);
        return farmacosList;
    }

    public void obtenerFarmacos(Context ctx) {
        SessionManager session = SessionManager.get(ctx);
        Call<List<FarmacoResumen>> callFarmacos = ApiAdapter.getApiService().getFarmacos(session.getUserTokenType() + " " + session.getUserToken());
        callFarmacos.enqueue(new Callback<List<FarmacoResumen>>() {
            @Override
            public void onResponse(@NonNull Call<List<FarmacoResumen>> call, @NonNull Response<List<FarmacoResumen>> response) {
                if (response.isSuccessful()) {
                    farmacosList.setValue(response.body());
                    Log.d("[CPMEDICAL][REST]", "Obtener listado fármacos: " + farmacosList.getValue().size());
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<FarmacoResumen>> call, @NonNull Throwable t) {
                Log.d("[CPMEDICAL][REST][ERROR]", "Obtener listado fármacos: " + t.getMessage());
            }
        });
    }
}
