package com.mohsin.mvvmpractice2java.ui.login;

import androidx.lifecycle.MutableLiveData;

import com.mohsin.mvvmpractice2java.data.network.LoginResponse;

public interface LoginListener {

    void onStarted();
    void onSuccess(MutableLiveData<LoginResponse> loginResponse);
    void onFailure(String message);

}
