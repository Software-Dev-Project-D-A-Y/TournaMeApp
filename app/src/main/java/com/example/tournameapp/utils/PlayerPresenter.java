package com.example.tournameapp.utils;

import android.util.Log;

import com.example.tournameapp.database.RequestsService;
import com.example.tournameapp.database.TournamentsService;
import com.example.tournameapp.database.UsersService;
import com.example.tournameapp.interfaces.PlayerObserver;
import com.example.tournameapp.interfaces.OnDataLoadedListener;
import com.example.tournameapp.model.Player;
import com.example.tournameapp.model.Tournament;
import com.example.tournameapp.model.TournamentRequest;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class PlayerPresenter {

    private UsersService usersService;
    private TournamentsService tourService;
    private RequestsService reqService;

    private PlayerObserver observer;
    private Player player;

    public PlayerPresenter(PlayerObserver observer, String playerLogged){
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
                        Log.d("LoadPlayerRequests","Wait until data is loaded");
                    }

                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        List<TournamentRequest> requests = new ArrayList<>();
                        for(DataSnapshot child: dataSnapshot.getChildren()){
                            TournamentRequest tr = child.getValue(TournamentRequest.class);
                            requests.add(tr);
                        }
                        observer.onMyRequestsSuccess(requests);
                    }
                });
    }

    public void setRequestApproved(final TournamentRequest requestChose) {
        if(!requestChose.getTournament().isJoinable()){
            Log.d(requestChose.getTournament().toString(),"full capacity!");
            return;
        }

        requestChose.setPlayerApprove(true);
        if(requestChose.isManagerApprove() && requestChose.isPlayerApprove()){
            final Player player = requestChose.getPlayer();
            final Tournament tournament = requestChose.getTournament();

            tourService.loadTournamentPlayers(tournament, new OnDataLoadedListener() {
                @Override
                public void onStart() {

                }

                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getChildrenCount() < tournament.getCapacity()){
                        tourService.addPlayerToTournament(player,tournament);
                        observer.onPlayerAddedToTournament("Player added successfully!");
                    } else {
                        tournament.setJoinable(false);
                        Log.d("onFailure","full capacity!");
                    }
                }
            });
            reqService.removeRequest(requestChose);
        } else {
            reqService.updateRequest(requestChose);
        }

    }
}
