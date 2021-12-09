package com.example.wateringapp;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.example.wateringapp.databinding.PlantsRowBinding;
import com.example.wateringapp.models.PlantModel;
import com.example.wateringapp.repository.AppDatabase;
import com.example.wateringapp.repository.PlantDao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.PlantsViewHolder>{

    List<PlantModel> plantModels = new ArrayList<>();
    Context mContext;
    PlantModel plantModel;
    FragmentManager fragmentManager;

    public RecyclerAdapter(Context mContext, FragmentManager fragmentManager) {
        this.mContext = mContext;
        this.fragmentManager = fragmentManager;
    }

    public void setPlantModels(List<PlantModel> plantModels){
        this.plantModels = plantModels;
        notifyDataSetChanged();
    }


    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public PlantsViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        PlantsRowBinding plantsRowBinding = PlantsRowBinding.inflate(layoutInflater, parent,false);
        return new PlantsViewHolder(plantsRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull RecyclerAdapter.PlantsViewHolder holder, int position) {
        plantModel = plantModels.get(position);
        holder.bindView(plantModels.get(position));
    }

    @Override
    public int getItemCount() {
        return plantModels.size();
    }


    class PlantsViewHolder extends RecyclerView.ViewHolder{

        PlantsRowBinding plantsRowBinding;

        public PlantsViewHolder(@NonNull PlantsRowBinding plantsRowBinding){
            super(plantsRowBinding.getRoot());
            this.plantsRowBinding = plantsRowBinding;
        }

        public void bindView(PlantModel plantModel){
            plantModel = plantModels.get(getPosition());

            plantsRowBinding.textViewPlantName.setText(plantModel.getPlantName());
            plantsRowBinding.textViewTime.setText(plantModel.getStartTime());
            plantsRowBinding.buttonOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopupMenu(v);
                }
            });

            ContextWrapper cw = new ContextWrapper(mContext);
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            File file = new File(directory, plantModel.photoUrl);
            Glide.with(mContext).load(file.toString()).centerCrop().into(plantsRowBinding.imageViewPlant);
        }

        AppDatabase db = Room.databaseBuilder(mContext,
                AppDatabase.class, "ozkan").allowMainThreadQueries().build();
        PlantDao plantDao = db.userDao();

        private void showPopupMenu(View view){
            PopupMenu popupMenu = new PopupMenu(view.getContext(),view);
            popupMenu.inflate(R.menu.popup_menu);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    switch (item.getItemId()){  //edit ekranına yolla
                        case R.id.actionEdit:
                            PlantModel plantModel = plantModels.get(getBindingAdapterPosition());
                            EditPlantFragment editPlantFragment = new EditPlantFragment();
                            Bundle bundle = new Bundle();
                            bundle.putInt("uid",plantModel.getUid());
                            bundle.putString("plantName",plantModel.getPlantName());
                            bundle.putString("startDate",plantModel.getStartDate());
                            bundle.putString("startTime",plantModel.getStartTime());
                            bundle.putString("routine",plantModel.getRoutine());
                            bundle.putString("photoUrl",plantModel.getPhotoUrl());

                            editPlantFragment.setArguments(bundle);
                            fragmentManager.beginTransaction().replace(R.id.fragmentHolder, editPlantFragment).commit();


                            return true;
                        case R.id.actionDelete:

                            int uid = plantModels.get(getBindingAdapterPosition()).getUid();
                            cancelAlarm(uid);
                            String url = plantModels.get(getBindingAdapterPosition()).getPhotoUrl();
                            plantModels.remove(getBindingAdapterPosition());
                            notifyDataSetChanged();
                            plantDao.delete(uid);

                            ContextWrapper cw = new ContextWrapper(mContext);
                            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
                            File file2 = new File(directory, url);
                            file2.delete();

                            return true;
                        default: return false;
                    }
                }
            });
            popupMenu.show();
        }

    }

    private void cancelAlarm(int id) {
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(mContext, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, id, intent, 0);
        alarmManager.cancel(pendingIntent);
    }
    
}
