package com.mohsin.mvvmpractice2java.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.Toast;

import com.mohsin.mvvmpractice2java.R;
import com.mohsin.mvvmpractice2java.data.network.LoginResponse;
import com.mohsin.mvvmpractice2java.databinding.ActivityLoginBinding;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements LoginListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
        ActivityLoginBinding activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        LoginViewModel viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        activityLoginBinding.setViewmodel(viewModel);

        viewModel.loginListener = this;

        printAsterix();


    }

    void printAsterix() {
        HashMap<Integer, String> hm = new HashMap<>();
        hm.put(101, "ABC");
        hm.put(102, "PQR");
        hm.put(103, "RST");
        hm.put(100,"OOO");

        for (Map.Entry<Integer, String> list : hm.entrySet()) {
            System.out.println(list.getKey()+" "+list.getValue());

        }
    }

    @Override
    public void onStarted() {

    }

    @Override
    public void onSuccess(MutableLiveData<LoginResponse> loginResponse) {
        loginResponse.observe(this, new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse) {
                String strName = loginResponse.getUser().getName();
                Toast.makeText(LoginActivity.this, "Welcome " + strName, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}