package com.FitBuddy.fitbuddy.TrainerPackage.TrainerViewHolder;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.FitBuddy.fitbuddy.TrainerPackage.Model.TrainerSession;
import com.FitBuddy.fitbuddy.TrainerPackage.TrainerRepository.TrainerListRepisitory;

import java.util.List;

public class TrainerViewHolder extends ViewModel {
    private String TAG=this.getClass().getSimpleName();
    MutableLiveData<List<TrainerSession>> trainerDetails;

    public void init(Context context){
        trainerDetails= TrainerListRepisitory.getInstance().getTrainerDetails(context);
        Log.d(TAG,trainerDetails+"");
    }

    public LiveData<List<TrainerSession>> getLiveData(){
        return trainerDetails;
    }
}
