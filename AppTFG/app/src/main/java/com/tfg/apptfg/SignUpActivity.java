package com.tfg.apptfg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.tfg.apptfg.io.request.SignUpUser;


public class SignUpActivity extends AppCompatActivity {

    TextInputLayout tilName, tilAp1, tilAp2, tilEmail;
    EditText etName, etAp1, etAp2, etEmail, etPassword, etPasswordConfirmed;
    MaterialButton btContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        tilName = findViewById(R.id.til_signup_name);
        tilAp1 = findViewById(R.id.til_signup_ap1);
        tilAp2 = findViewById(R.id.til_signup_ap2);
        tilEmail = findViewById(R.id.til_signup_email);

        etName = findViewById(R.id.et_signup_name);
        etAp1 = findViewById(R.id.et_signup_ap1);
        etAp2 = findViewById(R.id.et_signup_ap2);
        etEmail = findViewById(R.id.et_signup_email);
        etPassword = findViewById(R.id.et_signup_password);
        etPasswordConfirmed = findViewById(R.id.et_signup_password_repetir);
        btContinue = findViewById(R.id.bt_signup_continuar);

        btContinue.setOnClickListener(view -> {
            etName.setText("Celia");
            etAp1.setText("Llanes");
            //etEmail.setText("c@alum.us");
            etPassword.setText("12345678");
            etPasswordConfirmed.setText("12345678");

           if(isValid()) {
                Intent intent = new Intent(view.getContext(), HospitalActivity.class);
                SignUpUser user = new SignUpUser();
                user.setNombre(etName.getText().toString().trim());
                user.setApellido1(etAp1.getText().toString().trim());
                user.setApellido2(etAp2.getText().toString().trim());
                user.setEmail(etEmail.getText().toString().trim().toLowerCase());
                user.setPassword(etPassword.getText().toString());
                intent.putExtra("signup", user);
                startActivity(intent);
            }
        });
    }

    private boolean isValid(){
        boolean result = true;
        result = ValidationUtils.validateName(etName) && result;
        result = ValidationUtils.validateApellido1(etAp1) && result;
        result = ValidationUtils.validateApellido2(etAp2) && result;
        result = ValidationUtils.validateEmail(etEmail) && result;
        result = ValidationUtils.validatePassword(etPassword, etPasswordConfirmed) && result;
        return result;
    }

    private void login(){



    }
}