package com.FitBuddy.fitbuddy.UserPackage.UserFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.FitBuddy.fitbuddy.R;
import com.FitBuddy.fitbuddy.UserPackage.UserFragments.Adapter.UserHomeAdapter;
import com.FitBuddy.fitbuddy.UserPackage.UserFragments.Designing.CirclePagerIndicatorDecoration;
import com.FitBuddy.fitbuddy.UserPackage.UserFragments.FitBuddyViewHolder.UserViewHolder;

import java.util.ArrayList;
import java.util.List;

public class UserHomeFragment extends Fragment {
    RecyclerView recyclerView;
    TextView user_name;
    UserViewHolder userViewHolder;
    UserHomeAdapter userHomeAdapter;
    private java.lang.String TAG=this.getClass().getSimpleName();
    String name="";
    @Override
    public void onStart() {
        super.onStart();


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.user_home_fragment,container,false);
        initialize(view);

        return view;
    }

    private void initialize(View view) {
        user_name=view.findViewById(R.id.user_home_name);

        recyclerView=view.findViewById(R.id.user_home_recycler_view);
        setrecyclerView();
////        recyclerView.setHasFixedSize(true);
////
////        homeViewHolder= ViewModelProviders.of(getActivity()).get(HomeViewHolder.class);
////        homeViewHolder.init();
////        homeViewHolder.getLiveDetails().observe(getActivity(), new Observer<List<SessionsDetails>>() {
////            @Override
////            public void onChanged(List<SessionsDetails> sessionsDetails) {
////                UserHomeAdapter userHomeAdapter=new UserHomeAdapter(getContext(),sessionsDetails);
////                RecyclerView.LayoutManager manager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
////                recyclerView.setLayoutManager(manager);
////                recyclerView.setAdapter(userHomeAdapter);
////                recyclerView.addItemDecoration(new CirclePagerIndicatorDecoration());
//
//            }
//        });

    }

    private void setrecyclerView() {
        List<java.lang.String> list=new ArrayList<>();
        list.add("Yoga");
        list.add("Fitness");
        list.add("Aerobics");
        UserHomeAdapter userHomeAdapter=new UserHomeAdapter(getContext(),list);
        RecyclerView.LayoutManager manager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(userHomeAdapter);
                recyclerView.addItemDecoration(new CirclePagerIndicatorDecoration());


    }
}
