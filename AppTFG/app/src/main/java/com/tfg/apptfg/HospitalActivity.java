package com.tfg.apptfg;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.tfg.apptfg.io.ApiAdapter;
import com.tfg.apptfg.io.request.SignUpUser;
import com.tfg.apptfg.io.response.JwtResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;

public class HospitalActivity extends AppCompatActivity {

    AutoCompleteTextView acHospitales;
    MaterialButton btSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);

        acHospitales = findViewById(R.id.ac_hospitales);
        btSubmit = findViewById(R.id.bt_signup_submit);

        SignUpUser user = getIntent().getParcelableExtra("signup");
        Toast errorApiToast = Toast.makeText(this, R.string.error_servidor, Toast.LENGTH_LONG);
        errorApiToast.setDuration(Toast.LENGTH_LONG);

        btSubmit.setOnClickListener(view -> {
            EditText etHospitales = new EditText(view.getContext());
            etHospitales.setText(acHospitales.getText().toString());

            if(ValidationUtils.validateHospital(etHospitales)){
                user.setHospital(etHospitales.getText().toString().trim().toLowerCase());
                Log.d("[CPMEDICAL][SignUp]", "Usuario a registrar válido: " + user.getNombre() + " -- " + user.getApellido1()+" -- " +user.getEmail()+" -- " +user.getPassword() + " -- " + user.getHospital());

                Call<JwtResponse> callSignUp = ApiAdapter.getApiService().signUpUser(user);
                callSignUp.enqueue(new Callback<JwtResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<JwtResponse> call, @NonNull Response<JwtResponse> response) {
                        if(response.isSuccessful()) {
                            JwtResponse jwt = response.body();
                            Log.d("[CPMEDICAL][REST]", "SignUp User: " + jwt);
                            SessionManager session = new SessionManager(jwt.getToken(), jwt.getType(), jwt.getId().toString(), jwt.getRol(), jwt.getEmail());
                            SessionManager.save(session, getApplicationContext());
                            Intent intent = new Intent(view.getContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<JwtResponse> call, @NonNull Throwable t) {
                            Log.d("[CPMEDICAL][REST][ERROR]",  "SignUp User " + t.getMessage());
                    }
                });

            }
        });

        Call<List<String>> callGetHospitales = ApiAdapter.getApiService().getHospitales();
        callGetHospitales.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(@NonNull Call<List<String>> call, @NonNull Response<List<String>> response) {
                if(response.isSuccessful()){
                    List<String> hospitales = response.body();
                    Log.d("[CPMEDICAL][REST]",  "Get hospitales");
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(HospitalActivity.this, android.R.layout.simple_spinner_dropdown_item, hospitales);
                    acHospitales.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<String>> call, @NonNull Throwable t) {
                errorApiToast.show();
                Log.d("[CPMEDICAL][REST][ERROR]",  "Get hospitales: " + t.getMessage());
            }
        });
    }
}