package com.tfg.apptfg.io;

import com.tfg.apptfg.io.request.DatosCalculoDosis;
import com.tfg.apptfg.io.request.DatosCalculoMezclas;
import com.tfg.apptfg.io.request.DatosFarmacoCrear;
import com.tfg.apptfg.io.request.LoginUser;
import com.tfg.apptfg.io.request.PropiedadSimple;
import com.tfg.apptfg.io.request.Propiedades;
import com.tfg.apptfg.io.request.SignUpUser;
import com.tfg.apptfg.io.response.Carga;
import com.tfg.apptfg.io.response.FarmacoDetalle;
import com.tfg.apptfg.io.response.FarmacoResumen;
import com.tfg.apptfg.io.response.FarmacoSimple;
import com.tfg.apptfg.io.response.JwtResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    /** Registro de usuario */
    @POST("auth/signup")
    Call<JwtResponse> signUpUser( @Body SignUpUser signup );

    /** Inicio de sesión de usuario */
    @POST("auth/signin")
    Call<JwtResponse> signInUser( @Body LoginUser sigin );

    /** Obtención del listado de hospitales existentes en el sistema */
    @GET("auth/hospitales")
    Call<List<String>> getHospitales();

    /** Obtención de los fármacos registrados en el hospital del usuario actual */
    @GET("farmacos")
    Call<List<FarmacoResumen>> getFarmacos(@Header("Authorization") String token);

    /** Creación de fármaco */
    @POST("farmacos")
    Call<FarmacoDetalle> createFarmaco(@Header("Authorization") String token, @Body DatosFarmacoCrear datos);

    /** Obtención de un fármaco a partir de su identificador */
    @GET("farmacos/{id}")
    Call<FarmacoDetalle> getFarmaco(@Header("Authorization") String token, @Path("id") String id);

    /** Eliminar fármaco */
    @DELETE("farmacos/{id}")
    Call<Void> deleteFarmaco(@Header("Authorization") String token, @Path("id") String id);

    /** Actualizar fármaco */
    @PUT("farmacos/{id}")
    Call<FarmacoDetalle> updateFarmaco(@Header("Authorization") String token, @Path("id") String id, @Body Propiedades propiedades);

    /** Obtención del listado de nombre de fármacos existente */
    @GET("farmacos/nombres")
    Call<List<FarmacoSimple>> getNombresFarmacos(@Header("Authorization") String token);

    /** Cálculo de mezclas */
    @POST("calculo/mezclas")
    Call<List<Carga>> calcularReceta(@Header("Authorization") String token, @Body DatosCalculoMezclas datos);

    /** Cálculo de dosis */
    @POST("calculo/dosis")
    Call<PropiedadSimple> calcularNuevaDosis(@Header("Authorization") String token, @Body DatosCalculoDosis datos);

    /*@FormUrlEncoded
    @POST("farmacos")
    Call<FarmacosResponse> createFarmacos(); */
}
