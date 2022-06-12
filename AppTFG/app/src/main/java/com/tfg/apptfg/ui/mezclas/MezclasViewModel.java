package com.tfg.apptfg.ui.mezclas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MezclasViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private final MutableLiveData<String> mText;

    public MezclasViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Mezclas fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}