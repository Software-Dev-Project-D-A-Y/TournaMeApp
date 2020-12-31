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

    private PlayerActionsListener observer;
    private Player player;

    public PlayerPresenter(PlayerActionsListener observer, String playerLogged) {
        this.usersService = UsersService.getInstance();
        this.tourService = TournamentsService.getInstance();
        this.reqService = RequestsService.getInstance();
        this.player = usersService.getPlayer(playerLogged);
        this.observer = observer;
    }

    public void onMyRequestsClicked() {
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
                observer.onMyRequestsSuccess(requests);
            }

            @Override
            public void onError(DatabaseError error) {
                throw new RuntimeException(error.getMessage());
            }
        });
    }

    public void setRequestApproved(final TournamentRequest requestChose) {
        if (!requestChose.getTournament().isJoinable()) {
            Log.d(requestChose.getTournament().toString(), "full capacity!");
            return;
        }

        requestChose.setPlayerApprove(true);
        if (requestChose.isManagerApprove() && requestChose.isPlayerApprove()) {
            final Player player = requestChose.getPlayer();
            final Tournament tournament = requestChose.getTournament();

            tourService.loadTournamentPlayers(tournament, new OnDataLoadedListener() {
                @Override
                public void onStart() {

                }

                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    long numOfPlayers = dataSnapshot.getChildrenCount();

                    if (numOfPlayers < tournament.getCapacity()) {
                        tourService.addPlayerToTournament(player, tournament);
                        observer.onPlayerAddedToTournament("Player added successfully!");
                    } else {
                        tournament.setJoinable(false);
                        observer.onAddFailure("Tournament is full!");
                    }
                }

                @Override
                public void onError(DatabaseError error) {
                    throw new RuntimeException(error.getMessage());
                }
            });
            reqService.removeRequest(requestChose);
        } else {
            reqService.updateRequest(requestChose);
        }

    }

    public void onMyTournamentClicked() {
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

                observer.onMyTournamentsSuccess(tournaments);
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
                        observer.onJoinRequestSuccess("request sent successfully");
                        return;
                    }
                }
                observer.onJoinRequestFailure("Couldn't find this tournamnetID, try another tournament ID");
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

    public void onPlayerLeave(Tournament tourToLeave) {
        tourService.removePlayerFromTournament(player,tourToLeave);
        observer.onPlayerRemoved(player.getUserName()+" Removed from "+tourToLeave.getTournamentName());
    }
}
