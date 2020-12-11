package com.example.tournameapp.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.tournameapp.R;
import com.example.tournameapp.database.MatchesService;
import com.example.tournameapp.model.Match;

import java.util.List;

public class UpdateScoreFragment extends DialogFragment {

    private List<Match> matches;
    private MatchesService matchService;
    private ListView matchesLv;

    public UpdateScoreFragment(List<Match> matches){
        this.matches = matches;
        matchService = MatchesService.getInstance();
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_score, container, false);
        matchesLv = (ListView) view.findViewById(R.id.mathcesLv);
        ArrayAdapter<Match> adapter = new ArrayAdapter<>(getContext(), R.layout.layout_tournaments_list, matches);
        matchesLv.setAdapter(adapter);

        return view;
    }

}

