package com.example.tournameapp.app;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.example.tournameapp.R;
import com.example.tournameapp.interfaces.TournamentEditListener;
import com.example.tournameapp.interfaces.TournamentListener;
import com.example.tournameapp.model.Match;
import com.example.tournameapp.model.Player;
import com.example.tournameapp.model.Tournament;
import com.example.tournameapp.model.TournamentTableRow;
import com.example.tournameapp.utils.TournamentPresenter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class TournamentFragment extends DialogFragment implements TournamentListener {

    TournamentPresenter presenter;
    private List<Match> matches;
    private List<Match> matchesPlayed;
    private List<Player> players;
    private List<TournamentTableRow> rowsList;




    public TournamentFragment(Tournament tournament){
        presenter = new TournamentPresenter(this,tournament);
        matchesPlayed = new ArrayList<>();
        players = new ArrayList<>();
        presenter.loadAllMatches();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_table, container, false);

        return view;
    }

    private void init(View view) {
        TableLayout tournamentTbl = (TableLayout) view.findViewById(R.id.tournamentTbl);
        rowsList =  presenter.initTableRows();
        for(int i = 0 ; i < rowsList.size() ; i++) {
            TournamentTableRow row = rowsList.get(i);


            // Position
            TableRow i_stPlaceRow = new TableRow(getContext());
            TextView posTxt = new TextView(getContext());
            posTxt.setText(" " +(i+ 1) +"");
            posTxt.setTextColor(Color.BLACK);
            i_stPlaceRow.addView(posTxt);


            //userName - Player
            TextView playerTxt = new TextView(getContext());
            playerTxt.setText(row.getPlayer().getUserName());
            playerTxt.setTextColor(Color.BLACK);
            i_stPlaceRow.addView(playerTxt);


            //wins
            TextView wTxt = new TextView(getContext());
            wTxt.setText(row.getWins()+"");
            wTxt.setTextColor(Color.BLACK);
            i_stPlaceRow.addView(wTxt);

            //draws
            TextView dTxt = new TextView(getContext());
            dTxt.setText(row.getDraws()+"");
            dTxt.setTextColor(Color.BLACK);
            i_stPlaceRow.addView(dTxt);

            //loses
            TextView lTxt = new TextView(getContext());
            lTxt.setText(row.getLoses()+"");
            lTxt.setTextColor(Color.BLACK);
            i_stPlaceRow.addView(lTxt);

            //forGoals
            TextView forTxt = new TextView(getContext());
            forTxt.setText(row.getForGoals()+"");
            forTxt.setTextColor(Color.BLACK);
            i_stPlaceRow.addView(forTxt);

            //aggaintsGoals
            TextView againstTxt = new TextView(getContext());
            againstTxt.setText(row.getAgainstGoals()+"");
            againstTxt.setTextColor(Color.BLACK);
            i_stPlaceRow.addView(againstTxt);

            //goalsDiffernet
            TextView diffTxt = new TextView(getContext());
            diffTxt.setText(" "+row.getGoalsDifference()+"");
            diffTxt.setTextColor(Color.BLACK);
            i_stPlaceRow.addView(diffTxt);

            //points
            TextView ptsTxt = new TextView(getContext());
            ptsTxt.setText(row.getPoints()+"");
            ptsTxt.setTextColor(Color.BLACK);
            i_stPlaceRow.addView(ptsTxt);

            tournamentTbl.addView(i_stPlaceRow);
        }

    }

    @Override
    public void onMatchesLoad(List<Match> matches) {
        this.matches = matches;

    }

    @Override
    public void onMatchesPlayedLoaded(List<Match> matchesPlayed) {
        presenter.setMatchesPlayed(matchesPlayed);
        Log.d("Dovie","The Gay");
        View view = getView();
        init(view);
    }


}
