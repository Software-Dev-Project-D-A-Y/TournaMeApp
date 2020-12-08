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
        usersService = UsersService.getInstance();
        tourService = TournamentsService.getInstance();
        reqService = RequestsService.getInstance();
        player = usersService.getPlayer(playerLogged);
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

    public void setRequestApproved(TournamentRequest requestChose) {
        requestChose.setPlayerApprove(true);
        if(requestChose.isManagerApprove() && requestChose.isPlayerApprove()){
            Player player = requestChose.getPlayer();
            Tournament tournament = requestChose.getTournament();

            tourService.addPlayerToTournament(player,tournament);
            reqService.removeRequest(requestChose);

            observer.onPlayerAddedToTournament("Player added successfully!");
        } else {
            reqService.updateRequest(requestChose);
        }

    }
}
