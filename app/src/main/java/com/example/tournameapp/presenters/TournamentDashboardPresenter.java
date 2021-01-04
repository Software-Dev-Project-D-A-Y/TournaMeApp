package com.example.tournameapp.presenters;

import com.example.tournameapp.interfaces.TournamentDataListener;
import com.example.tournameapp.database.MatchesService;
import com.example.tournameapp.database.TournamentsService;
import com.example.tournameapp.database.UsersService;
import com.example.tournameapp.interfaces.OnDataLoadedListener;
import com.example.tournameapp.model.Match;
import com.example.tournameapp.model.Player;
import com.example.tournameapp.model.Tournament;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

public class TournamentDashboardPresenter {

    private UsersService usersService;
    private TournamentsService tournamentsService;
    private MatchesService matchesService;
    private TournamentDataListener listener;

    private Tournament tournament;
    private Player player;
    private List<Match> allMatches;
    private List<Match> myMatches;

    public TournamentDashboardPresenter(TournamentDataListener listener) {
        this.usersService = UsersService.getInstance();
        this.tournamentsService = TournamentsService.getInstance();
        this.matchesService = MatchesService.getInstance();
        this.listener = listener;
    }

    public void loadData(String tournamentID, String playerUsername) {
        this.player = usersService.getPlayer(playerUsername);
        tournamentsService.loadTournament(tournamentID, new OnDataLoadedListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                tournament = dataSnapshot.getValue(Tournament.class);
                loadMatches();
            }

            @Override
            public void onError(DatabaseError error) {
                throw new RuntimeException(error.getMessage());
            }
        });
    }

    private void loadMatches() {
        matchesService.loadAllMatches(tournament, new OnDataLoadedListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                allMatches = new ArrayList<>();
                myMatches = new ArrayList<>();
                for(DataSnapshot child : dataSnapshot.getChildren()) {
                    Match match = child.getValue(Match.class);
                    allMatches.add(match);
                    if(match.getHomePlayer().equals(player) || match.getAwayPlayer().equals(player)) {
                        myMatches.add(match);
                    }
                }
                listener.onDataLoaded(tournament,allMatches,myMatches,player);
            }

            @Override
            public void onError(DatabaseError error) {
                throw new RuntimeException(error.getMessage());
            }
        });
    }
}
