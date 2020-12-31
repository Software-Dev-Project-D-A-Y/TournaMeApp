package com.example.tournameapp.presenters;

import android.util.Log;

import com.example.tournameapp.database.RequestsService;
import com.example.tournameapp.database.TournamentsService;
import com.example.tournameapp.database.UsersService;
import com.example.tournameapp.interfaces.PlayerActionsListener;
import com.example.tournameapp.interfaces.OnDataLoadedListener;
import com.example.tournameapp.model.Player;
import com.example.tournameapp.model.Tournament;
import com.example.tournameapp.model.TournamentRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

public class PlayerPresenter {

    private UsersService usersService;
    private TournamentsService tourService;
    private RequestsService reqService;

    private PlayerActionsListener listener;
    private Player player;

    public PlayerPresenter(PlayerActionsListener listener, String playerLogged) {
        this.usersService = UsersService.getInstance();
        this.tourService = TournamentsService.getInstance();
        this.reqService = RequestsService.getInstance();
        this.player = usersService.getPlayer(playerLogged);
        this.listener = listener;
    }

    public void loadRequests() {
        reqService.loadPlayerRequests(player, new OnDataLoadedListener() {
            @Override
            public void onStart() {
                Log.d("LoadPlayerRequests", "Wait until data is loaded");
            }

            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                List<TournamentRequest> requests = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    TournamentRequest tr = child.getValue(TournamentRequest.class);
                    requests.add(tr);
                }
                listener.onPlayerRequestsLoaded(requests);
            }

            @Override
            public void onError(DatabaseError error) {
                throw new RuntimeException(error.getMessage());
            }
        });
    }

    public void loadPlayerTournaments() {
        tourService.loadPlayerTournaments(player, new OnDataLoadedListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                List<Tournament> tournaments = new ArrayList<>();

                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    Tournament tournament = child.getValue(Tournament.class);
                    tournaments.add(tournament);
                }

                listener.onPlayerTournamentsLoaded(tournaments);
            }

            @Override
            public void onError(DatabaseError error) {
                throw new RuntimeException(error.getMessage());
            }
        });
    }

    public void onJoinRequest(final String tournamentID) {
        tourService.loadAllTournaments(new OnDataLoadedListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String key = child.getKey();
                    Log.d(key,tournamentID);
                    if (key.equals(tournamentID)) {
                        Tournament tournament = child.getValue(Tournament.class);
                        reqService.insertTournamentRequest(tournament, player, false);
                        listener.onJoinRequestSuccess("request sent successfully");
                        return;
                    }
                }
                listener.onJoinRequestFailure("Couldn't find this tournamnetID, try another tournament ID");
                return;// there is no such tournament should update the player
            }

            @Override
            public void onError(DatabaseError error) {
                throw new RuntimeException(error.getMessage());
            }
        });

    }

    public Player getPlayer(){
        return player;
    }

}
