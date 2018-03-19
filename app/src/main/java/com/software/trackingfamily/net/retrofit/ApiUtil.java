package com.software.trackingfamily.net.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by thaopt on 11/24/17.
 */

public class ApiUtil {


    public final static String HOST ="http://gisfamily.brotherhood.webstarterz.com/";
    public static Retrofit createService(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
    Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(HOST)
            .addConverterFactory(GsonConverterFactory.create(gson));
        return builder.build();
    }
}
