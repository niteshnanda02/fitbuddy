package com.FitBuddy.fitbuddy.UserPackage.UserFragments.FitBuddyViewHolder;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.FitBuddy.fitbuddy.UserPackage.UserFragments.FitBuddyRepository.UserListRepository;
import com.FitBuddy.fitbuddy.model.Users;

public class UserViewHolder extends ViewModel {

    MutableLiveData<Users> userDetails;

    public void init(){
//        if (userDetails !=null)
//            return;
        userDetails = UserListRepository.getInstance().getUsersDetails();
    }

    public LiveData<Users> getLiveData(){
        return userDetails;
    }

}
