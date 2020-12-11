package com.example.tournameapp.utils;

import android.os.Build;
import android.util.Log;

import com.example.tournameapp.app.TournamentFragment;
import com.example.tournameapp.database.MatchesService;
import com.example.tournameapp.interfaces.OnDataLoadedListener;
import com.example.tournameapp.interfaces.TournamentListener;
import com.example.tournameapp.model.Match;
import com.example.tournameapp.model.Player;
import com.example.tournameapp.model.Tournament;
import com.example.tournameapp.model.TournamentTableRow;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class TournamentPresenter {

    private TournamentListener listener;
    private MatchesService matchesService;
    private Tournament tournament;
    private List<Match> matchesPlayed;
    private List<Player> players;
    private HashMap<String, TournamentTableRow> rows;
    private ArrayList<TournamentTableRow> rowsList;
    private List<Match> matches;



    public TournamentPresenter(TournamentListener listener, Tournament tournament) {
        this.listener = listener;
        this.tournament = tournament;
        matchesPlayed = new ArrayList<>();
        rows = new HashMap<>();
        matchesService = MatchesService.getInstance();
    }

    public void loadAllMatches() {
        matchesService.loadAllMatches(tournament, new OnDataLoadedListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                matches = new ArrayList<>();
                for(DataSnapshot child : dataSnapshot.getChildren()) {
                    Match match = child.getValue(Match.class);
                    matches.add(match);
                }
                Log.d("matches",matches.toString());
                initPlayers();
                listener.onMatchesLoad(matches);
            }
        });
    }

        private void initPlayers() {
        players = new ArrayList<>();
        for (Match match : matches) {
            if (!players.contains(match.getHomePlayer())) {
                players.add(match.getHomePlayer());
            }
        }
    }


    public List<TournamentTableRow>  initTableRows() {
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
    return rowsList;
    }




}
