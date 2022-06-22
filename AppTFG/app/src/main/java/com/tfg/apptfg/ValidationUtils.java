package com.tfg.apptfg;

import android.widget.EditText;

import java.util.Objects;

public class ValidationUtils {
    private static final int MAX_LENGTH_NAME = 20;
    private static final int MAX_LENGTH_AP1 = 50;
    private static final int MAX_LENGTH_AP2 = 50;
    private static final int MAX_LENGTH_EMAIL = 255;
    private static final int MAX_LENGTH_PASS = 40;
    private static final int MAX_LENGTH_HOSPITAL = 100;

    public static boolean validateName(EditText etName){
        boolean result = true;
        if(!isNotEmpty(etName)){
            result = false;
        } else if(!isValidLength(etName, MAX_LENGTH_NAME)) {
            result = false;
        }
        return result;
    }

    public static boolean validateApellido1(EditText etAp1){
        boolean result = true;
        if(!isNotEmpty(etAp1)){
            result = false;
        } else if(!isValidLength(etAp1, MAX_LENGTH_AP1)) {
            result = false;
        }
        return result;
    }

    public static boolean validateApellido2(EditText etAp2){
        return etAp2.getText().toString().isEmpty() || isValidLength(etAp2, MAX_LENGTH_AP2);
    }

    public static boolean validatePassword(EditText etPassword, EditText etPasswordConfirmed){
        boolean result = true;
        if(!isNotEmpty(etPassword) || !isNotEmpty(etPasswordConfirmed)){
            result = false;
        } else if (!isValidLength(etPassword, MAX_LENGTH_PASS)) {
            result = false;
        }
        else if(!arePasswordEquals(etPassword, etPasswordConfirmed)) {
            result = false;
        }
        return result;
    }

    public static boolean arePasswordEquals(EditText etPass1, EditText etPass2){
        boolean result = false;
        String password1 = etPass1.getText().toString();
        String password2 = etPass2.getText().toString();

        if (!Objects.equals(password1, password2)){
            etPass1.setError("Las contraseñas no coinciden ");
            etPass2.setError("Las contraseñas no coinciden ");
        } else {
            etPass1.setError(null);
            etPass2.setError(null);
            result = true;
        }
        return  result;
    }

    public static boolean validateEmail(EditText etEmail){
        boolean result = true;
        String emailPattern = "[a-z0-9._-]+@[a-z]+\\.+[a-z]+";
        String value = etEmail.getText().toString().trim().toLowerCase();
        if(!isNotEmpty(etEmail)){
            result = false;
        } else if(!isValidLength(etEmail, MAX_LENGTH_EMAIL)){
            result = false;
        } else if (!value.matches(emailPattern)) {
            etEmail.setError("Formato incorrecto");
            result = false;
        }
        return result;
    }

    public static boolean validateHospital(EditText etHospital){
        boolean result = true;
        if(!isNotEmpty(etHospital)){
            result = false;
        } else if(!isValidLength(etHospital, MAX_LENGTH_HOSPITAL)) {
            result = false;
        }
        return result;
    }

    public static boolean isNotEmpty(EditText et){
        boolean result = false;
        String value = et.getText().toString();
        if(value.isEmpty()) {
            et.setError("Este campo no puede estar vacío");
        } else{
            et.setError(null);
            result = true;
        }
        return result;
    }

    public static boolean isValidLength(EditText et, int maxSize) {
        boolean result = false;
        String value = et.getText().toString().trim();

        if(value.length()>maxSize) {
            et.setError("Máximo de caracteres superado ("+maxSize+")");
        } else{
            et.setError(null);
            result = true;
        }
        return result;
    }


}
