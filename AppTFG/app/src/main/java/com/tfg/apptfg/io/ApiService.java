package com.tfg.apptfg.io;

import com.tfg.apptfg.io.request.LoginUser;
import com.tfg.apptfg.io.request.SignUpUser;
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

    @POST("auth/signup")
    Call<JwtResponse> signUpUser( @Body SignUpUser signup );

    @POST("auth/signin")
    Call<JwtResponse> signInUser( @Body LoginUser sigin );

    @GET("auth/hospitales")
    Call<List<String>> getHospitales();

    @GET("farmacos")
    Call<List<FarmacoResumen>> getFarmacos(@Header("Authorization") String token);



    /*@FormUrlEncoded
    @POST("farmacos")
    Call<FarmacosResponse> createFarmacos(); */
}
