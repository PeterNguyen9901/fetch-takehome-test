package com.fetchhiring;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static APIClient instance;
    private final Retrofit retrofit;

    /**
     * APIClient provides a single instance of Retrofit for the application.
     * Singleton pattern to ensure only one instance of Retrofit is used throughout the app.
     */
    private APIClient() {
        // Interceptor for logging network requests and responses
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://fetch-hiring.s3.amazonaws.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }
    // Returns the single instance of APIClient
    //If instance does not exist, it is created
    public static synchronized APIClient getInstance() {
        if (instance == null) {
            instance = new APIClient();
        }
        return instance;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}


