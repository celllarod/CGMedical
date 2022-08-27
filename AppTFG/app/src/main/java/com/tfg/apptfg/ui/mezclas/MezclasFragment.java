package com.tfg.apptfg.ui.mezclas;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tfg.apptfg.R;
import com.tfg.apptfg.databinding.MezclasFragmentBinding;
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

    private MezclasFragmentBinding binding;
    private FragmentTransaction fragmentTransaction;
    private Fragment volumenFragment, fdFragment, fsFragment, recetaFragment;
    private Button bt1, bt2, bt3, bt4;
    private Button btActual;
    private Button btAnterior;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getChildFragmentManager().setFragmentResultListener("requestKey", this, (requestKey, result) -> {
            String res = result.getString("bundleKey");
            Log.d("Child Manager", res);
        });
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mezclasViewModel = new ViewModelProvider(this).get(MezclasViewModel.class);
        volumenViewModel = new ViewModelProvider(requireActivity()).get(VolumenStepViewModel.class);
        fdViewModel = new ViewModelProvider(requireActivity()).get(FarmacoDominanteStepViewModel.class);
        fsViewModel = new ViewModelProvider(requireActivity()).get(FarmacosSecundariosStepViewModel.class);

        binding = MezclasFragmentBinding.inflate(inflater, container, false);
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
        getChildFragmentManager().beginTransaction().add(R.id.contenedor_fragmentos, volumenFragment).commit();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void onClick(View view) {
        fragmentTransaction = getChildFragmentManager().beginTransaction();
        switch (view.getId()) {
            case R.id.step1:
                fragmentTransaction.replace(R.id.contenedor_fragmentos, volumenFragment);
                fragmentTransaction.addToBackStack("volumen");
                break;
            case R.id.step2:
                fragmentTransaction.replace(R.id.contenedor_fragmentos, fdFragment);
                fragmentTransaction.addToBackStack("fd");
                break;
            case R.id.step3:
                fragmentTransaction.replace(R.id.contenedor_fragmentos, fsFragment);
                fragmentTransaction.addToBackStack("fs");
                break;
            case R.id.step4:
                fragmentTransaction.replace(R.id.contenedor_fragmentos, recetaFragment);
                fragmentTransaction.addToBackStack("receta");
                break;
        }
        fragmentTransaction.commit();
        changeEstadoStepper();

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

    private void setEmptyButton(Button bt) {
        Drawable buttonDrawable = bt.getBackground();
        buttonDrawable = DrawableCompat.wrap(buttonDrawable);
        //the color is a direct color int and not a color resource
        DrawableCompat.setTint(buttonDrawable, ResourcesCompat.getColor(getResources(), R.color.azul_electrico, null));
        bt.setBackground(buttonDrawable);
    }

    private void setCorrectButton(Button bt) {
        Drawable buttonDrawable = bt.getBackground();
        buttonDrawable = DrawableCompat.wrap(buttonDrawable);
        //the color is a direct color int and not a color resource
        DrawableCompat.setTint(buttonDrawable, ResourcesCompat.getColor(getResources(), R.color.verde, null));
        bt.setBackground(buttonDrawable);
    }
    private void setErrorButton(Button bt) {
        Drawable buttonDrawable = bt.getBackground();
        buttonDrawable = DrawableCompat.wrap(buttonDrawable);
        //the color is a direct color int and not a color resource
        DrawableCompat.setTint(buttonDrawable, ResourcesCompat.getColor(getResources(), R.color.rojo, null));
        bt.setBackground(buttonDrawable);
    }
}