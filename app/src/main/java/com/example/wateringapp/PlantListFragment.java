package com.example.wateringapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.wateringapp.databinding.PlantListFragmentBinding;
import com.example.wateringapp.models.PlantModel;
import com.example.wateringapp.repository.AppDatabase;
import com.example.wateringapp.repository.PlantDao;

import java.util.ArrayList;
import java.util.List;

public class PlantListFragment extends Fragment {

    private PlantListFragmentBinding binding;

    //private PlantsAdapter adapter;

    private RecyclerAdapter adapter;
   // private PlantModel plantModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = PlantListFragmentBinding.inflate(inflater,container,false);
        adapter = new RecyclerAdapter(getContext(),getParentFragmentManager());

        binding.plantListRecyclerView.setAdapter(adapter);

        initPlantModel();


        return binding.getRoot();
    }

    private void initPlantModel(){
        AppDatabase db = Room.databaseBuilder(getContext(),
                AppDatabase.class, "ozkan").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        PlantDao plantDao = db.userDao();

        ArrayList<PlantModel> data = (ArrayList<PlantModel>) plantDao.getAll();
        adapter.setPlantModels(data);
    }



}
