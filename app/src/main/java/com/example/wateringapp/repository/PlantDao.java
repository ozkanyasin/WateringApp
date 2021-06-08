package com.example.wateringapp.repository;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.wateringapp.models.PlantModel;

import java.util.List;

@Dao
public interface PlantDao {
    @Query("SELECT * FROM plantmodel")
    List<PlantModel> getAll();

    @Insert
    long[] insertAll(PlantModel... plantModels);



    @Query("DELETE FROM plantmodel WHERE uid = :id")
    void delete(int id);

    @Update
    void update(PlantModel plantModel);

}
