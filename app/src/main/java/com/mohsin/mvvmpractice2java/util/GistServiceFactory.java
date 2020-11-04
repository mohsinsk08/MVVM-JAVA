package com.mohsin.mvvmpractice2java.util;



import com.google.gson.GsonBuilder;
import com.mohsin.mvvmpractice2java.data.network.MyAPI;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Mohsin
 */

public class GistServiceFactory {

    public MyAPI getGistService() {
        OkHttpClient okHttpClient = getOkHttpClient();
        Retrofit retrofit = provideGistRetrofit(okHttpClient);
        return retrofit.create(MyAPI.class);
    }

    /**
     * This will gives the {@link OkHttpClient} instance with 30 seconds timeout for Read and Connect timeout limitation.
     * and also It has set {@link HttpLoggingInterceptor.Logger} information to print the all information
     * about your service call like URL, Header if available, body content, Http Type, etc,,,.
     *
     * @return
     */
    private OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS) // 30 seconds Connection Timeout
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request request = original.newBuilder()
                                .method(original.method(), original.body())
                                .build();

                        return chain.proceed(request);
                    }
                })
                .readTimeout(600, TimeUnit.SECONDS) // 60 seconds Read Timeout
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)) // Logger for Api call
                .build();
    }

    /**
     * It returns retrofit instance to make API call for given URL with RxJava2 support.
     *
     * @param okHttpClient - it provide all timeout info, logger info and all necessary
     *                     information to {@link Retrofit} by {@link OkHttpClient}
     * @return
     */
    private Retrofit provideGistRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // provides RxJava2 webservice call support here.
                .client(okHttpClient) // Sets OkHttpClient.
                .addConverterFactory(providesGsonConverterFactory()) // Set Gson converter here
                .baseUrl("https://api.simplifiedcoding.in/course-apis/mvvm/").build();
    }

    /**
     * It provide Gson instance to convert Json to Pojo.
     *
     * @return
     */
    private GsonConverterFactory providesGsonConverterFactory() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return GsonConverterFactory.create(gsonBuilder.create());
    }
}
