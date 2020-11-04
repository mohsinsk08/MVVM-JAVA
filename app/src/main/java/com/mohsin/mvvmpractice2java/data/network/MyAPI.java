package com.mohsin.mvvmpractice2java.data.network;


import android.accounts.NetworkErrorException;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;


public interface MyAPI {

    @POST("login")
    Call<LoginResponse> user_login(@Body Map<String, Object> objectMap) throws NetworkErrorException;

}
