package com.tfg.apptfg.io;

import com.tfg.apptfg.io.request.DatosFarmacoCrear;
import com.tfg.apptfg.io.request.LoginUser;
import com.tfg.apptfg.io.request.SignUpUser;
import com.tfg.apptfg.io.response.FarmacoDetalle;
import com.tfg.apptfg.io.response.FarmacoResumen;
import com.tfg.apptfg.io.response.JwtResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

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




    /*@FormUrlEncoded
    @POST("farmacos")
    Call<FarmacosResponse> createFarmacos(); */
}
