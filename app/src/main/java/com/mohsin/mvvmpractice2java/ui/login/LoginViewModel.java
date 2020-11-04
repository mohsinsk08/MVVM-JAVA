package com.mohsin.mvvmpractice2java.ui.login;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mohsin.mvvmpractice2java.data.network.LoginResponse;
import com.mohsin.mvvmpractice2java.data.repositories.LoginRepository;

public class LoginViewModel extends ViewModel {
    private String email = "";
    private String password;
    LoginListener loginListener;
    private MutableLiveData<LoginResponse> loginRes = new MutableLiveData<>();





    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public void onButtonClick(View view){

        if(email.isEmpty() || password.isEmpty()){
            loginListener.onFailure("Invalid");
        }else{
            loginListener.onSuccess(getMoviesRepository(email,password));
        }
    }


    public MutableLiveData<LoginResponse> getMoviesRepository(String email, String password) {
        loginRes = loadMoviesData(email, password);
        return loginRes;
    }
    private MutableLiveData<LoginResponse> loadMoviesData(String email, String password) {

        LoginRepository loginRepository = new LoginRepository();
        return loginRepository.doLogin(email, password);
    }
}
