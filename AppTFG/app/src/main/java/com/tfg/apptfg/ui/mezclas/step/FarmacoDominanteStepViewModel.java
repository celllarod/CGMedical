package com.tfg.apptfg.ui.mezclas.step;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tfg.apptfg.SessionManager;
import com.tfg.apptfg.io.ApiAdapter;
import com.tfg.apptfg.io.response.FarmacoDetalle;
import com.tfg.apptfg.io.response.FarmacoSimple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FarmacoDominanteStepViewModel extends ViewModel {

    private MutableLiveData<Map<String, UUID>> nombresFarmacosMap;
    private MutableLiveData<FarmacoDetalle> farmaco;

    public FarmacoDominanteStepViewModel(){  }

    public MutableLiveData<Map<String, UUID>> getNombresFarmacos(Context cxt) {
        if (Objects.isNull(nombresFarmacosMap)){
            nombresFarmacosMap = new MutableLiveData<>();
            obtenerNombresFarmacos(cxt);
        }
        return nombresFarmacosMap;
    }

    public MutableLiveData<FarmacoDetalle> getFarmacoDetalle(Context ctx, UUID uuid) {
//        UUID uuid = nombresFarmacosMap.getValue().get(nombre);
        farmaco = new MutableLiveData<>();
        obtenerFarmacoById(ctx, uuid);
        return farmaco;
    }

    public void obtenerNombresFarmacos(Context ctx) {
        SessionManager session = SessionManager.get(ctx);
        Call<List<FarmacoSimple>> callFarmacos = ApiAdapter.getApiService().getNombresFarmacos(session.getUserTokenType() + " " + session.getUserToken());
        callFarmacos.enqueue(new Callback<List<FarmacoSimple>>() {
            @Override
            public void onResponse(@NonNull Call<List<FarmacoSimple>> call, @NonNull Response<List<FarmacoSimple>> response) {
                if (response.isSuccessful()) {
                    HashMap<String, UUID> map1 = new HashMap<>();
                    response.body().forEach(f -> map1.put(f.getNombre(), f.getId()));

                    Log.d("[CPMEDICAL][REST]", "Obtener listado nombres fármacos: " + map1);
//                    HashMap<String, UUID> map2 = response.body().stream().collect(Collectors.toMap(FarmacoSimple::getNombre, FarmacoSimple::getId, (x, y)
//                            -> y, HashMap::new));
                    nombresFarmacosMap.setValue(map1);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<FarmacoSimple>> call, @NonNull Throwable t) {
                Log.d("[CPMEDICAL][REST][ERROR]", "Obtener listado nombres fármacos: " + t.getMessage());
            }

        });
    }

    public void obtenerFarmacoById(Context ctx, UUID idFarmaco) {
        SessionManager session = SessionManager.get(ctx);
        Call<FarmacoDetalle> callFarmacos = ApiAdapter.getApiService().getFarmaco(session.getUserTokenType() + " " + session.getUserToken(), idFarmaco.toString());
        callFarmacos.enqueue(new Callback<FarmacoDetalle>() {
            @Override
            public void onResponse(@NonNull Call<FarmacoDetalle> call, @NonNull Response<FarmacoDetalle> response) {
                if (response.isSuccessful()) {
                    farmaco.setValue(response.body());
                    Log.d("[CPMEDICAL][REST]", "Obtener detalle fármaco: " + farmaco.getValue().getNombre());
                }
            }

            @Override
            public void onFailure(@NonNull Call<FarmacoDetalle> call, @NonNull Throwable t) {
                Log.d("[CPMEDICAL][REST][ERROR]", "Obtener detalle fármaco: " + t.getMessage());
            }

        });
    }
}