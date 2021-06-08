package com.example.wateringapp;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private Fragment tempFragment;
    private BottomNavigationView bottomNavigationView;
    String tag ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setItemIconTintList(null);

        loadFragments(new PlantListFragment(),tag);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                tempFragment = null;
                switch (item.getItemId()){
                    case R.id.action1:
                        tempFragment = new PlantListFragment();
                        tag = "fragment1";
                        break;
                    case R.id.action2:
                        tempFragment = new WeatherFragment();
                        tag = "fragment2";
                        break;
                    case R.id.action3:
                        tempFragment = new AddPlantFragment();
                        tag = "fragment3";
                        break;
                }
                return loadFragments(tempFragment,tag);
            }
        });

        getResources().getString(R.string.once_a_day);

    }

    public boolean loadFragments(Fragment fragment, String tag){
        if (fragment!=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolder, fragment,tag)
                    .addToBackStack(tag).commit();
        }
        return true;
    }

}