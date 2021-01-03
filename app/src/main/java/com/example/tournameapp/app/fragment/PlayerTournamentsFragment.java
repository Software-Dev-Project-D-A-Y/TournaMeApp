package com.example.tournameapp.app.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
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
import com.example.tournameapp.adapters.PlayerTournamentsListAdapter;
import com.example.tournameapp.interfaces.OnLeaveListener;
import com.example.tournameapp.interfaces.PlayerActionsListener;
import com.example.tournameapp.model.Player;
import com.example.tournameapp.model.Tournament;
import com.example.tournameapp.presenters.PlayerTournamentsPresenter;

import java.util.List;

public class PlayerTournamentsFragment extends DialogFragment implements OnLeaveListener {


    private List<Tournament> tournaments;
    private PlayerTournamentsPresenter presenter;
    private Player player;

    private ListView myTournamentsLv;
    private PlayerTournamentsListAdapter adapter;

    private PlayerActionsListener listener;

    public PlayerTournamentsFragment(List<Tournament> tournaments) {
        this.tournaments = tournaments;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof PlayerActionsListener) {
            listener = (PlayerActionsListener) context;
        } else {
            throw new RuntimeException(context.toString() + " Must implement PlayerActionsListener interface!");
        }
        player = listener.getPlayer();
        presenter = new PlayerTournamentsPresenter(this,player);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player_tournaments, container, false);

        adapter = new PlayerTournamentsListAdapter(getContext(),R.layout.layout_player_tournaments, tournaments);
        adapter.setOnLeaveListener(this);

        myTournamentsLv = (ListView) view.findViewById(R.id.pMyTournamentsLv);
        myTournamentsLv.setAdapter(adapter);

        myTournamentsLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tournament tournamentChose = tournaments.get(position);
                TournamentFragment tournamentFragment = new TournamentFragment(tournamentChose, listener.getPlayer());
                tournamentFragment.show(getFragmentManager(), "player Tournament");
            }
        });

        return view;
    }

    private void leaveTournamentDialog(final Tournament tourToLeave) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Leave "+tourToLeave.getTournamentName());
        builder.setMessage("Are you sure?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                presenter.leaveTournament(tourToLeave);
                tournaments.remove(tourToLeave);
                adapter.notifyDataSetChanged();
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
    public void onLeaveTournamentClicked(Tournament tourToLeave) {
        leaveTournamentDialog(tourToLeave);
    }

    @Override
    public void onLeave(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        dismiss();
        listener.refresh();
    }
}


