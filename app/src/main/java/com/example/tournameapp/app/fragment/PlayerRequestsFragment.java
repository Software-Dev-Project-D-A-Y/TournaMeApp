package com.example.tournameapp.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.tournameapp.utils.NotificationHelper;
import com.example.tournameapp.R;
import com.example.tournameapp.interfaces.PlayerActionsListener;
import com.example.tournameapp.model.TournamentRequest;

import java.util.List;

public class PlayerRequestsFragment extends DialogFragment {

    private List<TournamentRequest> requests;
    private ListView myRequestsLw;

    private PlayerActionsListener observer;

    public PlayerRequestsFragment(List<TournamentRequest> requests) {
        this.requests = requests;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof PlayerActionsListener){
            observer = (PlayerActionsListener) context;
        } else {
            throw new RuntimeException(context.toString()+" Must implement PlayerObserver interface!");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player_requests, container, false);


        myRequestsLw = (ListView) view.findViewById(R.id.playerRequestsLw);
        ArrayAdapter<TournamentRequest> adapter = new ArrayAdapter<>(getContext(),R.layout.layout_tournaments_list,requests);
        myRequestsLw.setAdapter(adapter);

        myRequestsLw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TournamentRequest requestChose = requests.get(position);
                observer.onRequestApproved(requestChose);
                NotificationHelper notificationHelper = NotificationHelper.getInstance(getContext());
                notificationHelper.addRequestApprovedNotification(requestChose);
                dismiss();
            }
        });

        return view;
    }

}
