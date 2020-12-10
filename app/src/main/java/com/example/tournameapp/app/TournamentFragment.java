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
import com.example.tournameapp.model.Match;
import com.example.tournameapp.model.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class TournamentFragment extends DialogFragment {

    private TournamentEditListener listener;
    private List<Match> matches;
    private List<Match> matchesPlayed;
    private List<Player> players;
    private HashMap<String, TournamentTableRow> rows;

    private ArrayList<TournamentTableRow> rowsList;


    public TournamentFragment(List<Match> matches) {
        this.matches = matches;
        matchesPlayed = new ArrayList<>();
        players = new ArrayList<>();
        rows = new HashMap<>();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof TournamentEditListener) {
            listener = (TournamentEditListener) context;
        } else {
            throw new RuntimeException(context.toString() + " Must implement TournamentEditListener!");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_table, container, false);
        initPlayers();
        initTableRows();
        Log.d("players", players.toString());
        init(view);
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

//        TableRow tbrow0 = new TableRow(getContext());
//
//        TextView tv0 = new TextView(getContext());
//        tv0.setText(" No ");
//        tv0.setTextColor(Color.BLACK);
//        tbrow0.addView(tv0);
//
//        TextView tv1 = new TextView(getContext());
//        tv1.setText(" Player ");
//        tv1.setTextColor(Color.BLACK);
//        tbrow0.addView(tv1);
//
//        TextView tv2 = new TextView(getContext());
//        tv2.setText("  W  ");
//        tv2.setTextColor(Color.BLACK);
//        tbrow0.addView(tv2);
//
//        TextView tv3 = new TextView(getContext());
//        tv3.setText("  D  ");
//        tv3.setTextColor(Color.BLACK);
//        tbrow0.addView(tv3);
//
//        TextView tv4 = new TextView(getContext());
//        tv4.setText("  L  ");
//        tv4.setTextColor(Color.BLACK);
//        tbrow0.addView(tv4);
//
//        TextView tv6 = new TextView(getContext());
//        tv6.setText("  F  ");
//        tv6.setTextColor(Color.BLACK);
//        tbrow0.addView(tv6);
//
//        TextView tv7 = new TextView(getContext());
//        tv7.setText("  A  ");
//        tv7.setTextColor(Color.BLACK);
//        tbrow0.addView(tv7);
//
//        TextView tv8 = new TextView(getContext());
//        tv8.setText("  GD  ");
//        tv8.setTextColor(Color.BLACK);
//        tbrow0.addView(tv8);
//
//        TextView tv5 = new TextView(getContext());
//        tv5.setText(" PTS ");
//        tv5.setTextColor(Color.BLACK);
//        tbrow0.addView(tv5);
//
//        tournamentTbl.addView(tbrow0);
//        for (int i = 1; i < 10; i++) {
//            TableRow tbrow = new TableRow(getContext());
//
//            TextView t1v = new TextView(getContext());
//            t1v.setText("" + i);
//            t1v.setTextColor(Color.BLACK);
//            tbrow.addView(t1v);
//
//            TextView t2v = new TextView(getContext());
//            t2v.setText("TEAM " + i);
//            t2v.setTextColor(Color.BLACK);
//            tbrow.addView(t2v);
//
//            TextView t3v = new TextView(getContext());
//            t3v.setText(" " + (40 - i));
//            t3v.setTextColor(Color.BLACK);
//            tbrow.addView(t3v);
//
//            TextView t3va = new TextView(getContext());
//            t3va.setText(" " + (40 - i));
//            t3va.setTextColor(Color.BLACK);
//            tbrow.addView(t3va);
//
//            TextView t3vab = new TextView(getContext());
//            t3vab.setText(" " + (40 - i));
//            t3vab.setTextColor(Color.BLACK);
//            tbrow.addView(t3vab);
//
//            TextView t3vsd = new TextView(getContext());
//            t3vsd.setText(" " + (40 - i));
//            t3vsd.setTextColor(Color.BLACK);
//            tbrow.addView(t3vsd);
//
//            TextView t4v = new TextView(getContext());
//            t4v.setText(" " + i * 2);
//            t4v.setTextColor(Color.BLACK);
//            tbrow.addView(t4v);
//
//            TextView t5v = new TextView(getContext());
//            t5v.setText(" " + (2 + i));
//            t5v.setTextColor(Color.BLACK);
//            tbrow.addView(t5v);
//
//            TextView t6v = new TextView(getContext());
//            t6v.setText(" " + (100 - i));
//            t6v.setTextColor(Color.BLACK);
//            tbrow.addView(t6v);
//
//
//            tournamentTbl.addView(tbrow);
//
//        }
//        Log.d("after init", "after init went hello my name is koby");

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
