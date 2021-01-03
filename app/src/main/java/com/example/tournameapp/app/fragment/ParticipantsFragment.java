package com.example.tournameapp.app.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.tournameapp.R;
import com.example.tournameapp.adapters.ParticipantsListAdapter;
import com.example.tournameapp.adapters.TournamentMatchesListAdapter;
import com.example.tournameapp.interfaces.EditTournamentListener;
import com.example.tournameapp.interfaces.ManagerActionsListener;
import com.example.tournameapp.interfaces.OnKickListener;
import com.example.tournameapp.interfaces.PlayerActionsListener;
import com.example.tournameapp.model.Player;
import com.example.tournameapp.model.Tournament;
import com.example.tournameapp.presenters.ParticipantsPresenter;
import com.example.tournameapp.presenters.PlayerTournamentsPresenter;

import java.util.List;

public class ParticipantsFragment extends DialogFragment implements OnKickListener {

    private Tournament tournament;
    private List<Player> players;

    private ListView participantsLv;
    private ParticipantsListAdapter adapter;
    private EditTournamentListener listener;
    private ParticipantsPresenter presenter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EditTournamentListener) {
            listener = (EditTournamentListener) context;
        } else {
            throw new RuntimeException(context.toString() + " Must implement EditTournamentListener interface!");
        }
    }

    public ParticipantsFragment(Tournament tournament, List<Player> players) {
        this.tournament = tournament;
        this.players = players;
        this.presenter = new ParticipantsPresenter(this, tournament);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_participants, container, false);

        adapter = new ParticipantsListAdapter(getContext(), R.layout.layout_tournament_participants, players);
        adapter.setOnKickListener(this);

        participantsLv = (ListView) view.findViewById(R.id.participantsLv);
        participantsLv.setAdapter(adapter);


        return view;
    }

    @Override
    public void onKickPlayerClicked(Player player) {
        kickPlayerDialog(player);
    }

    private void kickPlayerDialog(final Player player) {
        if(tournament.isActive()){
            Toast.makeText(getContext(), "Cannot kick while tournament is running!", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Kick " + player.getUserName());
        builder.setMessage("Are you sure?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                presenter.kickPlayer(player);
                players.remove(player);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onKick(String message) {
        adapter.notifyDataSetChanged();
        listener.refresh();
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        dismiss();
    }
}
