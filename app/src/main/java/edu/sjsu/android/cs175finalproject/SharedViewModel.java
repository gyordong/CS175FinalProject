package edu.sjsu.android.cs175finalproject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Boolean> isPostCardVisible = new MutableLiveData<>();

    public void setPostCardVisible(boolean isVisible) {
        isPostCardVisible.setValue(isVisible);
    }

    public LiveData<Boolean> getPostCardVisible() {
        return isPostCardVisible;
    }
}
