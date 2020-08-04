package com.FitBuddy.fitbuddy.UserPackage.UserFragments.UserDetails;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.FitBuddy.fitbuddy.R;
import com.FitBuddy.fitbuddy.UserPackage.UserFragments.Adapter.UserDetailsAdapter;
import com.FitBuddy.fitbuddy.UserPackage.UserFragments.FitBuddyViewHolder.UserViewHolder;
import com.FitBuddy.fitbuddy.UserPackage.UserFragments.model.UserSessionModel;
import com.FitBuddy.fitbuddy.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class UsersDetailsFragment extends Fragment {
    private final String TAG = this.getClass().getSimpleName();
    TextView name,proficiency,age,height,weight;
    FirebaseFirestore firebaseFirestoredb;
    FirebaseAuth mAuth;
    List<UserSessionModel> sessionsDetailsList=new ArrayList<>();
    List<UserSessionModel> sessionCompletedList=new ArrayList<>();
    UserViewHolder userViewHolder;
    private TabLayout tabLayout;
    Fragment selectedFragment;
    RecyclerView recyclerView,completed_recyclerView;
    @Override
    public void onStart() {
        super.onStart();
        mAuth=FirebaseAuth.getInstance();
        firebaseFirestoredb=FirebaseFirestore.getInstance();
//        loadfromfirebase();
        loadfromfirebaseWithLiveData();

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.user_details_fragment, container, false);
        initialize(view);
        return view;
    }

    private void initialize(View view) {
        name=view.findViewById(R.id.user_name);
        proficiency=view.findViewById(R.id.user_proficiency);
        age=view.findViewById(R.id.user_age);
        height=view.findViewById(R.id.user_height);
        weight=view.findViewById(R.id.user_weight);
        recyclerView=view.findViewById(R.id.selected_user_exercise_recycler_view);
        completed_recyclerView=view.findViewById(R.id.completed_user_exercise_recycler_view);
        tabLayout=(TabLayout) view.findViewById(R.id.selected_user_tabs);
    }


    private void loadfromfirebase() {
        firebaseFirestoredb.collection("users")
            .document(mAuth.getCurrentUser().getUid())
            .get()
            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()){
//                        users=task.getResult().toObject(Users.class);
//                        name.setText(users.name);
//                        proficiency.setText(users.registrationDetails.proficiency_level);
//                        age.setText(users.registrationDetails.age);
//                        height.setText(users.registrationDetails.height);
//                        weight.setText(users.registrationDetails.weight);
                    }
                }
            });
    }

    private void loadfromfirebaseWithLiveData() {
        userViewHolder= ViewModelProviders.of(getActivity()).get(UserViewHolder.class);
        userViewHolder.init();
        userViewHolder.getLiveData().observe(getActivity(), new Observer<Users>() {
            @Override
            public void onChanged(Users users) {
                Log.d(TAG,users+"");
                name.setText(users.name);
                proficiency.setText(users.registrationDetails.proficiency_level);
                age.setText(users.registrationDetails.age);
                height.setText(users.registrationDetails.height+" cm");
                weight.setText(users.registrationDetails.weight+" kg");
                List<UserSessionModel> sessionsDetailsList=users.sessionsDetailsList;
                List<UserSessionModel> sessionCompletedList=users.sessionCompletedList;

                if(sessionsDetailsList==null)
                    sessionsDetailsList=new ArrayList<>();
                if(sessionCompletedList==null)
                    sessionCompletedList=new ArrayList<>();
                UserDetailsAdapter adapter = new UserDetailsAdapter(getContext(), sessionsDetailsList,sessionCompletedList,false);
                RecyclerView.LayoutManager first_manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(first_manager);
                recyclerView.setAdapter(adapter);

                UserDetailsAdapter completed_adapter = new UserDetailsAdapter(getContext(), sessionsDetailsList,sessionCompletedList,true);
                RecyclerView.LayoutManager second_manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                completed_recyclerView.setLayoutManager(second_manager);
                completed_recyclerView.setAdapter(completed_adapter);
            }
        });
        inittablayout();

    }
    private void inittablayout() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        completed_recyclerView.setVisibility(View.INVISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                        break;

                    case 1:
                        recyclerView.setVisibility(View.INVISIBLE);
                        completed_recyclerView.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}
