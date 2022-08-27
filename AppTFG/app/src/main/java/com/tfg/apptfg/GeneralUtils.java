package com.tfg.apptfg;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

public class GeneralUtils {

    public static void showErrorToast(Context context, String msg) {
        Toast errorPresentacionToast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        View toastView = errorPresentacionToast.getView();
        toastView.setBackground(ContextCompat.getDrawable(context, R.drawable.toast_error_backgound));
        errorPresentacionToast.setDuration(Toast.LENGTH_LONG);
        errorPresentacionToast.show();
    }

    public static void showSuccesToast(Context context, String msg) {
        Toast errorPresentacionToast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        View toastView = errorPresentacionToast.getView();
        toastView.setBackground(ContextCompat.getDrawable(context, R.drawable.toast_exito_backgound));
        errorPresentacionToast.setDuration(Toast.LENGTH_LONG);
        errorPresentacionToast.show();
    }
}
