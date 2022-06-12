package com.tfg.apptfg.ui.dosimetro;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DosimetroViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public DosimetroViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dosimetro fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}