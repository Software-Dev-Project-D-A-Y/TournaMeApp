package com.example.tournameapp.presenters;

import android.util.Log;

import com.example.tournameapp.database.MatchesService;
import com.example.tournameapp.database.RequestsService;
import com.example.tournameapp.database.TournamentsService;
import com.example.tournameapp.database.UsersService;
import com.example.tournameapp.interfaces.OnDataLoadedListener;
import com.example.tournameapp.interfaces.EditTournamentListener;
import com.example.tournameapp.model.Manager;
import com.example.tournameapp.model.Match;
import com.example.tournameapp.model.Player;
import com.example.tournameapp.model.Tournament;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

public class EditTournamentPresenter {

    private EditTournamentListener listener;

    private TournamentsService tourService;
    private UsersService usersService;
    private RequestsService reqService;
    private MatchesService matchesService;

    private Tournament tournament;
    private Manager manager;

    private List<Player> players;
    private List<Match> matches;

    public EditTournamentPresenter(EditTournamentListener listener) {
        this.listener = listener;
        tourService = TournamentsService.getInstance();
        usersService = UsersService.getInstance();
        reqService = RequestsService.getInstance();
        matchesService = MatchesService.getInstance();
    }

    public void loadTournament() {
        if(tournament == null || manager == null) {
            throw new RuntimeException("Tournament or Manager contains null value!");
        }

        loadPlayers();
        loadAllMatches();
        loadMatchesPlayed();

        listener.onTournamentLoaded(tournament);
    }

    public void loadTournament(String tournamentID) {
        tourService.loadTournament(tournamentID, new OnDataLoadedListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                tournament = dataSnapshot.getValue(Tournament.class);
                if(tournament == null) return;

                manager = tournament.getManager();

                setTournament(tournament);
                setManager(manager);

                loadPlayers();
                loadAllMatches();
                loadMatchesPlayed();

                listener.onTournamentLoaded(tournament);
            }

            @Override
            public void onError(DatabaseError error) {
                throw new RuntimeException(error.getMessage());
            }
        });
    }

    private void loadMatchesPlayed() {
        matchesService.loadMatchesPlayed(tournament, new OnDataLoadedListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                List<Match> matchesPlayed = new ArrayList<>();
                for(DataSnapshot child: dataSnapshot.getChildren()){
                    Match match = child.getValue(Match.class);
                    matchesPlayed.add(match);
                }
                listener.onTournamentMatchesPlayedLoaded(matchesPlayed);
            }

            @Override
            public void onError(DatabaseError error) {
                throw new RuntimeException(error.getMessage());
            }
        });
    }

    private void loadAllMatches() {
        matchesService.loadAllMatches(tournament, new OnDataLoadedListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                matches = new ArrayList<>();
                for(DataSnapshot child: dataSnapshot.getChildren()){
                    Match match = child.getValue(Match.class);
                    matches.add(match);
                }
                listener.onTournamentAllMatchesLoaded(matches);
            }

            @Override
            public void onError(DatabaseError error) {
                throw new RuntimeException(error.getMessage());
            }
        });
    }

    private void loadPlayers() {

        tourService.loadTournamentPlayers(tournament, new OnDataLoadedListener() {
            @Override
            public void onStart() {
                Log.d("Tournament Players", "Before loading");
            }

            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                players = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Player player = child.getValue(Player.class);
                    players.add(player);
                }
                Log.d("Tournament Players", "After loading");

                if (players.size() >= tournament.getCapacity()) {
                    tournament.setJoinable(false);
                } else {
                    tournament.setJoinable(true);
                }
                tourService.updateTournament(tournament);
                listener.onTournamentPlayersLoaded(players);
            }

            @Override
            public void onError(DatabaseError error) {
                throw new RuntimeException(error.getMessage());
            }
        });
    }

    public void sendInvite(String username) {
        Player player = usersService.getPlayer(username);
        boolean isExists = player != null;
        if (!isExists) {
            listener.onInviteUsernameError("Wrong Username!");
            return;
        }

        if (!tournament.isJoinable()) {
            listener.onInviteFailure("Tournament is full");
            return;
        }

        boolean isInserted = reqService.insertTournamentRequest(tournament, player, true);
        if (isInserted) {
            listener.onInvite("Invite sent");
        }

    }

    public void startTournament(Tournament tournament) {

        if (!(tournament.isActive())) {
            generateLeagueMatches();
        }
        tournament.setActive(true);
        tourService.updateTournament(tournament);
        listener.onTournamentStarted("Tournament has started");

    }

    private void generateLeagueMatches() {
        for (Player homePlayer : players) {
            for (Player awayPlayer : players) {
                if (homePlayer == awayPlayer) {
                    continue;
                }
                Match match = new Match(tournament, homePlayer, awayPlayer);
                matches.add(match);
                matchesService.addMatch(match);

            }
        }
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public List<Match> getMatches() {
        return matches;
    }

}
