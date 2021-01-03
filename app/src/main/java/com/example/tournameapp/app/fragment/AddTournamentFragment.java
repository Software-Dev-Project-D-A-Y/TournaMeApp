package com.example.tournameapp.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.tournameapp.R;
import com.example.tournameapp.interfaces.ManagerActionsListener;
import com.example.tournameapp.interfaces.OnAddTournamentListener;
import com.example.tournameapp.model.Manager;
import com.example.tournameapp.model.Tournament;
import com.example.tournameapp.presenters.AddTournamentPresenter;

public class AddTournamentFragment extends DialogFragment implements OnAddTournamentListener {

    private TextView tournamentNameTxt;
    private TextView tournamentCapacityTxt;
    private TextView tournamentDescTxt;
    private Button addNewTournamentBtn;

    private AddTournamentPresenter presenter;
    private ManagerActionsListener listener;

    public AddTournamentFragment(Manager manager){
        this.presenter = new AddTournamentPresenter(manager,this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_tournament, container, false);

        tournamentNameTxt = (TextView) view.findViewById(R.id.tournamentNameTxt);
        tournamentCapacityTxt = (TextView) view.findViewById(R.id.tournamentCapacityTxt);
        tournamentDescTxt = (TextView) view.findViewById(R.id.tournamentDescTxt);
        addNewTournamentBtn = (Button) view.findViewById(R.id.addNewTournamentBtn);

        addNewTournamentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tourName = tournamentNameTxt.getText().toString();
                String tourDesc = tournamentDescTxt.getText().toString();
                // DATE
                int tourCap;
                try {
                    tourCap = Integer.parseInt(tournamentCapacityTxt.getText().toString());
                } catch (Exception e){
                    tourCap = -1;
                }

                presenter.addTournament(tourName,tourDesc,tourCap);

            }
        });

        return view;
    }

    @Override
    public void onTournamentNameError(String message) {
        tournamentNameTxt.setError(message);
    }

    @Override
    public void onCapacityError(String message) {
        tournamentCapacityTxt.setError(message);
    }

    @Override
    public void onAdd(Tournament newTournament) {
        Toast.makeText(getActivity(),"Tournament Added for "+newTournament.getManager().getUserName(),Toast.LENGTH_SHORT).show();
        dismiss();
    }
}
