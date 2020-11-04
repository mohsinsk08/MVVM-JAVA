package com.mohsin.mvvmpractice2java.data.repositories;

import android.accounts.NetworkErrorException;
import androidx.lifecycle.MutableLiveData;
import com.mohsin.mvvmpractice2java.data.network.LoginResponse;
import com.mohsin.mvvmpractice2java.util.GistServiceFactory;
import com.mohsin.mvvmpractice2java.util.RetrofitService;
import java.util.LinkedHashMap;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {

    LoginResponse loginResponse = new LoginResponse();

    private final MutableLiveData<LoginResponse> listOfMovies = new MutableLiveData<>();


    public MutableLiveData<LoginResponse> doLogin(String email, String password) {
        GistServiceFactory serviceFactory = new GistServiceFactory();

        Map<String, Object> params = new LinkedHashMap<>();

        params.put("email", email);
        params.put("password", password);


        RetrofitService retrofitService = new RetrofitService();
        try {
            retrofitService.getInterface().user_login(params).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    System.out.println("REso : "+response);

                    loginResponse = response.body();
                    listOfMovies.setValue(response.body());

                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    listOfMovies.setValue(null);

                }
            });
        } catch (NetworkErrorException e) {
            e.printStackTrace();
        }

/*
        try {
            serviceFactory.getGistService().user_login(params)
                    .subscribeOn(Schedulers.io()) //Asynchronously subscribes Observable to perform action in I/O Thread.
                    .delaySubscription(5000, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread()) // To perform its emissions and response on UiThread(or)MainThread.
                    .subscribe(new Observer<LoginResponse>() { // It would dispose the subscription automatically. If you wish to handle it use io.reactivex.Observer
                        @Override
                        public void onNext(LoginResponse gist) {
                            StringBuilder sb = new StringBuilder();

                            System.out.println("DATA : " + gist);

                            loginResponse = gist;



                        }

                        @Override
                        public void onCompleted() {
                            Log.d("Mohsin", "on Complete Called!");



                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace(); // Just to see complete log information. we can comment if not necessary!

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();

        }
*/

    return listOfMovies;
    }
}
