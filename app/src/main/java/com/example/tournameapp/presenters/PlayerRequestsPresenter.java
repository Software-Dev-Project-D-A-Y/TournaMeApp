package com.example.tournameapp.presenters;

import android.util.Log;

import com.example.tournameapp.database.RequestsService;
import com.example.tournameapp.database.TournamentsService;
import com.example.tournameapp.database.UsersService;
import com.example.tournameapp.interfaces.OnDataLoadedListener;
import com.example.tournameapp.interfaces.OnRequestApprovedListener;
import com.example.tournameapp.model.Player;
import com.example.tournameapp.model.Tournament;
import com.example.tournameapp.model.TournamentRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class PlayerRequestsPresenter {

    private TournamentsService tourService;
    private RequestsService reqService;
    private OnRequestApprovedListener listener;

    public PlayerRequestsPresenter(OnRequestApprovedListener listener) {
        this.tourService = TournamentsService.getInstance();
        this.reqService = RequestsService.getInstance();
        this.listener = listener;
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
                        listener.onRequestApproved("Player added successfully!");
                    } else {
                        tournament.setJoinable(false);
                        listener.onRequestFailure("Tournament is full!");
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

}
