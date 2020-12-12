package com.example.tournameapp.app.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.tournameapp.R;
import com.example.tournameapp.interfaces.TournamentEditListener;
import com.example.tournameapp.model.Match;

import java.util.List;

public class UpdateScoreFragment extends DialogFragment {

    private List<Match> matches;
    private ListView matchesLv;

    private TournamentEditListener listener;

    public UpdateScoreFragment(List<Match> matches){
        this.matches = matches;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof TournamentEditListener) {
            listener = (TournamentEditListener) context;
        } else {
            throw new RuntimeException(context+" need to implements TournamentEditListener interface");
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_score, container, false);

        matchesLv = (ListView) view.findViewById(R.id.mathcesLv);
        ArrayAdapter<Match> adapter = new ArrayAdapter<>(getContext(), R.layout.layout_tournaments_list, matches);
        matchesLv.setAdapter(adapter);

        matchesLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editMatchDialog(matches.get(position));
            }
        });

        return view;
    }

    private void editMatchDialog(final Match match) {
        LayoutInflater factory = LayoutInflater.from(getContext());
        final View textEntryView = factory.inflate(R.layout.layout_edit_match, null);
        final TextView homePlayerTxt = textEntryView.findViewById(R.id.homePlayerTxt);
        final TextView awayPlayerTxt = textEntryView.findViewById(R.id.awayPlayerTxt);
        final EditText homePlayerScoreTxt = textEntryView.findViewById(R.id.homePlayerScoreTxt);
        final EditText awayPlayerScoreTxt = textEntryView.findViewById(R.id.awayPlayerScoreTxt);

        homePlayerTxt.setText(match.getHomePlayer().getUserName()+":");
        awayPlayerTxt.setText(match.getAwayPlayer().getUserName()+":");

        if(match.isUpdated()){
            homePlayerScoreTxt.setText(match.getHomeScore()+"");
            awayPlayerScoreTxt.setText(match.getAwayScore()+"");
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

                listener.onMatchUpdated(match);
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

}

