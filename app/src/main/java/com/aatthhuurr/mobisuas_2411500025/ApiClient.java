package com.aatthhuurr.mobisuas_2411500025;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    // Ganti IP di bawah ini dengan IP Laptop kalian (buka cmd -> ketik ipconfig)
    private static final String BASE_URL = "http://1.1.1.213/api_proyek/";
    private static Retrofit retrofit = null;

    public static ApiService getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiService.class);
    }
}