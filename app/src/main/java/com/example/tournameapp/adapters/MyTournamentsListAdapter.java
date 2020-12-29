package com.example.tournameapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tournameapp.R;
import com.example.tournameapp.model.Tournament;

import java.util.List;

public class MyTournamentsListAdapter extends ArrayAdapter<Tournament> {

    private Context context;
    private int resource;
    private List<Tournament> tournaments;

    private class ViewHolder {
        TextView tournamentTxt;
    }

    public MyTournamentsListAdapter(@NonNull Context context, int resource, @NonNull List<Tournament> tournaments) {
        super(context, resource, tournaments);
        this.context = context;
        this.resource = resource;
        this.tournaments = tournaments;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        Tournament tournament = tournaments.get(position);

        if (convertView == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            convertView = vi.inflate(resource, null);
        }

        if (tournament == null) return convertView;

        holder.tournamentTxt = (TextView) convertView.findViewById(R.id.tournamentTxt);

        holder.tournamentTxt.setText(tournament.getTournamentName());
        if (tournament.isActive())
        {
            holder.tournamentTxt.setTextColor(Color.GREEN);
        } else {
            holder.tournamentTxt.setTextColor(Color.RED);
        }


        return convertView;
    }
}
