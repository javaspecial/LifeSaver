package com.peoples.lifesaver.risk;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {
    // Demo server folder (CHANGE to your GitHub raw URL)
    private static final String BASE_URL = "https://raw.githubusercontent.com/yourusername/riskdata/main/";
    private static Retrofit retrofit;

    public static ApiService api() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiService.class);
    }
}
