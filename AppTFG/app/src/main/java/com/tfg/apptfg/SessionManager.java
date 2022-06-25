package com.tfg.apptfg;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class SessionManager {

    private  final static String USER_TOKEN = "userToken";
    private  final static String USER_TOKEN_TYPE = "userTokenType";
    private  final static String USER_ID = "userId";
    private  final static String USER_EMAIL = "userEmail";
    private  final static String USER_ROL = "userRol";
    private  final static String PREFERENCES_NAME = "cpmedical";

    private final String userToken;
    private final String userTokenType;
    private final String userId;
    private final String userRol;
    private final String userEmail;

    public SessionManager(String userToken, String userTokenType, String userId, String userRol, String userEmail) {
        this.userToken = userToken;
        this.userTokenType = userTokenType;
        this.userId = userId;
        this.userRol = userRol;
        this.userEmail = userEmail;
    }

    public static void save(SessionManager session, Context cxt) {
        Log.d("[CPMEDICAL][SESSION]", "Se guarda la sesión en SharedPreferences para el usuario (" + session.getUserEmail() + ")");
        SharedPreferences sp = cxt.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);

        sp.edit()
            .putString(USER_TOKEN, session.getUserToken())
            .putString(USER_TOKEN_TYPE, session.getUserTokenType())
            .putString(USER_ID, session.getUserId())
            .putString(USER_EMAIL, session.getUserEmail())
            .putString(USER_ROL, session.getUserRol())
            .apply();
    }

    public static SessionManager get(Context cxt) {
        SharedPreferences sp = cxt.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SessionManager result = new SessionManager(sp.getString(USER_TOKEN, null),
                                    sp.getString(USER_TOKEN_TYPE, "null"),
                                    sp.getString(USER_ID, "null"),
                                    sp.getString(USER_ROL, "null"),
                                    sp.getString(USER_EMAIL, "null")
                                    );
        if (result.getUserId() == null) {
            destroy(cxt);
            result = null;
        }
        return result;
    }

    public static void destroy(Context cxt) {
        SharedPreferences sp = cxt.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor  spEditor = sp.edit();
        Log.d("[CPMEDICAL][SESSION]", "Se destruye la sesión.");
        spEditor.clear();
        spEditor.apply();
    }

    public String getUserToken() {
        return this.userToken;
    }

    public String getUserTokenType() {
        return this.userTokenType;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public String getUserRol() {
        return this.userRol;
    }

}
