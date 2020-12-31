package com.example.tournameapp.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.tournameapp.R;
import com.example.tournameapp.adapters.PlayerTournamentsListAdapter;
import com.example.tournameapp.adapters.TournamentMatchesListAdapter;
import com.example.tournameapp.interfaces.PlayerObserver;
import com.example.tournameapp.model.Tournament;

import java.util.List;

public class PlayerMyTournamentsFragment extends DialogFragment {

    private List<Tournament> tournaments;
    private TextView loadingLbl;
    private ListView myTournamentsLv;
    private PlayerTournamentsListAdapter adapter;

    private PlayerObserver observer;

    public PlayerMyTournamentsFragment(List<Tournament> tournaments) {
        this.tournaments = tournaments;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof PlayerObserver) {
            observer = (PlayerObserver) context;
        } else {
            throw new RuntimeException(context.toString() + " Must implement PlayerObserver interface!");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player_tournaments, container, false);

        adapter = new PlayerTournamentsListAdapter(getContext(),R.layout.layout_player_tournaments, tournaments);
        loadingLbl = (TextView) view.findViewById(R.id.loadingLbl);
        myTournamentsLv = (ListView) view.findViewById(R.id.pMyTournamentsLv);

        myTournamentsLv.setAdapter(adapter);

        myTournamentsLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tournament tournamentChose = tournaments.get(position);
                TournamentFragment tournamentFragment = new TournamentFragment(tournamentChose,observer.getPlayer());
                tournamentFragment.show(getFragmentManager(), "player Tournament");
            }
        });

        return view;
    }
}


