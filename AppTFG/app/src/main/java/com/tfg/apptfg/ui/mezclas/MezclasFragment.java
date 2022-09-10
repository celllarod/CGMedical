package com.tfg.apptfg.ui.mezclas;

import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;
import com.tfg.apptfg.R;
import com.tfg.apptfg.databinding.FragmentMezclasBinding;
import com.tfg.apptfg.ui.mezclas.step.FarmacoDominanteStepFragment;
import com.tfg.apptfg.ui.mezclas.step.FarmacoDominanteStepViewModel;
import com.tfg.apptfg.ui.mezclas.step.FarmacosSecundariosStepFragment;
import com.tfg.apptfg.ui.mezclas.step.FarmacosSecundariosStepViewModel;
import com.tfg.apptfg.ui.mezclas.step.RecetaStepFragment;
import com.tfg.apptfg.ui.mezclas.step.VolumenStepFragment;
import com.tfg.apptfg.ui.mezclas.step.VolumenStepViewModel;

public class MezclasFragment extends Fragment {

    private MezclasViewModel mezclasViewModel;
    private VolumenStepViewModel volumenViewModel;
    private FarmacoDominanteStepViewModel fdViewModel;
    private FarmacosSecundariosStepViewModel fsViewModel;

    private FragmentMezclasBinding binding;
    private FragmentTransaction fragmentTransaction;
    private Fragment volumenFragment, fdFragment, fsFragment, recetaFragment;
    private MaterialButton bt1, bt2, bt3, bt4;

    private Integer step;


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("tag_mezclas", "destroy");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null) {
            step = savedInstanceState.getInt("step");
        }
        Log.d("tag_mezclas", "create");
        savedInstanceState = null;
//        getChildFragmentManager().setFragmentResultListener("requestKey", this, (requestKey, result) -> {
//            String res = result.getString("bundleKey");
//            Log.d("Child Manager", res);
//        });
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.d("tag_mezclas", "createView");
        mezclasViewModel = new ViewModelProvider(this).get(MezclasViewModel.class);
        volumenViewModel = new ViewModelProvider(requireActivity()).get(VolumenStepViewModel.class);
        fdViewModel = new ViewModelProvider(requireActivity()).get(FarmacoDominanteStepViewModel.class);
        fsViewModel = new ViewModelProvider(requireActivity()).get(FarmacosSecundariosStepViewModel.class);

        binding = FragmentMezclasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        bt1 = binding.step1;
        bt2 = binding.step2;
        bt3 = binding.step3;
        bt4 = binding.step4;

        bt1.setOnClickListener(view -> onClick(view));
        bt2.setOnClickListener(view -> onClick(view));
        bt3.setOnClickListener(view -> onClick(view));
        bt4.setOnClickListener(view -> onClick(view));

        volumenFragment = new VolumenStepFragment();
        fdFragment = new FarmacoDominanteStepFragment();
        fsFragment = new FarmacosSecundariosStepFragment();
        recetaFragment = new RecetaStepFragment();

        // Por defecto se mostrará el paso 1 (volumen)
        if(step == null) {
            getChildFragmentManager().beginTransaction().add(R.id.contenedor_fragmentos, volumenFragment).commit();
            step = 1;
        }
        return root;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("step", step);
        Log.d("tag_mezclas", "saveinstancestate");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        Log.d("tag_mezclas", "destroyview");
    }

    public void onClick(View view) {
        fragmentTransaction = getChildFragmentManager().beginTransaction();
        switch (view.getId()) {
            case R.id.step1:
                fragmentTransaction.replace(R.id.contenedor_fragmentos, volumenFragment);
                step = 1;
                fragmentTransaction.addToBackStack("s1");
                break;
            case R.id.step2:
                fragmentTransaction.replace(R.id.contenedor_fragmentos, fdFragment);
                step = 2;
                fragmentTransaction.addToBackStack("s2");
                break;
            case R.id.step3:
                fragmentTransaction.replace(R.id.contenedor_fragmentos, fsFragment);
                step = 3;
                fragmentTransaction.addToBackStack("s3");
                break;
            case R.id.step4:
                isValidDatos();
                fragmentTransaction.replace(R.id.contenedor_fragmentos, recetaFragment);
                step = 4;
                fragmentTransaction.addToBackStack("s4");
                break;
        }
        fragmentTransaction.commit();
        //changeEstadoStepper();

    }

    private boolean isValidDatos() {
        boolean result = true;
        // Volumen
        if(mezclasViewModel.isEmptyVolumen()){
            result = false && result;
            setEmptyButton(bt1);
        } else {
            result = true && result;
            setCorrectButton(bt1);
        }
        //Farmaco dominante
        if(mezclasViewModel.isEmptyFarmacoDominante()){
            result = false && result;
            setEmptyButton(bt2);
        } else if(mezclasViewModel.isValidFarmacoDominante()){
            result = true && result;
            setCorrectButton(bt2);
        } else {
            result = false && result;
            setErrorButton(bt2);
        }
        //Farmacos secundarios
        if(mezclasViewModel.isValidFarmacosSecundarios()){
            setCorrectButton(bt3);
            result = true && result;
        } else {
            setEmptyButton(bt3);
            result = true && result;
        }
        return result;
    }

    private void changeEstadoStepper(){
        // Volumen
        if(mezclasViewModel.isEmptyVolumen()){
            setEmptyButton(bt1);
        } else {
            setCorrectButton(bt1);
        }

        //Farmaco dominante
        if(mezclasViewModel.isEmptyFarmacoDominante()){
            setEmptyButton(bt2);
        } else if(mezclasViewModel.isValidFarmacoDominante()){
            setCorrectButton(bt2);
        } else {
            setErrorButton(bt2);
        }



    }

    // TODO: llamar al método genérico de
    private void setEmptyButton(Button bt) {
        Drawable buttonDrawable = bt.getBackground();
        buttonDrawable = DrawableCompat.wrap(buttonDrawable);
        //the color is a direct color int and not a color resource
        DrawableCompat.setTint(buttonDrawable, ResourcesCompat.getColor(getResources(), R.color.rojo, null));
        bt.setBackground(buttonDrawable);
    }

    private void setCorrectButton(Button bt) {
        Drawable buttonDrawable = bt.getBackground();
        buttonDrawable = DrawableCompat.wrap(buttonDrawable);
        DrawableCompat.setTint(buttonDrawable, ResourcesCompat.getColor(getResources(), R.color.verde, null));
        bt.setBackground(buttonDrawable);
    }
    private void setErrorButton(Button bt) {
        Drawable buttonDrawable = bt.getBackground();
        buttonDrawable = DrawableCompat.wrap(buttonDrawable);
        DrawableCompat.setTint(buttonDrawable, ResourcesCompat.getColor(getResources(), R.color.rojo, null));
        bt.setBackground(buttonDrawable);
    }

}