package com.example.wateringapp.service;

import com.example.wateringapp.retrofit.Example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPI {

    @GET("http://api.openweathermap.org/data/2.5/weather?&appid=b5d44dd0792a864e57626297e51045c7&units=metric")
    Call<Example> getWeatherData(@Query("q") String name);

}
