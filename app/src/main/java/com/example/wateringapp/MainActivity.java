package com.example.wateringapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.wateringapp.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Fragment tempFragment;
    private FirebaseAuth mAuth;
    String tag ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null){
            Intent loginIntent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(loginIntent);
            Toast.makeText(this,"Lütfen Giriş Yapınız",Toast.LENGTH_LONG).show();
        }

        binding.bottomNavigationView.setItemIconTintList(null);

        loadFragments(new PlantListFragment(),tag);

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
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
                    case R.id.action4:
                        mAuth.signOut();
                        Toast.makeText(MainActivity.this,"Oturum Kapatıldı",Toast.LENGTH_SHORT).show();
                        Intent loginIntent = new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(loginIntent);

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