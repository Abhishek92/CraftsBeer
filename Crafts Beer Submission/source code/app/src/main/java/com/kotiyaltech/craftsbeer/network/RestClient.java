package com.kotiyaltech.craftsbeer.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kotiyaltech.craftsbeer.BuildConfig;

import java.lang.reflect.Modifier;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hp pc on 30-06-2018.
 */

public class RestClient {
    private static String BASE_URL = "http://starlord.hackerearth.com/";
    private static Retrofit restAdapter;
    private static RestClient restClient = null;
    private static BeerEventService topperEventService;


    public static RestClient getInstance()
    {
        if(restClient == null){
            restClient = new RestClient();
            restAdapter = getRestClient();
        }
        return restClient;
    }

    private static Retrofit getRestClient()
    {
        if(null == restAdapter) {
            Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC).serializeNulls()
                    .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'").create();
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.readTimeout(90, TimeUnit.SECONDS);
            httpClient.connectTimeout(90, TimeUnit.SECONDS);

            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                httpClient.addInterceptor(logging);
            }
            OkHttpClient client = httpClient.build();

            Retrofit.Builder builder = new Retrofit.Builder();
            builder.baseUrl(BASE_URL);
            builder.client(client);
            builder.addConverterFactory(GsonConverterFactory.create(gson));
            restAdapter = builder.build();
        }
        return restAdapter;
    }

    public BeerEventService getBeerEventService() {
        if (topperEventService == null)
            topperEventService = restAdapter.create(BeerEventService.class);

        return topperEventService;
    }
}

