package com.example.tournameapp.app.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.tournameapp.R;
import com.example.tournameapp.adapters.TournamentMatchesListAdapter;
import com.example.tournameapp.interfaces.EditTournamentListener;
import com.example.tournameapp.interfaces.OnMatchUpdateListener;
import com.example.tournameapp.model.Match;
import com.example.tournameapp.presenters.TournamentMatchesPresenter;

import java.util.List;

public class TournamentMatchesFragment extends DialogFragment implements OnMatchUpdateListener {

    private List<Match> matches;
    private ListView matchesLv;

    private EditTournamentListener listener;
    private TournamentMatchesListAdapter adapter;
    private TournamentMatchesPresenter presenter;

    public TournamentMatchesFragment(List<Match> matches) {
        presenter = new TournamentMatchesPresenter(this);
        this.matches = matches;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EditTournamentListener) {
            listener = (EditTournamentListener) context;
        } else {
            listener = null;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tournament_matches, container, false);

        matchesLv = (ListView) view.findViewById(R.id.mathcesLv);
        adapter = new TournamentMatchesListAdapter(getContext(), R.layout.layout_matches, matches);
        matchesLv.setAdapter(adapter);

        if (listener != null) {
            matchesLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    editMatchDialog(matches.get(position));
                }
            });
        } else {
            matchesLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    showMatchDialog(matches.get(position));
                }
            });
        }
        return view;
    }

    private void showMatchDialog(final Match match) {
        LayoutInflater factory = LayoutInflater.from(getContext());
        final View textEntryView = factory.inflate(R.layout.layout_show_match, null);
        final TextView homePlayerTxt = textEntryView.findViewById(R.id.showHomeNameTxt);
        final TextView awayPlayerTxt = textEntryView.findViewById(R.id.showAwayNameTxt);
        final TextView homePlayerScoreTxt = textEntryView.findViewById(R.id.showHomeScoreTxt);
        final TextView awayPlayerScoreTxt = textEntryView.findViewById(R.id.showAwayScoreTxt);

        homePlayerTxt.setText(match.getHomePlayer().getUserName() + ":");
        awayPlayerTxt.setText(match.getAwayPlayer().getUserName() + ":");

        if (match.isUpdated()) {
            homePlayerScoreTxt.setText(match.getHomeScore() + "");
            awayPlayerScoreTxt.setText(match.getAwayScore() + "");
        } else {
            return;
        }

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle(match.matchToString());
        alert.setView(textEntryView);

        alert.setNegativeButton("Close",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
        alert.show();
    }

    private void editMatchDialog(final Match match) {
        LayoutInflater factory = LayoutInflater.from(getContext());
        final View textEntryView = factory.inflate(R.layout.layout_edit_match, null);
        final TextView homePlayerTxt = textEntryView.findViewById(R.id.homePlayerTxt);
        final TextView awayPlayerTxt = textEntryView.findViewById(R.id.awayPlayerTxt);
        final EditText homePlayerScoreTxt = textEntryView.findViewById(R.id.homePlayerScoreTxt);
        final EditText awayPlayerScoreTxt = textEntryView.findViewById(R.id.awayPlayerScoreTxt);

        homePlayerTxt.setText(match.getHomePlayer().getUserName() + ":");
        awayPlayerTxt.setText(match.getAwayPlayer().getUserName() + ":");

        if (match.isUpdated()) {
            homePlayerScoreTxt.setText(match.getHomeScore() + "");
            awayPlayerScoreTxt.setText(match.getAwayScore() + "");
        }

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle(match.matchToString());
        alert.setView(textEntryView);


        alert.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                int homePlayerNewScore = Integer.parseInt(homePlayerScoreTxt.getText().toString());
                int awayPlayerNewScore = Integer.parseInt(awayPlayerScoreTxt.getText().toString());

                match.setHomeScore(homePlayerNewScore);
                match.setAwayScore(awayPlayerNewScore);
                match.setUpdated(true);

                presenter.updateMatch(match);
                return;
            }
        });

        alert.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
        alert.show();
    }

    @Override
    public void onMatchUpdated(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        adapter.notifyDataSetChanged();
        // REFRESH
    }
}

