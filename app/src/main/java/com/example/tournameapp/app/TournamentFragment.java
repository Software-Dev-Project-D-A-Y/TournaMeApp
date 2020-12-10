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
import com.example.tournameapp.utils.TournamentPresenter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class TournamentFragment extends DialogFragment implements TournamentListener {

    TournamentPresenter presenter;
   // private TournamentEditListener listener; // problem because its comimg from manager
    private List<Match> matches;
    private List<Match> matchesPlayed;
    private List<Player> players;
    private HashMap<String, TournamentTableRow> rows;

    private ArrayList<TournamentTableRow> rowsList;


    public TournamentFragment(Tournament tournament){
        presenter = new TournamentPresenter(this,tournament);
        matchesPlayed = new ArrayList<>();
        players = new ArrayList<>();
        rows = new HashMap<>();
        presenter.loadAllMatches();

    }

    public TournamentFragment(List<Match> matches) {
        this.matches = matches;
        matchesPlayed = new ArrayList<>();
        players = new ArrayList<>();
        rows = new HashMap<>();
    }

//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        if (context instanceof TournamentEditListener) {
//            listener = (TournamentEditListener) context;
//        } else {
//            throw new RuntimeException(context.toString() + " Must implement TournamentEditListener!");
//        }
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_table, container, false);

        return view;
    }

    private void initPlayers() {
        for (Match match : matches) {
            if (!players.contains(match.getHomePlayer())) {
                players.add(match.getHomePlayer());
            }
        }
    }

    private void initTableRows() {
        for (Player player : players) {
            TournamentTableRow row = new TournamentTableRow(player);
            rows.put(player.getUserName(), row);
        }

        for (Match match : matchesPlayed) {
            String homePlayer = match.getHomePlayer().getUserName();
            String awayPlayer = match.getAwayPlayer().getUserName();
            int homeGoals = match.getHomeScore();
            int awayGoals = match.getAwayScore();
            TournamentTableRow homePlayerRow = rows.get(homePlayer);
            TournamentTableRow awayPlayerRow = rows.get(awayPlayer);

            homePlayerRow.updateRow(homeGoals, awayGoals);
            awayPlayerRow.updateRow(awayGoals, homeGoals);
        }


        rowsList = new ArrayList<>();
        for (TournamentTableRow row : rows.values()){
            rowsList.add(row);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            rowsList.sort(new TournamentTableRow());
        }

    }

    public void init(View view) {
        TableLayout tournamentTbl = (TableLayout) view.findViewById(R.id.tournamentTbl);

        for(int i = 0 ; i < rowsList.size() ; i++) {
            TournamentTableRow row = rowsList.get(i);

            TableRow i_stPlaceRow = new TableRow(getContext());
            TextView posTxt = new TextView(getContext());
            posTxt.setText(i+ 1 +"");
            posTxt.setTextColor(Color.BLACK);
            i_stPlaceRow.addView(posTxt);

            TextView playerTxt = new TextView(getContext());
            playerTxt.setText(row.player.getUserName());
            playerTxt.setTextColor(Color.BLACK);
            i_stPlaceRow.addView(playerTxt);

            TextView wTxt = new TextView(getContext());
            wTxt.setText(row.wins+"");
            wTxt.setTextColor(Color.BLACK);
            i_stPlaceRow.addView(wTxt);

            TextView dTxt = new TextView(getContext());
            dTxt.setText(row.draws+"");
            dTxt.setTextColor(Color.BLACK);
            i_stPlaceRow.addView(dTxt);

            TextView lTxt = new TextView(getContext());
            lTxt.setText(row.loses+"");
            lTxt.setTextColor(Color.BLACK);
            i_stPlaceRow.addView(lTxt);

            TextView forTxt = new TextView(getContext());
            forTxt.setText(row.forGoals+"");
            forTxt.setTextColor(Color.BLACK);
            i_stPlaceRow.addView(forTxt);

            TextView againstTxt = new TextView(getContext());
            againstTxt.setText(row.againstGoals+"");
            againstTxt.setTextColor(Color.BLACK);
            i_stPlaceRow.addView(againstTxt);

            TextView diffTxt = new TextView(getContext());
            diffTxt.setText(row.goalsDifference+"");
            diffTxt.setTextColor(Color.BLACK);
            i_stPlaceRow.addView(diffTxt);

            TextView ptsTxt = new TextView(getContext());
            ptsTxt.setText(row.points+"");
            ptsTxt.setTextColor(Color.BLACK);
            i_stPlaceRow.addView(ptsTxt);

            tournamentTbl.addView(i_stPlaceRow);
        }

    }

    @Override
    public void onMatchesLoad(List<Match> matches) {
        this.matches = matches;
        View view = getView();

        initPlayers();
        initTableRows();
        init(view);
    }

    private class TournamentTableRow implements Comparator<TournamentTableRow>{
        Player player;
        int wins, draws, loses, forGoals, againstGoals, goalsDifference, points;

        public TournamentTableRow(){}

        public TournamentTableRow(Player player) {
            this.player = player;
        }

        public void updateRow(int forGoals, int againstGoals) {
            this.forGoals += forGoals;
            this.againstGoals += againstGoals;
            this.goalsDifference = this.forGoals - this.againstGoals;
            if(forGoals > againstGoals) {
                wins++;
                points += 3;
            } else if(forGoals == againstGoals) {
                draws++;
                points++;
            } else {
                loses++;
            }
        }

        @Override
        public String toString() {
            return "TournamentTableRow{" +
                    "player=" + player.getUserName() +
                    ", points=" + points +
                    '}';
        }

        @Override
        public int compare(TournamentTableRow o1, TournamentTableRow o2) {
            int comp = o2.player.getUserName().compareTo(o1.player.getUserName());
            return comp;
        }
    }

}
