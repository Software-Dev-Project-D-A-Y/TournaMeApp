package com.example.tournameapp.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.tournameapp.R;
import com.example.tournameapp.adapters.ManagerTournamentsListAdapter;
import com.example.tournameapp.app.activity.EditTournamentActivity;
import com.example.tournameapp.interfaces.ManagerActionsListener;
import com.example.tournameapp.model.Tournament;

import java.util.List;

public class ManagerTournamentsFragment extends DialogFragment {

    private TextView loadingLbl;
    private ListView myTournamentsLv;

    private List<Tournament> tournaments;

    private ManagerActionsListener listener;

    public ManagerTournamentsFragment(List<Tournament> tournaments){
        this.tournaments = tournaments;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof ManagerActionsListener){
            listener = (ManagerActionsListener) context;
        } else {
            throw new RuntimeException(context.toString()+" Must implement ManagerActionsListener interface!");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manager_tournaments, container, false);

        loadingLbl = (TextView) view.findViewById(R.id.loadingLbl);
        myTournamentsLv = (ListView) view.findViewById(R.id.mMyTournamentLv);

        ManagerTournamentsListAdapter adapter = new ManagerTournamentsListAdapter(getContext(),R.layout.layout_my_tournaments,tournaments);
        myTournamentsLv.setAdapter(adapter);

        myTournamentsLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tournament tournamentChose = tournaments.get(position);

                Intent intent = new Intent(getContext(), EditTournamentActivity.class);
                intent.putExtra("tournamentChose",tournamentChose.getId());
                startActivity(intent);
            }
        });

        return view;
    }
}
