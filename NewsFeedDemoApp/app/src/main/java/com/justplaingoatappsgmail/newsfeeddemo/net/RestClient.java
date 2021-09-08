package com.justplaingoatappsgmail.newsfeeddemo.net;

import android.util.Log;
import com.justplaingoatappsgmail.newsfeeddemo.AppConstants;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    public static volatile Retrofit retrofit = null;
    private static NewsFeedApiService newsFeedApiService;
    private static final HttpLoggingInterceptor logger = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            Log.d("NewsFeed", message);
        }
    }).setLevel(HttpLoggingInterceptor.Level.BASIC);
    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(logger)
            .build();

    protected RestClient() {}

    public static Retrofit getRetrofitInstance() {
        if(retrofit == null) {
            synchronized (RestClient.class) {
                if(retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(AppConstants.BASE_URL)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(okHttpClient)
                            .build();
                }
            }
        }
        return retrofit;
    }

    public static NewsFeedApiService getSteamApiInterface() {
        if(newsFeedApiService == null) {
            synchronized (RestClient.class) {
                if(newsFeedApiService == null) {
                    newsFeedApiService = getRetrofitInstance().create(NewsFeedApiService.class);
                }
            }
        }
        return newsFeedApiService;
    }

}
