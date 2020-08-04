package com.FitBuddy.fitbuddy.UserPackage.UserFragments.UserDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.FitBuddy.fitbuddy.R;
import com.FitBuddy.fitbuddy.UserPackage.UserFragments.Adapter.UserDetailsAdapter;
import com.FitBuddy.fitbuddy.UserPackage.UserFragments.model.UserSessionModel;

import java.util.List;

public class UpcomingSessionFragment extends Fragment {
    RecyclerView recyclerView;
    List<UserSessionModel> sessionsDetailsList;
    List<UserSessionModel> sessionCompletedList;
    boolean completedSession;

    public UpcomingSessionFragment(List<UserSessionModel> sessionsDetailsList, List<UserSessionModel> sessionCompletedList, boolean completedSession) {
        this.sessionsDetailsList = sessionsDetailsList;
        this.sessionCompletedList = sessionCompletedList;
        this.completedSession = completedSession;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_sessions_list, container, false);
        initialize(view);
        return view;
    }

    private void initialize(View view) {
        recyclerView=view.findViewById(R.id.user_sessions_exercise_recycler_view);
        UserDetailsAdapter adapter = new UserDetailsAdapter(getContext(), sessionsDetailsList,sessionCompletedList,completedSession);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }
}
