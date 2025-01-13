package com.thuve.slkustuportal.ui.personalInfo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class personalInfoViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public personalInfoViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}