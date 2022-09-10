package com.tfg.apptfg;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.google.android.material.textfield.TextInputLayout;

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

    public static void setBackgroundColorButton(Button bt, @NonNull Resources res, @ColorRes int idColor) {
        Drawable buttonDrawable = bt.getBackground();
        buttonDrawable = DrawableCompat.wrap(buttonDrawable);
        DrawableCompat.setTint(buttonDrawable, ResourcesCompat.getColor(res, idColor, null));
        bt.setBackground(buttonDrawable);
    }

    public static void setBlinckEffect(TextView txt, @ColorRes int idColor1, @ColorRes int idColor2){

        ObjectAnimator anim = ObjectAnimator.ofInt(txt, "textColor", ContextCompat.getColor(txt.getContext(), idColor1), ContextCompat.getColor(txt.getContext(), idColor2),
                ContextCompat.getColor(txt.getContext(), idColor1));
        anim.setDuration(2000); //ms
        anim.setEvaluator(new ArgbEvaluator());
        anim.setRepeatMode(ValueAnimator.RESTART);
        anim.setRepeatCount(Animation.RESTART);
        anim.start();

    }

    public static void setBlinckEffectBackgroung(TextInputLayout txt, @ColorRes int idColor1, @ColorRes int idColor2){

        ObjectAnimator anim = ObjectAnimator.ofInt(txt, "backgroundColor", ContextCompat.getColor(txt.getContext(), idColor1), ContextCompat.getColor(txt.getContext(), idColor2),
                ContextCompat.getColor(txt.getContext(), idColor1));
        anim.setDuration(2000); //ms
        anim.setEvaluator(new ArgbEvaluator());
        anim.setRepeatMode(ValueAnimator.RESTART);
        anim.setRepeatCount(Animation.RESTART);
        anim.start();

    }
}
