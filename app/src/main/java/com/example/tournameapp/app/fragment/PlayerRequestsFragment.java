package com.example.tournameapp.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.tournameapp.interfaces.OnRequestApprovedListener;
import com.example.tournameapp.presenters.PlayerRequestsPresenter;
import com.example.tournameapp.utils.NotificationHelper;
import com.example.tournameapp.R;
import com.example.tournameapp.interfaces.PlayerActionsListener;
import com.example.tournameapp.model.TournamentRequest;

import java.util.List;

public class PlayerRequestsFragment extends DialogFragment implements OnRequestApprovedListener {

    private PlayerRequestsPresenter presenter;
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
        presenter = new PlayerRequestsPresenter(this);
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
                presenter.setRequestApproved(requestChose);
                NotificationHelper notificationHelper = NotificationHelper.getInstance(getContext());
                notificationHelper.addRequestApprovedNotification(requestChose);
            }
        });

        return view;
    }

    @Override
    public void onRequestApproved(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        observer.refresh();
        dismiss();
    }

    @Override
    public void onRequestFailure(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        dismiss();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        presenter = null;
    }
}
