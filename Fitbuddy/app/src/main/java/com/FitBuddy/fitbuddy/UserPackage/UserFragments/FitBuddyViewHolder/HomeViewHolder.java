package com.FitBuddy.fitbuddy.UserPackage.UserFragments.FitBuddyViewHolder;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.FitBuddy.fitbuddy.UserPackage.UserFragments.FitBuddyRepository.AerbocisHomeListRepository;
import com.FitBuddy.fitbuddy.UserPackage.UserFragments.FitBuddyRepository.FitnessHomeListRepository;
import com.FitBuddy.fitbuddy.UserPackage.UserFragments.FitBuddyRepository.YogaHomeListRepository;
import com.FitBuddy.fitbuddy.UserPackage.UserFragments.model.SessionsDetails;

import java.util.List;

public class HomeViewHolder extends ViewModel {
    MutableLiveData<List<SessionsDetails>> YogasessionDetailsList;
    MutableLiveData<List<SessionsDetails>> AerobicssessionDetailsList;
    MutableLiveData<List<SessionsDetails>> FitnesssessionDetailsList;

    public void init(String exercise) {
//        if (exercise.equals("Yoga") && YogasessionDetailsList != null)
//            return;
//        else if (exercise.equals("Aerobics") && AerobicssessionDetailsList != null)
//            return;
//        else if (exercise.equals("Fitness") && FitnesssessionDetailsList != null)
//            return;
        if (exercise.equals("Yoga"))
            YogasessionDetailsList = YogaHomeListRepository.getInstance().getSessionDetails("yoga");
        else if (exercise.equals("Aerobics"))
            AerobicssessionDetailsList= AerbocisHomeListRepository.getInstance().getSessionDetails("aerobics-zumba");
        else if (exercise.equals("Fitness"))
            FitnesssessionDetailsList= FitnessHomeListRepository.getInstance().getSessionDetails("strength_workout");
    }

    public LiveData<List<SessionsDetails>> getLiveDetails(String exercise) {
        if (exercise.equals("Yoga"))
            return YogasessionDetailsList;
        else if (exercise.equals("Aerobics"))
            return AerobicssessionDetailsList;
        else if (exercise.equals("Fitness"))
            return FitnesssessionDetailsList;
        return null;
    }
}
