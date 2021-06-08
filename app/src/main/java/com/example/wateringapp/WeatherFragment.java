package com.example.wateringapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wateringapp.retrofit.Example;
import com.example.wateringapp.service.WeatherAPI;
import com.example.wateringapp.service.WeatherAPIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherFragment extends Fragment {

    private ImageView imageSearch;
    private EditText editTextSearch;
    private TextView textCity, textTemp, textDesc, textMin, textMax, textHum;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.weather_fragment,container,false);

        imageSearch = view.findViewById(R.id.imageSearch);
        editTextSearch = view.findViewById(R.id.editTextSearch);
        textCity = view.findViewById(R.id.textCity);
        textTemp = view.findViewById(R.id.textTemp);
        textDesc = view.findViewById(R.id.textDesc);
        textMin = view.findViewById(R.id.textMin);
        textMax = view.findViewById(R.id.textMax);
        textHum = view.findViewById(R.id.textHum);


        imageSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getWeatherData(editTextSearch.getText().toString().trim());

            }
        });


        return view;
    }

    private void getWeatherData(String name){

        WeatherAPI weatherAPI = WeatherAPIService.getClient().create(WeatherAPI.class);

        Call<Example> call = weatherAPI.getWeatherData(name);

        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                textCity.setText(editTextSearch.getText().toString());
                textTemp.setText(response.body().getMain().getTemp()+" °C");
                textDesc.setText(response.body().getMain().getFeels_like()+" °C");
                textMin.setText(response.body().getMain().getTemp_min()+" °C");
                textMax.setText(response.body().getMain().getTemp_max()+" °C");
                textHum.setText(response.body().getMain().getHumidity()+"%");
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });



    }

}
