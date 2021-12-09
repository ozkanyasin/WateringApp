package com.example.wateringapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wateringapp.databinding.WeatherFragmentBinding;
import com.example.wateringapp.retrofit.Example;
import com.example.wateringapp.service.WeatherAPI;
import com.example.wateringapp.service.WeatherAPIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherFragment extends Fragment {

    private WeatherFragmentBinding binding;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = WeatherFragmentBinding.inflate(inflater,container,false);

        sharedPreferences = getContext().getSharedPreferences("OpeningCity", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

       /* String city = sharedPreferences.getString("city"," ");
        editor.putString("city",binding.editTextSearch.getText().toString().trim());
        editor.apply();
        binding.editTextSearch.setText(city);*/


        binding.imageSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getWeatherData(binding.editTextSearch.getText().toString().trim());


            }
        });

        binding.editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.textCity.setText(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return binding.getRoot();
    }

    private void getWeatherData(String name){

        WeatherAPI weatherAPI = WeatherAPIService.getClient().create(WeatherAPI.class);

        Call<Example> call = weatherAPI.getWeatherData(name);

        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                binding.textCity.setText(binding.editTextSearch.getText().toString());
                binding.textTemp.setText(response.body().getMain().getTemp()+" °C");
                binding.textDesc.setText(response.body().getMain().getFeels_like()+" °C");
                binding.textMin.setText(response.body().getMain().getTemp_min()+" °C");
                binding.textMax.setText(response.body().getMain().getTemp_max()+" °C");
                binding.textHum.setText(response.body().getMain().getHumidity()+"%");
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Toast.makeText(getContext(),"Şehir bulunamadı",Toast.LENGTH_SHORT).show();
            }
        });



    }

}
