package com.tfg.apptfg.ui.mezclas;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tfg.apptfg.databinding.MezclasFragmentBinding;

public class MezclasFragment extends Fragment {

    private MezclasFragmentBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MezclasViewModel mezclasViewModel =
                new ViewModelProvider(this).get(MezclasViewModel.class);

        binding = com.tfg.apptfg.databinding.MezclasFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textMezclas;
        mezclasViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}