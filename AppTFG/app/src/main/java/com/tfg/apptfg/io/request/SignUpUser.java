package com.tfg.apptfg.io.request;

import android.os.Parcel;
import android.os.Parcelable;

/** Clase que modela el cuerpo de la petición para registrar a un usuario */

public class SignUpUser implements Parcelable {
    /** Nombre del usuario */
    private String nombre;
    /** Primer apellido del usuario */
    private String apellido1;
    /** Segundo apellido del usuario */
    private String apellido2;
    /** Correo electrónico del suuario */
    private String email;
    /** Contrasella del usuario*/
    private String password;
    /** Hospital del usuario */
    private String hospital;

    public SignUpUser() {
    }

    public SignUpUser(String nombre, String apellido1, String apellido2, String email, String password, String hospital) {
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.email = email;
        this.password = password;
        this.hospital = hospital;
    }

    protected SignUpUser(Parcel in) {
        nombre = in.readString();
        apellido1 = in.readString();
        apellido2 = in.readString();
        email = in.readString();
        password = in.readString();
        hospital = in.readString();
    }

    public static final Creator<SignUpUser> CREATOR = new Creator<SignUpUser>() {
        @Override
        public SignUpUser createFromParcel(Parcel in) {
            return new SignUpUser(in);
        }

        @Override
        public SignUpUser[] newArray(int size) {
            return new SignUpUser[size];
        }
    };

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nombre);
        parcel.writeString(apellido1);
        parcel.writeString(apellido2);
        parcel.writeString(email);
        parcel.writeString(password);
        parcel.writeString(hospital);
    }
}
