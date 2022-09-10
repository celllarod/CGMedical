package com.tfg.apptfg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.tfg.apptfg.io.ApiAdapter;
import com.tfg.apptfg.io.request.LoginUser;
import com.tfg.apptfg.io.response.JwtResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout tilEmail;
    EditText etEmail, etPassword;
    MaterialButton btSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tilEmail = findViewById(R.id.til_login_email);
        etEmail = findViewById(R.id.et_login_email);
        etPassword = findViewById(R.id.et_login_password);
        btSubmit = findViewById(R.id.bt_login_submit);

        btSubmit.setOnClickListener(view -> {
                etEmail.setText("car@us.es");
                etPassword.setText("123456789");

                if(isValid()) {
                    LoginUser user = new LoginUser();
                    user.setEmail(etEmail.getText().toString().trim().toLowerCase());
                    user.setPassword(etPassword.getText().toString());

                    Log.d("[CPMEDICAL][REST]", "Credenciales rellenas");

                    Call<JwtResponse> callSignIn = ApiAdapter.getApiService().signInUser(user);
                    callSignIn.enqueue(new Callback<JwtResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<JwtResponse> call, @NonNull Response<JwtResponse> response) {
                            if (response.isSuccessful()) {
                                Log.d("[CPMEDICAL][REST]", "Usuario Autenticado");
                                JwtResponse jwt = response.body();
                                SessionManager session = new SessionManager(jwt.getToken(), jwt.getType(), jwt.getId().toString(), jwt.getRol(), jwt.getEmail());
                                SessionManager.save(session, getApplicationContext());
                                Intent intent = new Intent(view.getContext(), MainActivity.class);
                                startActivity(intent);
                            } else {
                                Log.d("[CPMEDICAL][REST][ERROR]", "SignIn User: " + response.errorBody());
                                GeneralUtils.showErrorToast(getApplicationContext(), "Error al iniciar sesión. Revise sus credenciales.");
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<JwtResponse> call, @NonNull Throwable t) {
                            Log.d("[CPMEDICAL][REST][ERROR]", "SignIn User: " + t.getMessage());
                        }
                    });
                }
        });
    }


    private boolean isValid(){
            boolean result = true;
            result = ValidationUtils.isNotEmpty(etEmail) && result;
            result = ValidationUtils.isNotEmpty(etPassword) && result;
            return result;
    }
}

