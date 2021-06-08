package com.example.wateringapp.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherAPIService {

// https://api.openweathermap.org/data/2.5/weather?q={city name},{state code}&appid=b5d44dd0792a864e57626297e51045c7

    public static final  String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    private String api_key = "b5d44dd0792a864e57626297e51045c7";


    private static Retrofit retrofit = null;

    public static Retrofit getClient(){

        if (retrofit == null){

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }

        return retrofit;

    }


}
