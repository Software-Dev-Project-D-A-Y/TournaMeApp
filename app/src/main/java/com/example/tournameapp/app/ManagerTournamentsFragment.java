package com.example.tournameapp.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.tournameapp.interfaces.ManagerObserver;
import com.example.tournameapp.interfaces.PlayerObserver;
import com.example.tournameapp.model.Tournament;
import com.example.tournameapp.model.TournamentRequest;

import java.util.List;

public class ManagerTournamentsFragment extends DialogFragment {

    private TextView loadingLbl;
    private ListView myTournamentsLv;

    private List<Tournament> tournaments;

    private ManagerObserver observer;

    public ManagerTournamentsFragment(List<Tournament> tournaments){
        this.tournaments = tournaments;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof ManagerObserver){
            observer = (ManagerObserver) context;
        } else {
            throw new RuntimeException(context.toString()+" Must implement PlayerObserver interface!");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_manager_tournaments, container, false);

        loadingLbl = (TextView) view.findViewById(R.id.loadingLbl);
        myTournamentsLv = (ListView) view.findViewById(R.id.myTournamentLv);

        ArrayAdapter<Tournament> adapter = new ArrayAdapter<>(getContext(),R.layout.layout_tournaments_list,tournaments);
        myTournamentsLv.setAdapter(adapter);

        myTournamentsLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tournament tournamentChose = tournaments.get(position);

                Intent intent = new Intent(getContext(),ManagerEditTournamentActivity.class);
                intent.putExtra("tournamentChose",tournamentChose.getId());
                startActivity(intent);
            }
        });


        return view;
    }
}
