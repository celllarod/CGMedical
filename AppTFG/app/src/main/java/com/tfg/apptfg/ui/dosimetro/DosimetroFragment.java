package com.tfg.apptfg.ui.dosimetro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.tfg.apptfg.databinding.DosimetroFragmentBinding;

public class DosimetroFragment extends Fragment {

    private DosimetroFragmentBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DosimetroViewModel dosimetroViewModel =
                new ViewModelProvider(this).get(DosimetroViewModel.class);

        binding = DosimetroFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDosimetro;
        dosimetroViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}